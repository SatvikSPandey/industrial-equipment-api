package com.satvik.equipment.service;

import com.satvik.equipment.dto.request.EquipmentRequest;
import com.satvik.equipment.dto.response.EquipmentResponse;
import com.satvik.equipment.entity.Equipment;
import com.satvik.equipment.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public List<EquipmentResponse> getAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EquipmentResponse getById(UUID id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + id));
        return toResponse(equipment);
    }

    public EquipmentResponse create(EquipmentRequest request) {
        Equipment equipment = Equipment.builder()
                .name(request.getName())
                .type(request.getType())
                .location(request.getLocation())
                .status(request.getStatus())
                .installationDate(request.getInstallationDate())
                .lastMaintenanceDate(request.getLastMaintenanceDate())
                .build();
        return toResponse(equipmentRepository.save(equipment));
    }

    public EquipmentResponse update(UUID id, EquipmentRequest request) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + id));
        equipment.setName(request.getName());
        equipment.setType(request.getType());
        equipment.setLocation(request.getLocation());
        equipment.setStatus(request.getStatus());
        equipment.setInstallationDate(request.getInstallationDate());
        equipment.setLastMaintenanceDate(request.getLastMaintenanceDate());
        return toResponse(equipmentRepository.save(equipment));
    }

    public void delete(UUID id) {
        if (!equipmentRepository.existsById(id)) {
            throw new RuntimeException("Equipment not found: " + id);
        }
        equipmentRepository.deleteById(id);
    }

    private EquipmentResponse toResponse(Equipment e) {
        return EquipmentResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .type(e.getType())
                .location(e.getLocation())
                .status(e.getStatus())
                .installationDate(e.getInstallationDate())
                .lastMaintenanceDate(e.getLastMaintenanceDate())
                .build();
    }
}