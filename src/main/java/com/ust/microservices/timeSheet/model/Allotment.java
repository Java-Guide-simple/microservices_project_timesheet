package com.ust.microservices.timeSheet.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Allotment {

    private Integer aid;
    private Integer employeeId;
    private Integer projectId;
    private Integer contribution;

}
