package com.satvik.equipment.service;

import com.satvik.equipment.dto.request.WorkOrderRequest;
import com.satvik.equipment.dto.response.WorkOrderResponse;
import com.satvik.equipment.entity.Equipment;
import com.satvik.equipment.entity.User;
import com.satvik.equipment.entity.WorkOrder;
import com.satvik.equipment.repository.EquipmentRepository;
import com.satvik.equipment.repository.UserRepository;
import com.satvik.equipment.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public List<WorkOrderResponse> getAll() {
        return workOrderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public WorkOrderResponse getById(UUID id) {
        return toResponse(workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + id)));
    }

    public List<WorkOrderResponse> getByEquipment(UUID equipmentId) {
        return workOrderRepository.findByEquipmentId(equipmentId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public WorkOrderResponse create(WorkOrderRequest request) {
        Equipment equipment = equipmentRepository.findById(request.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + request.getEquipmentId()));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User createdBy = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + currentUsername));

        User assignedTo = null;
        if (request.getAssignedToId() != null) {
            assignedTo = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found: " + request.getAssignedToId()));
        }

        WorkOrder workOrder = WorkOrder.builder()
                .equipment(equipment)
                .createdBy(createdBy)
                .assignedTo(assignedTo)
                .priority(request.getPriority())
                .status(request.getStatus())
                .description(request.getDescription())
                .scheduledDate(request.getScheduledDate())
                .completedDate(request.getCompletedDate())
                .build();

        return toResponse(workOrderRepository.save(workOrder));
    }

    public WorkOrderResponse update(UUID id, WorkOrderRequest request) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + id));

        Equipment equipment = equipmentRepository.findById(request.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + request.getEquipmentId()));

        User assignedTo = null;
        if (request.getAssignedToId() != null) {
            assignedTo = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found: " + request.getAssignedToId()));
        }

        workOrder.setEquipment(equipment);
        workOrder.setAssignedTo(assignedTo);
        workOrder.setPriority(request.getPriority());
        workOrder.setStatus(request.getStatus());
        workOrder.setDescription(request.getDescription());
        workOrder.setScheduledDate(request.getScheduledDate());
        workOrder.setCompletedDate(request.getCompletedDate());

        return toResponse(workOrderRepository.save(workOrder));
    }

    public void delete(UUID id) {
        if (!workOrderRepository.existsById(id)) {
            throw new RuntimeException("Work order not found: " + id);
        }
        workOrderRepository.deleteById(id);
    }

    private WorkOrderResponse toResponse(WorkOrder w) {
        return WorkOrderResponse.builder()
                .id(w.getId())
                .equipmentId(w.getEquipment().getId())
                .equipmentName(w.getEquipment().getName())
                .assignedToId(w.getAssignedTo() != null ? w.getAssignedTo().getId() : null)
                .assignedToUsername(w.getAssignedTo() != null ? w.getAssignedTo().getUsername() : null)
                .createdById(w.getCreatedBy().getId())
                .createdByUsername(w.getCreatedBy().getUsername())
                .priority(w.getPriority())
                .status(w.getStatus())
                .description(w.getDescription())
                .createdAt(w.getCreatedAt())
                .scheduledDate(w.getScheduledDate())
                .completedDate(w.getCompletedDate())
                .build();
    }
}