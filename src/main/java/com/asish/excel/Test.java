package com.asish.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.asish.excel.dao.AttandanceRecordDao;
import com.asish.excel.dao.StudentDao;
import com.asish.excel.entities.AttandanceRecord;
import com.asish.excel.entities.Student;
import com.asish.excel.helper.Attandance;
import com.asish.excel.helper.AttandanceData;

@Component
public class Test {

	@Autowired
	private AttandanceRecordDao attandanceRecordDao;
	@Autowired
	private StudentDao studentDao;

	public void start(MultipartFile f) {
		try {

//			FileInputStream file = new FileInputStream(new File("c:\\users\\dell\\documents\\Book.xlsx"));

			Workbook workbook = new XSSFWorkbook(f.getInputStream());
			int numberOfSheets = workbook.getNumberOfSheets();

			for (int ns = 0; ns < numberOfSheets; ns++) {

				Attandance attandance = new Attandance();
				List<AttandanceData> attandanceDatas = new ArrayList<>();

				Sheet sheet = workbook.getSheetAt(ns);

				Iterator<Row> rowIterator = sheet.iterator();

				int i = 0;
				while (rowIterator.hasNext()) {

					Row row = rowIterator.next();
					if (i < 3) {
						if (i < 2) {
							Iterator<Cell> cellIterator = row.cellIterator();
							// getting info and month
							while (cellIterator.hasNext()) {
								Cell cell = cellIterator.next();

								String stringCellValue = cell.getStringCellValue();

								if (stringCellValue.length() > 9) {
									attandance.setInfoString(stringCellValue);

								} else if (!stringCellValue.equals("month")) {
									attandance.setMonthString(stringCellValue);

								}
							}
						}
						i++;
						continue;
					}

					AttandanceData attandanceData = new AttandanceData();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					int present_days = 0;
					int x = -1;
					Set<Integer> set = new HashSet<>();
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();

						switch (cell.getCellType()) {

						case NUMERIC:
							int numericCellValue = (int) cell.getNumericCellValue();
							attandanceData.setRoll(numericCellValue);

							break;

						case STRING:
							String stringCellValue = cell.getStringCellValue();
							if (stringCellValue.equals("p")) {
								present_days++;
								set.add(x);
								break;
							}
							if (stringCellValue.equals("f")) {
								break;
							}
							attandanceData.setName(stringCellValue);

							break;

						case BOOLEAN:
							System.out.print(cell.getBooleanCellValue() + "bol ");
							break;

						default:
							System.out.print(cell.getStringCellValue() + "def ");
							break;

						}
						x++;
					}
					attandanceData.setDaySet(set);
					attandanceData.setPresentDay(present_days);

					attandanceDatas.add(attandanceData);
				}
				attandance.setAttandanceDatas(attandanceDatas);
				System.out.println(attandance);
//				filter(attandance);
			}
//			file.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public void filter(Attandance attandance) {
		List<Student> students = new ArrayList<>();
		List<AttandanceRecord> attandanceRecords = new ArrayList<>();

		String[] arr = attandance.getInfoString().split(" ");

		String faculty = arr[0];
		String section = arr[1].substring(1, 2);
		int year = Integer.parseInt(arr[2]);
		String month = attandance.getMonthString();

		for (AttandanceData aData : attandance.getAttandanceDatas()) {
			Student student = new Student();
			student.setId(faculty + "-" + section + "-" + aData.getRoll());
			student.setStudentName(aData.getName());
			student.setFaculty(faculty);
			student.setSection(section);

			AttandanceRecord attandanceRecord = new AttandanceRecord();
			attandanceRecord.setMonth(month);
			attandanceRecord.setPresentDays(aData.getPresentDay());
			attandanceRecord.setYear(year);
			attandanceRecord.setStudent(student);

			students.add(student);
			attandanceRecords.add(attandanceRecord);
		}

		int save = save(attandanceRecords, students);
		System.out.println(save);
	}

	public int save(List<AttandanceRecord> attandanceRecords, List<Student> students) {
		for (Student student : students) {
			Optional<Student> findById = studentDao.findById(student.getId());
			if (findById.isEmpty()) {
				studentDao.save(student);
			}
		}
		int size = 0;
		for (AttandanceRecord attandanceRecord : attandanceRecords) {
			Optional<AttandanceRecord> findByMonthAndYearAndStudent = attandanceRecordDao.findByMonthAndYearAndStudent(
					attandanceRecord.getMonth(), attandanceRecord.getYear(), attandanceRecord.getStudent());
			if (findByMonthAndYearAndStudent.isEmpty()) {
				attandanceRecordDao.save(attandanceRecord);
				size++;
			}
		}
		return size;
	}
}
