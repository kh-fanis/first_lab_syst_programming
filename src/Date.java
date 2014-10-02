public class Date {

	private Integer day;
	private Integer month;
	private Integer year;
	
	public Date(Integer day, Integer month, Integer year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		if (day > 0 && day < 32) this.day = day;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		if (month > 0 && month < 13) this.month = month;
	}
	public void setMonth(String month) {
		int m = this.month;
		if (month ==  "Jan") m = 1; else if (month == "Feb") m = 2;
		else if (month == "Mar") m =  3; else if (month == "Apr") m =  4;
		else if (month == "May") m =  5; else if (month == "Jun") m =  6;
		else if (month == "Jul") m =  7; else if (month == "Aug") m =  8;
		else if (month == "Sep") m =  9; else if (month == "Oct") m = 10;
		else if (month == "Nov") m = 11; else if (month == "Dec") m = 12;
		if (m > 0 && m < 13) this.month = m;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		java.util.Calendar calendar = java.util.Calendar.getInstance
				(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
		calendar.setTime(new java.util.Date());
		if (year > -1 && year < calendar.get(java.util.Calendar.YEAR)) this.year = year;
	}

	@Override
	public String toString() {
		String m = month.toString();
		if (month ==  1) m = "Jan"; else if (month == 2) m = "Feb";
		else if (month ==  3) m = "Mar"; else if (month ==  4) m = "Apr";
		else if (month ==  5) m = "May"; else if (month ==  6) m = "Jun";
		else if (month ==  7) m = "Jul"; else if (month ==  8) m = "Aug";
		else if (month ==  9) m = "Sep"; else if (month == 10) m = "Oct";
		else if (month == 11) m = "Nov"; else if (month == 12) m = "Dec";
		String d = day.toString();
		if (day == 1) d = "1st"; else if (day == 2) d = "2nd";
		else d += "th";
		return d + " " + m + " " + year;
	}
}
