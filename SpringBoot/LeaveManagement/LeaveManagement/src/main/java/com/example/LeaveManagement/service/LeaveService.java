package com.example.LeaveManagement.service;

import com.example.LeaveManagement.entity.LeaveRequest;
import com.example.LeaveManagement.exception.InvalidLeaveException;
import com.example.LeaveManagement.exception.ResourceNotFoundException;
import com.example.LeaveManagement.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    private final LeaveRepository repository;

    public LeaveService(LeaveRepository repository) {
        this.repository = repository;
    }

    // Apply Leave
    public LeaveRequest applyLeave(LeaveRequest leave) {

        // Start date should not be after end date
        if (leave.getStartDate().isAfter(leave.getEndDate())) {
            throw new InvalidLeaveException("Start date cannot be after end date");
        }

        // Overlapping leave check
        if (!repository
                .findByEmployeeIdAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
                        leave.getEmployeeId(),
                        leave.getStartDate(),
                        leave.getEndDate())
                .isEmpty()) {

            throw new InvalidLeaveException("Employee already has leave during these dates");
        }

        leave.setStatus("Pending");

        return repository.save(leave);
    }

    // View all leaves
    public List<LeaveRequest> getAllLeaves() {
        return repository.findAll();
    }

    // View leave by employee
    public List<LeaveRequest> getLeavesByEmployee(Long employeeId) {

        List<LeaveRequest> leaves = repository.findByEmployeeId(employeeId);

        if (leaves.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }

        return leaves;
    }

    // Approve leave
    public LeaveRequest approveLeave(Long leaveId) {

        LeaveRequest leave = repository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found"));

        if (leave.getStatus().equalsIgnoreCase("Approved")) {
            throw new InvalidLeaveException("Leave already approved");
        }

        if (leave.getStatus().equalsIgnoreCase("Cancelled")) {
            throw new InvalidLeaveException("Cancelled leave cannot be approved");
        }

        leave.setStatus("Approved");

        return repository.save(leave);
    }

    // Reject leave
    public LeaveRequest rejectLeave(Long leaveId) {

        LeaveRequest leave = repository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found"));

        if (leave.getStatus().equalsIgnoreCase("Rejected")) {
            throw new InvalidLeaveException("Leave already rejected");
        }

        if (leave.getStatus().equalsIgnoreCase("Cancelled")) {
            throw new InvalidLeaveException("Cancelled leave cannot be rejected");
        }

        leave.setStatus("Rejected");

        return repository.save(leave);
    }

    // Cancel leave
    public LeaveRequest cancelLeave(Long leaveId) {

        LeaveRequest leave = repository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found"));

        if (!leave.getStatus().equalsIgnoreCase("Pending")) {
            throw new InvalidLeaveException("Only pending leave can be cancelled");
        }

        leave.setStatus("Cancelled");

        return repository.save(leave);
    }
}