package com.ust.microservices.timeSheet.repository;

import com.ust.microservices.timeSheet.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {

    //Fetch All TimeSheet on Single Date For Single Employee
    @Query(value = "select t from TimeSheet t where t.employeeId=?1 and t.date=?2")
    public List<TimeSheet> getTimeSheet(Integer eid, LocalDate date);

    //Fetch TimeSheet for single project on Single Date For Single Employee
    @Query(value = "select t from TimeSheet t where t.employeeId=?1 and t.projectId=?2 and t.date=?3")
    public TimeSheet getTimeSheet(Integer eid, Integer pid, LocalDate date);

    // Fetch TimeSheets Between two Dates For Single Employee and single Project
    @Query(value = "select t from TimeSheet t where t.employeeId=?1 and t.projectId=?2 and t.date between ?3 and ?4")
    public List<TimeSheet> getTimeSheet(Integer eid, Integer pid, LocalDate startDate, LocalDate endDate);

    // Fetch TimeSheets Between two Dates For Single Employee
    @Query(value = "select t from TimeSheet t where t.employeeId=?1 and t.date between ?2 and ?3")
    public List<TimeSheet> getTimeSheet(Integer eid, LocalDate startDate, LocalDate endDate);

    //Fetch All TimeSheet  Single Employee
    @Query(value = "select t from TimeSheet t where t.employeeId=?1 ")
    public List<TimeSheet> getTimeSheetByEmployeeId(Integer eid);


    // Fetch TimeSheets Between two Dates For All Employee and single Project
    @Query(value = "select t from TimeSheet t where t.projectId=?1 and t.date between ?2 and ?3")
    public List<TimeSheet> getTimeSheetByPid( Integer pid, LocalDate startDate, LocalDate endDate);

    // Fetch TimeSheets Between two Dates For All Employee
    @Query(value = "select t from TimeSheet t where t.date between ?1 and ?2")
    public List<TimeSheet> getTimeSheets(LocalDate startDate, LocalDate endDate);


}
