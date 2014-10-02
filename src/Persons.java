import java.util.*;

public class Persons {

	private ArrayList<String> names;
	private ArrayList<ArrayList<String>> numbers;
	private ArrayList<Date> birthdays;
	
	public Persons() {
		names = new ArrayList<String>();
		numbers = new ArrayList<ArrayList<String>>();
		birthdays = new ArrayList<Date>();
	}

	public void addPerson(String name, String number, Date birthday) {
		for (int i = 0; i < getSize(); i++)
			if(getNameAt(i) == name) {
				addNamesNumber(name, number); return;
			}
		names.add(name); birthdays.add(birthday);
		ArrayList<String> list = new ArrayList<String>();
		list.add(number); numbers.add(list);
	}
	public void addPerson(String name, String number) {
		for (int i = 0; i < getSize(); i++)
			if(getNameAt(i) == name) {
				addNamesNumber(name, number); return;
			}
		names.add(name); birthdays.add(null);
		ArrayList<String> list = new ArrayList<String>();
		list.add(number); numbers.add(list);
	}
	public void addNamesNumber(String name, String number) {
		numbers.get(getNamesIndex(name)).add(number);
	}

	public int getNamesIndex(String name) {
		for (int j = 0; j < names.size(); j++)
			if(names.get(j).equals(name))
				return j;
		return 0;
	}
	public String getNameAt(int i) {
		return names.get(i);
	}
	public int getSizeNamesNumbers(String name) {
		return numbers.get(getNamesIndex(name)).size();
	}
	public Date getNamesBirthday(String name) {
		return birthdays.get(getNamesIndex(name));
	}
	public String getNumberOf(String name, int i) {
		return numbers.get(getNamesIndex(name)).get(i);
	}
	public int getSize() {
		return names.size();
	}
	public Date getPersonsBirthday(String name) {
		return birthdays.get(getNamesIndex(name));
	}
	
	public void setName(String name, String new_name) {
		names.set(getSizeNamesNumbers(name), new_name);
	}
	public void setNamesNumber(String name, int position, String number) {
		numbers.get(getNamesIndex(name)).set(position, number);
	}
	public ArrayList<String> getNamesNumbers(String name) {
		return numbers.get(getNamesIndex(name));
	}
	
	public void removePerson(String name) {
		birthdays.remove(getNamesIndex(name));
		numbers.remove(getNamesIndex(name));
		names.remove(getNamesIndex(name));
	}

	public String toString() {
		String temp = new String("********");
		for (int i = 0; i < getSize(); i++) {
			temp += "\nName: " + getNameAt(i); temp += "\tBirthday: ";
			temp += getNamesBirthday(getNameAt(i)); temp += "\n";
			temp += "Numbers: ";
			for (int j = new Integer(0); j < getSizeNamesNumbers(getNameAt(i)); j++) {
				temp += getNumberOf(getNameAt(i), j);
				if (j != getSizeNamesNumbers(getNameAt(i)) - 1)
					temp += ", ";
			} temp += "\n********";
		}
		return temp;
	}
}
