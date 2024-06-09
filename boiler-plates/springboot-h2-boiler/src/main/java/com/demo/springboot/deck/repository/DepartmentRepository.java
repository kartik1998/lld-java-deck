package com.demo.springboot.deck.repository;

import com.demo.springboot.deck.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findByDepartmentName(String departmentName);

    public Department findByDepartmentNameIgnoreCase(String departmentName);

    @Query("select d from Department d where d.departmentName like ?1%")
    public List<Department> getAllDepartmentsStartingWith(String s);
}
