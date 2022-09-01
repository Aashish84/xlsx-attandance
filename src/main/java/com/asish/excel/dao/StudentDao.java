package com.asish.excel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asish.excel.entities.Student;

public interface StudentDao extends JpaRepository<Student, String> {
}
