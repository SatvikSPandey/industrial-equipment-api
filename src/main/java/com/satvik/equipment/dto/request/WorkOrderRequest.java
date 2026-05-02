package com.satvik.equipment.dto.request;

import com.satvik.equipment.enums.WorkOrderPriority;
import com.satvik.equipment.enums.WorkOrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class WorkOrderRequest {
    @NotNull(message = "Equipment ID is required")
    private UUID equipmentId;

    private UUID assignedToId;

    @NotNull(message = "Priority is required")
    private WorkOrderPriority priority;

    @NotNull(message = "Status is required")
    private WorkOrderStatus status;

    @NotBlank(message = "Description is required")
    private String description;

    private LocalDate scheduledDate;
    private LocalDate completedDate;
}