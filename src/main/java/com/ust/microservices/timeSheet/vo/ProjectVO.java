package com.ust.microservices.timeSheet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO {

    private Integer projectId;
    private String projectType;
    private Integer contributionHrs;
    private String billable;


}
