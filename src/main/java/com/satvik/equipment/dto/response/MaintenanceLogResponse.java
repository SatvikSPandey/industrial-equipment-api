package com.satvik.equipment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLogResponse {
    private UUID id;
    private UUID workOrderId;
    private UUID technicianId;
    private String technicianUsername;
    private String notes;
    private Double hoursSpent;
    private String partsReplaced;
}