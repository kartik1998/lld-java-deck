package com.demo.springboot.deck.service;

import com.demo.springboot.deck.entity.Department;
import com.demo.springboot.deck.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                .departmentName("IT").departmentAddress("Ahmedabad").departmentCode("IT-06").departmentId(1L).build();

        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("IT")).thenReturn(department);
    }

    // should return a valid department when a valid department name is send as an argument
    @Test
    @DisplayName("should return valid deparment for a valid department name")
    // @Disabled (used to disable a test like xit in mocha)
    public void fetchDepartmentViaName_positive_test() {
        String departmentName = "IT";
        Department foundDepartment = departmentService.fetchDepartmentViaName(departmentName);
        assertEquals(departmentName, foundDepartment.getDepartmentName());
    }

    @Test
    @DisplayName("should return an empty list of departments")
    public void getDepartments_empty_list() {
        List<Department> departments = departmentService.getDepartments();
        assertEquals(departments.size(), 0);
    }

    @Test
    @DisplayName("should return an list of departments")
    @Disabled
    public void getDepartments_populated_list() {
        departmentService.saveDepartment(Department.builder().departmentName("IT").departmentCode("ITXA").departmentAddress("MECHC").build());
        List<Department> departments = departmentService.getDepartments();
        assertEquals(departments.size(), 1);
        assertEquals(departments.get(0).getDepartmentName(), "IT");
    }
}