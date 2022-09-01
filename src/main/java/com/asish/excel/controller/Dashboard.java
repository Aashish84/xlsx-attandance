package com.asish.excel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asish.excel.Test;
import com.asish.excel.dao.AttandanceRecordDao;
import com.asish.excel.dao.StudentDao;
import com.asish.excel.entities.AttandanceRecord;
import com.asish.excel.entities.Student;

@Controller
@RequestMapping("/")
public class Dashboard {
	@Autowired
	private Test test;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private AttandanceRecordDao attandanceRecordDao;

	@GetMapping
	public String dashboard() {
		return "/index";
	}

	@ResponseBody
	@PostMapping("/addtest")
	public String yourData(@RequestParam("file") MultipartFile file) {
		String fname = file.getOriginalFilename();
		String fextension = file.getOriginalFilename().substring(fname.lastIndexOf(".") + 1);
		if (!fextension.equals("xlsx")) {
			return "invalid";
		}
		test.start(file);
		return fextension;
	}

	@GetMapping("/all")
	public String viewAll(Model m) {
		List<Student> findAll = studentDao.findAll();
		List<AttandanceRecord> findAll2 = attandanceRecordDao.findAll();
		m.addAttribute("allstudent", findAll);
		m.addAttribute("attandance", findAll2);
		return "all";
	}
}
