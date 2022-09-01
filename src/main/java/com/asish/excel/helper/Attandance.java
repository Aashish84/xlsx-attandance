package com.asish.excel.helper;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Attandance {
	private String infoString;
	private String monthString;
	private List<AttandanceData> attandanceDatas;

	public String getInfoString() {
		return infoString;
	}

	public void setInfoString(String infoString) {
		this.infoString = infoString;
	}

	public String getMonthString() {
		return monthString;
	}

	public void setMonthString(String monthString) {
		this.monthString = monthString;
	}

	public List<AttandanceData> getAttandanceDatas() {
		return attandanceDatas;
	}

	public void setAttandanceDatas(List<AttandanceData> attandanceDatas) {
		this.attandanceDatas = attandanceDatas;
	}

	@Override
	public String toString() {
		return "Attandance [infoString=" + infoString + ", monthString=" + monthString + ", attandanceDatas="
				+ attandanceDatas + "]";
	}

}
