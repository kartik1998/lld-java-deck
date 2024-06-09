package com.demo.springboot.deck.service;

import com.demo.springboot.deck.entity.Department;
import com.demo.springboot.deck.error.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {
    public Department saveDepartment(Department department);

    public List<Department> getDepartments();

    public Department getDepartmentViaId(Long departmentId) throws DepartmentNotFoundException;

    public void deleteDepartmentViaId(Long departmentId);

    public Department updateDepartment(Long departmentId, Department department);

    public Department fetchDepartmentViaName(String departmentName);
}
