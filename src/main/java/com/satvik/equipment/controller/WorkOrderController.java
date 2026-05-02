package com.satvik.equipment.controller;

import com.satvik.equipment.dto.request.WorkOrderRequest;
import com.satvik.equipment.dto.response.WorkOrderResponse;
import com.satvik.equipment.service.WorkOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
@Tag(name = "Work Orders", description = "Manage maintenance work orders")
@SecurityRequirement(name = "bearerAuth")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @GetMapping
    @Operation(summary = "Get all work orders")
    public ResponseEntity<List<WorkOrderResponse>> getAll() {
        return ResponseEntity.ok(workOrderService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get work order by ID")
    public ResponseEntity<WorkOrderResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(workOrderService.getById(id));
    }

    @GetMapping("/equipment/{equipmentId}")
    @Operation(summary = "Get work orders by equipment")
    public ResponseEntity<List<WorkOrderResponse>> getByEquipment(@PathVariable UUID equipmentId) {
        return ResponseEntity.ok(workOrderService.getByEquipment(equipmentId));
    }

    @PostMapping
    @Operation(summary = "Create new work order")
    public ResponseEntity<WorkOrderResponse> create(@Valid @RequestBody WorkOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update work order")
    public ResponseEntity<WorkOrderResponse> update(@PathVariable UUID id,
                                                     @Valid @RequestBody WorkOrderRequest request) {
        return ResponseEntity.ok(workOrderService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete work order")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        workOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}