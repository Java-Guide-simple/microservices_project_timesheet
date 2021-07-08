package com.ust.microservices.timeSheet.controller;


import com.ust.microservices.timeSheet.model.Project;
import com.ust.microservices.timeSheet.model.TimeSheet;
import com.ust.microservices.timeSheet.service.TimeSheetService;
import com.ust.microservices.timeSheet.vo.ListEmployeewithProjects;
import com.ust.microservices.timeSheet.vo.ProjectVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/timesheet")
public class TimeSheetController {

    @Autowired
    private TimeSheetService timeSheetService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")})


    //Add New TimeSheet

    @ApiOperation(value = "Add New TimeSheet",
            response = TimeSheet.class, tags = "CREATE TIMESHEET")
    @PostMapping("/submit-new-timesheet-daywise")
    public String saveNewTimeSheet(@RequestBody TimeSheet timeSheet) {
        log.info("Inside TimeSheetController#saveNewTimeSheet Method");
        TimeSheet timeSheet1 = timeSheetService.saveTimeSheet(timeSheet);

        if (timeSheet1 == null) {

            return "You have arleady submitted your timesheet for Date " + timeSheet.getDate() + "  and project Id  " + timeSheet.getProjectId()
                    + "If You Want to update You TimeSheet then go to update Tab";

        }
        if (timeSheet.getTid() == null) {
            return "Something Went Wrong contact Your Project Admin."
                    + " You Are not allocated on project Id  " + timeSheet1.getProjectId();


        } else {
            return " Timesheet Submitted for Date  " + timeSheet1.getDate() + "  and project Id  " + timeSheet1.getProjectId();
        }


    }


