package com.example.LeaveManagement.controller;


import com.example.LeaveManagement.entity.LeaveRequest;
import com.example.LeaveManagement.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    // Apply Leave
    @PostMapping
    public LeaveRequest applyLeave(@Valid @RequestBody LeaveRequest leave) {
        return service.applyLeave(leave);
    }

    // View All Leaves
    @GetMapping
    public List<LeaveRequest> getAllLeaves() {
        return service.getAllLeaves();
    }

    // View Leave By Employee
    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeavesByEmployee(@PathVariable Long employeeId) {
        return service.getLeavesByEmployee(employeeId);
    }

    // Approve Leave
    @PutMapping("/approve/{leaveId}")
    public LeaveRequest approveLeave(@PathVariable Long leaveId) {
        return service.approveLeave(leaveId);
    }

    // Reject Leave
    @PutMapping("/reject/{leaveId}")
    public LeaveRequest rejectLeave(@PathVariable Long leaveId) {
        return service.rejectLeave(leaveId);
    }

    // Cancel Leave
    @PutMapping("/cancel/{leaveId}")
    public LeaveRequest cancelLeave(@PathVariable Long leaveId) {
        return service.cancelLeave(leaveId);
    }
}