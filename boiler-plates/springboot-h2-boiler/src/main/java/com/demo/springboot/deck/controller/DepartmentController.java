package com.demo.springboot.deck.controller;

import com.demo.springboot.deck.entity.Department;
import com.demo.springboot.deck.error.DepartmentNotFoundException;
import com.demo.springboot.deck.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    private final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping("/departments")
    public Department saveDepartment(@Valid @RequestBody Department department) {
        log.info("save department called");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/departments")
    public List<Department> getDepartments() {
        log.info("get department called");
        return departmentService.getDepartments();
    }

    @GetMapping("/departments/{id}")
    public Department getDepartmentViaId(@PathVariable("id") Long departmentId) throws DepartmentNotFoundException {
        log.info("get department via id called");
        return departmentService.getDepartmentViaId(departmentId);
    }

    @DeleteMapping("/departments/{id}")
    public String deleteDepartmentViaId(@PathVariable("id") Long departmentId) {
        departmentService.deleteDepartmentViaId(departmentId);
        String result = "Department {" + departmentId + "} deleted successfully";
        log.info(result);
        return result;
    }

    @PutMapping("/departments/{id}")
    public Department updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department) {
        log.info("update department called");
        return departmentService.updateDepartment(departmentId, department);
    }

    @GetMapping("/departments/name/{name}")
    public Department fetchDepartmentViaName(@PathVariable("name") String departmentName) {
        log.info("fetch department via name called");
        return departmentService.fetchDepartmentViaName(departmentName);
    }
}
