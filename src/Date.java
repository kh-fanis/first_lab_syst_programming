public class Date {

	private Integer day;
	private Integer month;
	private Integer year;

	public Date(Integer day, Integer month, Integer year) {
		if (day > 0 && day < 32) this.day = day;
		if (month > 0 && month < 13) this.month = month;
		if (year > 0 && year < getToday()) this.year = year;
	}
	public Date(Integer day, String month, Integer year) {
		if (day > 0 && day < 32 &&
				getMonth(month) > 0 && getMonth(month) < 13 &&
				year > 0 && year < getToday()) {
			this.day = day; this.year = year;
			this.month = getMonth(month);
		}
	}
	
	public int getMonth(String month) {
			 if (month.equals("Jan")) return  1;
		else if (month.equals("Feb")) return  2;
		else if (month.equals("Mar")) return  3;
		else if (month.equals("Apr")) return  4;
		else if (month.equals("May")) return  5;
		else if (month.equals("Jun")) return  6;
		else if (month.equals("Jul")) return  7;
		else if (month.equals("Aug")) return  8;
		else if (month.equals("Sep")) return  9;
		else if (month.equals("Oct")) return 10;
		else if (month.equals("Nov")) return 11;
		else if (month.equals("Dec")) return 12;
		try { return Integer.valueOf(month); }
		catch (NumberFormatException var) { return 0; }
	}
	public String getMonth(int month) {
		if (month ==  1) return "Jan";
		else if (month ==  2) return "Feb";
		else if (month ==  3) return "Mar";
		else if (month ==  4) return "Apr";
		else if (month ==  5) return "May";
		else if (month ==  6) return "Jun";
		else if (month ==  7) return "Jul";
		else if (month ==  8) return "Aug";
		else if (month ==  9) return "Sep";
		else if (month == 10) return "Oct";
		else if (month == 11) return "Nov";
		else if (month == 12) return "Dec";
		return null;
	}
	public int getToday() {
		java.util.Calendar calendar = java.util.Calendar.getInstance
				(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
		calendar.setTime(new java.util.Date());
		return calendar.get(java.util.Calendar.YEAR);
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
		this.month = getMonth(month);
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		if (year > -1 && year < getToday()) this.year = year;
	}

	public String toString() {
		String d = day.toString();
		if (day.equals(1)) d = "1st"; else if (day.equals(2)) d = "2nd";
		else d += "th";
		return d + " " + getMonth(month) + " " + year;
	}
}
