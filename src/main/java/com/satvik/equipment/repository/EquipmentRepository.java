package com.satvik.equipment.repository;

import com.satvik.equipment.entity.Equipment;
import com.satvik.equipment.enums.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
    List<Equipment> findByStatus(EquipmentStatus status);
    List<Equipment> findByLocation(String location);
}
