package com.satvik.equipment.controller;

import com.satvik.equipment.dto.request.MaintenanceLogRequest;
import com.satvik.equipment.dto.response.MaintenanceLogResponse;
import com.satvik.equipment.service.MaintenanceLogService;
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
@RequestMapping("/api/maintenance-logs")
@RequiredArgsConstructor
@Tag(name = "Maintenance Logs", description = "Manage maintenance activity logs")
@SecurityRequirement(name = "bearerAuth")
public class MaintenanceLogController {

    private final MaintenanceLogService maintenanceLogService;

    @GetMapping
    @Operation(summary = "Get all maintenance logs")
    public ResponseEntity<List<MaintenanceLogResponse>> getAll() {
        return ResponseEntity.ok(maintenanceLogService.getAll());
    }

    @GetMapping("/workorder/{workOrderId}")
    @Operation(summary = "Get logs by work order")
    public ResponseEntity<List<MaintenanceLogResponse>> getByWorkOrder(@PathVariable UUID workOrderId) {
        return ResponseEntity.ok(maintenanceLogService.getByWorkOrder(workOrderId));
    }

    @PostMapping
    @Operation(summary = "Create maintenance log")
    public ResponseEntity<MaintenanceLogResponse> create(@Valid @RequestBody MaintenanceLogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceLogService.create(request));
    }
}