package com.demo.springboot.deck.service;

import com.demo.springboot.deck.entity.Department;
import com.demo.springboot.deck.error.DepartmentNotFoundException;
import com.demo.springboot.deck.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentViaId(Long departmentId) throws DepartmentNotFoundException {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if(!department.isPresent()) throw new DepartmentNotFoundException("Department not available");
        return department.get();
    }

    @Override
    public void deleteDepartmentViaId(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) {
        Department currentDepartment = departmentRepository.findById(departmentId).get();
        if (Objects.nonNull(department.getDepartmentName()) && !department.getDepartmentName().equals("")) {
            currentDepartment.setDepartmentName(department.getDepartmentName());
        }
        if (Objects.nonNull(department.getDepartmentAddress()) && !department.getDepartmentAddress().equals("")) {
            currentDepartment.setDepartmentAddress(department.getDepartmentAddress());
        }
        if (Objects.nonNull(department.getDepartmentCode()) && !department.getDepartmentCode().equals("")) {
            currentDepartment.setDepartmentCode(department.getDepartmentCode());
        }
        return departmentRepository.save(currentDepartment);
    }

    @Override
    public Department fetchDepartmentViaName(String departmentName) {
        return departmentRepository.findByDepartmentNameIgnoreCase(departmentName);
    }
}
