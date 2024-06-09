package com.demo.springboot.deck.controller;

import com.demo.springboot.deck.entity.Department;
import com.demo.springboot.deck.error.DepartmentNotFoundException;
import com.demo.springboot.deck.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentAddress("Ahmedabad")
                .departmentCode("IT - 06")
                .departmentName("IT")
                .departmentId(1L)
                .build();
    }

    @Test
    @DisplayName("should save a department")
    void saveDepartment() throws Exception {
        Department inputDepartment = Department.builder()
                .departmentAddress("Ahmedabad")
                .departmentCode("IT - 06")
                .departmentName("IT")
                .departmentId(1L)
                .build();
        Mockito.when(departmentService.saveDepartment(inputDepartment)).thenReturn(inputDepartment);
        mockMvc.perform(MockMvcRequestBuilders.post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"departmentName\": \"IT\",\n" +
                        "  \"departmentCode\": \"IT - 06\",\n" +
                        "  \"departmentAddress\": \"Ahmedabad\"\n" +
                        "}\n"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("should return a department via id")
    void getDepartmentViaId() throws Exception {
        Mockito.when(departmentService.getDepartmentViaId(1L)).thenReturn(department);
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value(department.getDepartmentName()));
    }
}