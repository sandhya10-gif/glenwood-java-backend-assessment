package com.example.LeaveManagement.repository;

import com.example.LeaveManagement.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

    List<LeaveRequest> findByEmployeeIdAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate
    );
}