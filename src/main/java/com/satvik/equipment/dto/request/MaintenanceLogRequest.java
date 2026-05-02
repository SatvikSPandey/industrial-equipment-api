package com.satvik.equipment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MaintenanceLogRequest {
    @NotNull(message = "Work Order ID is required")
    private UUID workOrderId;

    @NotNull(message = "Technician ID is required")
    private UUID technicianId;

    private String notes;
    private Double hoursSpent;
    private String partsReplaced;
}