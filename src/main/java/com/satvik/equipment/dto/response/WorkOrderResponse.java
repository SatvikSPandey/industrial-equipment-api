package com.satvik.equipment.dto.response;

import com.satvik.equipment.enums.WorkOrderPriority;
import com.satvik.equipment.enums.WorkOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderResponse {
    private UUID id;
    private UUID equipmentId;
    private String equipmentName;
    private UUID assignedToId;
    private String assignedToUsername;
    private UUID createdById;
    private String createdByUsername;
    private WorkOrderPriority priority;
    private WorkOrderStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDate scheduledDate;
    private LocalDate completedDate;
}