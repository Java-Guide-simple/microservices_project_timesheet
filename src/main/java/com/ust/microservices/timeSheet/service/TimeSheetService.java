package com.ust.microservices.timeSheet.service;


import com.ust.microservices.timeSheet.model.Project;
import com.ust.microservices.timeSheet.model.ResultEmployeeHasProjects;
import com.ust.microservices.timeSheet.model.TimeSheet;
import com.ust.microservices.timeSheet.repository.TimeSheetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TimeSheetService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    //Add New TimeSheet

    public TimeSheet saveTimeSheet(TimeSheet timeSheet) {
        log.info("Inside TimeSheetService#saveTimeSheet Method");
        TimeSheet timeSheet1 = showfilledTimeSheet(timeSheet.getEmployeeId(), timeSheet.getProjectId(), timeSheet.getDate());
        if (timeSheet1 == null) {

            ResultEmployeeHasProjects employeeWithProjects = restTemplate.getForObject("http://localhost:8085/api/allotment/get-projects-handleBy/" + timeSheet.getEmployeeId(), ResultEmployeeHasProjects.class);
            Set<Project> employeeHasprojects = employeeWithProjects.getProjects();


            employeeHasprojects.stream().forEach(p -> {
                        if (p.getId().equals(timeSheet.getProjectId())) {
                            timeSheetRepository.save(timeSheet);

                        }
                    }
            );

            return timeSheet;

        } else {
            return null;
        }


    }


    //Fetch All TimeSheet on Single Date For Single Employee

    public List<TimeSheet> showfilledTimeSheet(Integer employeeId, LocalDate date) {
        log.info("Inside TimeSheetService#showfilledTimeSheet Method");
        return timeSheetRepository.getTimeSheet(employeeId, date);
    }

    //Fetch TimeSheet for single project on Single Date For Single Employee

    public TimeSheet showfilledTimeSheet(Integer employeeId, Integer projectId, LocalDate date) {
        log.info("Inside TimeSheetService#showfilledTimeSheet Method");
        TimeSheet timeSheet = timeSheetRepository.getTimeSheet(employeeId, projectId, date);
        return timeSheet;
    }

    // Fetch TimeSheets Between two Dates For Single Employee and single Project
    public List<TimeSheet> getTimeSheet(Integer eid, Integer pid, LocalDate startDate, LocalDate endDate) {
        log.info("Inside TimeSheetService#getTimeSheet Method");
        return timeSheetRepository.getTimeSheet(eid, pid, startDate, endDate);
    }

    // Fetch TimeSheets Between two Dates For Single Employee
    public List<TimeSheet> getTimeSheet(Integer eid, LocalDate startDate, LocalDate endDate) {
        log.info("Inside TimeSheetService#getTimeSheet Method");
        return timeSheetRepository.getTimeSheet(eid, startDate, endDate);
    }

    // "Update TimeSheet"
    public TimeSheet updateTimeSheet(TimeSheet timeSheet) {
        log.info("Inside TimeSheetService#updateTimeSheet Method");
        boolean b = timeSheetRepository.existsById(timeSheet.getTid());
        if (b) {
            TimeSheet updated = timeSheetRepository.saveAndFlush(timeSheet);
            return updated;
        } else {
            return null;
        }

    }

    //"Delete TimeSheet By ID"
    public Boolean deleteTimesheet(Integer tid) {
        log.info("Inside TimeSheetService#deleteTimesheet Method");

        boolean b = timeSheetRepository.existsById(tid);
        if (b) {
            timeSheetRepository.deleteById(tid);
        }
        return b;

    }


}
