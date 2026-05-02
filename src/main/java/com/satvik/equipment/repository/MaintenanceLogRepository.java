package com.satvik.equipment.repository;

import com.satvik.equipment.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, UUID> {
    List<MaintenanceLog> findByWorkOrderId(UUID workOrderId);
    List<MaintenanceLog> findByTechnicianId(UUID technicianId);
}
