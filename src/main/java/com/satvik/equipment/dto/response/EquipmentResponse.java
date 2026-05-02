package com.satvik.equipment.dto.response;

import com.satvik.equipment.enums.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentResponse {
    private UUID id;
    private String name;
    private String type;
    private String location;
    private EquipmentStatus status;
    private LocalDate installationDate;
    private LocalDate lastMaintenanceDate;
}