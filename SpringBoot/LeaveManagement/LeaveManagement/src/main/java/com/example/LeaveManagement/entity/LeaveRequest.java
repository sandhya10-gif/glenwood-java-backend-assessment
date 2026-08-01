package com.example.LeaveManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotBlank(message = "Employee Name is required")
    private String employeeName;

    @NotBlank(message = "Leave Type is required")
    private String leaveType;

    @NotNull(message = "Start Date is required")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    private LocalDate endDate;

    @NotBlank(message = "Reason is required")
    private String reason;

    private String status = "Pending";
}