    //Fetch All TimeSheet on Single Date For Single Employee
    @ApiOperation(value = "Fetch All TimeSheet on Single Date For Single Employee",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")
    @GetMapping("/show/{eid}/{date}") // yyyy-mm-dd
    public List<TimeSheet> showTimeSheet(@PathVariable Integer eid, @PathVariable String date) {
        log.info("Inside TimeSheetController#showTimeSheet Method");
        LocalDate date1 = LocalDate.parse(date);
        return timeSheetService.showfilledTimeSheet(eid, date1);
    }


    //Fetch TimeSheet for single project on Single Date For Single Employee

    @ApiOperation(value = "Fetch TimeSheet for single project on Single Date For Single Employee",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")
    @GetMapping("/show/{eid}/{pid}/{date}")
    public TimeSheet showTimeSheetPerProject(@PathVariable Integer eid, @PathVariable Integer pid, @PathVariable String date) {
        log.info("Inside TimeSheetController#showTimeSheetPerProject Method");
        LocalDate date1 = LocalDate.parse(date);
        return timeSheetService.showfilledTimeSheet(eid, pid, date1);

    }

    // Fetch TimeSheets Between two Dates For Single Employee
    @ApiOperation(value = "Fetch TimeSheets Between two Dates For Single Employee",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")

    @GetMapping("/shows/{eid}/{startDate}/{endDate}")
    public ListEmployeewithProjects getTimeSheetPerEmployee(@PathVariable Integer eid, @PathVariable String startDate, @PathVariable String endDate) {
        log.info("Inside TimeSheetController#getTimeSheetPerEmployee Method");
        LocalDate startDate1 = LocalDate.parse(startDate);
        LocalDate endDate1 = LocalDate.parse(endDate);
        List<TimeSheet> list = timeSheetService.getTimeSheet(eid, startDate1, endDate1);
        List<ProjectVO> projects = new ArrayList<ProjectVO>();
        list.stream().forEach(l->{
                    ProjectVO project = new ProjectVO(l.getProjectId(),l.getProjectType(),l.getContributionHrs(),l.getBillable());
                    projects.add(project);
                }

                );

        ListEmployeewithProjects response = new ListEmployeewithProjects();
        response.setEmployeeId(eid);
        response.setProjects(projects);
       // return timeSheetService.getTimeSheet(eid, startDate1, endDate1);
        return response;

    }

    // Fetch TimeSheets Between two Dates For Single Employee and single Project

    @ApiOperation(value = "Fetch TimeSheets Between two Dates For Single Employee and single Project",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")
    @GetMapping("/showing/{eid}/{pid}/{startDate}/{endDate}")
    public List<TimeSheet> getTimeSheetPerEmployeePerProject(@PathVariable Integer eid, @PathVariable Integer pid, @PathVariable String startDate, @PathVariable String endDate) {
        log.info("Inside TimeSheetController#getTimeSheetPerEmployeePerProject Method");
        LocalDate startDate1 = LocalDate.parse(startDate);
        LocalDate endDate1 = LocalDate.parse(endDate);
        return timeSheetService.getTimeSheet(eid, pid, startDate1, endDate1);
    }

    // "Update TimeSheet"
    @ApiOperation(value = "Update TimeSheet",
            response = TimeSheet.class, tags = "UPDATE TIMESHEET")
    @PutMapping("/update")
    public String updateTimeSheet(@RequestBody TimeSheet timeSheet) {
        log.info("Inside TimeSheetController#updateTimeSheet Method");
        TimeSheet afterupdated = timeSheetService.updateTimeSheet(timeSheet);
        if (afterupdated != null) {
            return "TimeSheet Updated";
        } else {
            return "You don't have filled TimeShee  with TimeSheet Id " + timeSheet.getTid() +
                    ". Go To New TimeSheet Submit Tab.";
        }

    }

    //"Delete TimeSheet By ID"
    @ApiOperation(value = "Delete TimeSheet By ID",
            response = TimeSheet.class, tags = "DELETE TIMESHEET")
    @DeleteMapping("/deletd/{tid}")
    public String deleteTimeSheet(@PathVariable Integer tid) {
        log.info("Inside TimeSheetController#deleteTimeSheet Method");
        Boolean deleted = timeSheetService.deleteTimesheet(tid);
        if (deleted)
            return "Timesheet Deleted For TimeSheet Id " + tid;
        else
            return "Something went wrong . May be There is no TimeSheet With TimeSheet Id " + tid;

    }

    // Displaying all Filled Timesheet
    @ApiOperation(value = "Fetch All TimeSheet",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")
    @GetMapping("/get-all")
    public  List<TimeSheet> showAllTimeSheet(){
        List<TimeSheet> allTimeSheets = timeSheetService.showAllTimeSheet();
        return allTimeSheets;
    }



    // Displaying all Filled Timesheet By One Employee
    @ApiOperation(value = "Fetch All TimeSheet For One Employee",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")
    @GetMapping("/get-one/{eid}")
    public List<TimeSheet> getTimeSheetByEmployeeId(@PathVariable Integer eid){
       return timeSheetService.showTimeSheetByEmployeeId(eid);
    }



    // Fetch TimeSheets Between two Dates For all Employee , one project

    @ApiOperation(value = "Fetch TimeSheets Between two Dates For all Employee , one project ",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")


    @GetMapping("/{pid}/{startDate}/{endDate}")
    public List<TimeSheet> getTimeSheetByPid(@PathVariable Integer pid,@PathVariable String startDate,@PathVariable String endDate) {
        log.info("Inside TimeSheetController#getTimeSheetByPid Method");
        LocalDate startDate1 = LocalDate.parse(startDate);
        LocalDate endDate1 = LocalDate.parse(endDate);

        return timeSheetService.getTimeSheetByPid(pid, startDate1, endDate1);
    }


    // Fetch TimeSheets Between two Dates For all Employee

    @ApiOperation(value = "Fetch TimeSheets Between two Dates For all Employee  ",
            response = TimeSheet.class, tags = "DISPLAYING FILLED TIMESHEET")


    @GetMapping("/{startDate}/{endDate}")
    public List<TimeSheet> getTimeSheetsBetweenTwoDateForAllEmp(@PathVariable String startDate,@PathVariable String endDate) {
        log.info("Inside TimeSheetController#getTimeSheetsBetweenTwoDateForAllEmp Method");
        LocalDate startDate1 = LocalDate.parse(startDate);
        LocalDate endDate1 = LocalDate.parse(endDate);
        return timeSheetService.getTimeSheetsBetweenTwoDateForAllEmp(startDate1, endDate1);
    }

}
