package com.ust.microservices.timeSheet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.microservices.timeSheet.model.TimeSheet;
import com.ust.microservices.timeSheet.repository.TimeSheetRepository;
import com.ust.microservices.timeSheet.service.TimeSheetService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TimeSheetApplicationTests {


    @MockBean
    private TimeSheetRepository repo;

    @MockBean
    private TimeSheetService service;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TimeSheetModelTest() {
        TimeSheet timeSheet = Mockito.mock(TimeSheet.class);
        assertNotNull(timeSheet);
    }


    @Test
    public void testAddProduct() throws Exception {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        TimeSheet timeSheet = new TimeSheet(2, 10, 56, "WFH", 4, "yes", yesterday);

        Mockito.when(service.saveTimeSheet(ArgumentMatchers.any())).thenReturn(timeSheet);

        String json_content = mapper.writeValueAsString(timeSheet);

        mockMvc.perform(post("/api/timesheet/submit-new-timesheet-daywise")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json_content)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
               // .andExpect(jsonPath("$.tid", Matchers.equalTo(2)));


    }

}
