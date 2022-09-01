package com.asish.excel.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asish.excel.entities.AttandanceRecord;
import com.asish.excel.entities.Student;

public interface AttandanceRecordDao extends JpaRepository<AttandanceRecord, Integer> {
	public Optional<AttandanceRecord> findByMonthAndYearAndStudent(String month, int i, Student student);
}
