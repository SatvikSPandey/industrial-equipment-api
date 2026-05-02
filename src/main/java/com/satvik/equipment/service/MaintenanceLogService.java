package com.satvik.equipment.service;

import com.satvik.equipment.dto.request.MaintenanceLogRequest;
import com.satvik.equipment.dto.response.MaintenanceLogResponse;
import com.satvik.equipment.entity.MaintenanceLog;
import com.satvik.equipment.entity.User;
import com.satvik.equipment.entity.WorkOrder;
import com.satvik.equipment.repository.MaintenanceLogRepository;
import com.satvik.equipment.repository.UserRepository;
import com.satvik.equipment.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    public List<MaintenanceLogResponse> getAll() {
        return maintenanceLogRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<MaintenanceLogResponse> getByWorkOrder(UUID workOrderId) {
        return maintenanceLogRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public MaintenanceLogResponse create(MaintenanceLogRequest request) {
        WorkOrder workOrder = workOrderRepository.findById(request.getWorkOrderId())
                .orElseThrow(() -> new RuntimeException("Work order not found: " + request.getWorkOrderId()));

        User technician = userRepository.findById(request.getTechnicianId())
                .orElseThrow(() -> new RuntimeException("Technician not found: " + request.getTechnicianId()));

        MaintenanceLog log = MaintenanceLog.builder()
                .workOrder(workOrder)
                .technician(technician)
                .notes(request.getNotes())
                .hoursSpent(request.getHoursSpent())
                .partsReplaced(request.getPartsReplaced())
                .build();

        return toResponse(maintenanceLogRepository.save(log));
    }

    private MaintenanceLogResponse toResponse(MaintenanceLog log) {
        return MaintenanceLogResponse.builder()
                .id(log.getId())
                .workOrderId(log.getWorkOrder().getId())
                .technicianId(log.getTechnician().getId())
                .technicianUsername(log.getTechnician().getUsername())
                .notes(log.getNotes())
                .hoursSpent(log.getHoursSpent())
                .partsReplaced(log.getPartsReplaced())
                .build();
    }
}