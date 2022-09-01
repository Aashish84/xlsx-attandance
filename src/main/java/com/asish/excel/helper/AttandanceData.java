package com.asish.excel.helper;

import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class AttandanceData {
	private int roll;
	private String name;
	private int presentDay;
	private Set<Integer> daySet;

	public Set<Integer> getDaySet() {
		return daySet;
	}

	public void setDaySet(Set<Integer> daySet) {
		this.daySet = daySet;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPresentDay() {
		return presentDay;
	}

	public void setPresentDay(int presentDay) {
		this.presentDay = presentDay;
	}

	@Override
	public String toString() {
		return "AttandanceData [roll=" + roll + ", name=" + name + ", presentDay=" + presentDay + ", daySet="
				+ daySet.toString() + "]";
	}

}
