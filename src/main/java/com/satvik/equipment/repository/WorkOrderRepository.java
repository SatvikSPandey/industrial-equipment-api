package com.satvik.equipment.repository;

import com.satvik.equipment.entity.WorkOrder;
import com.satvik.equipment.enums.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID> {
    List<WorkOrder> findByEquipmentId(UUID equipmentId);
    List<WorkOrder> findByAssignedToId(UUID userId);
    List<WorkOrder> findByStatus(WorkOrderStatus status);
}
