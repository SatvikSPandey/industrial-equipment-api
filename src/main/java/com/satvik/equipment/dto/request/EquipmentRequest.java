package com.satvik.equipment.dto.request;

import com.satvik.equipment.enums.EquipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipmentRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Status is required")
    private EquipmentStatus status;

    private LocalDate installationDate;
    private LocalDate lastMaintenanceDate;
}