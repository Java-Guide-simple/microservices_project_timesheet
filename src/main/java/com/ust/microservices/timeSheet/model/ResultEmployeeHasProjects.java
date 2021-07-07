package com.ust.microservices.timeSheet.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEmployeeHasProjects {

    private Employee employee;
    private Set<Project> projects;

}
