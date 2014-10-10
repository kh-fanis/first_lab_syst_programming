import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Persons {
	private ArrayList<Integer> personsID;
	private ArrayList<ArrayList<Integer>> numbersID;
	private ArrayList<String> names;
	private ArrayList<ArrayList<String>> numbers;
	private ArrayList<Date> birthdays;
	
	private Connection connection;
	

	public Persons() {
		names = new ArrayList<String>();
		numbers = new ArrayList<ArrayList<String>>();
		birthdays = new ArrayList<Date>();
		connect();
	}
	public Persons(String name, String number) {
		names = new ArrayList<String>();
		numbers = new ArrayList<ArrayList<String>>();
		birthdays = new ArrayList<Date>();
		connect();
		addPerson(name, number);
	}
	public Persons(String name, String number, Date birthday) {
		names = new ArrayList<String>();
		numbers = new ArrayList<ArrayList<String>>();
		birthdays = new ArrayList<Date>();
		connect();
		addPerson(name, number, birthday);
	}
	public Persons(String name, Date birthday) {
		names = new ArrayList<String>();
		numbers = new ArrayList<ArrayList<String>>();
		birthdays = new ArrayList<Date>();
		connect();
		addPerson(name, birthday);
	}
	private void connect() {
		personsID = new ArrayList<Integer>();
		numbersID = new ArrayList<ArrayList<Integer>>();
		try {
			Class.forName("org.sqlite.JDBC");
			//System.out.println(this.getClass().getResource("").getPath() + "address_book.db");
            //connection = DriverManager.getConnection("jdbc:sqlite:" +
            //		this.getClass().getResource("").getPath() + "address_book.db");
			connection = DriverManager.getConnection("jdbc:sqlite:address_book.db");
			connection.setAutoCommit(false); refeshData();
		} catch (Exception e) {
			System.err.println("Database isn't available " +
					e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} System.out.println("Opened database successfully");
	}
	public void refeshData() {
		java.sql.Statement state;
		try {
			state = connection.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM Persons;");
			while (rs.next()) {
				personsID.add(rs.getInt("id")); names.add(rs.getString("name"));
				birthdays.add(new Date(rs.getInt("birthday"),
						rs.getInt("birthmonth"), rs.getInt("birthyear")));
				ArrayList<String> list = new ArrayList<String>();
				list.add(null); numbers.add(list);

				ResultSet rs2;
				java.sql.Statement state2 = connection.createStatement();
				rs2 = state2.executeQuery("SELECT * FROM Numbers WHERE id_of_person=" +
						rs.getInt("id") +";");
				ArrayList<Integer> list2 = new ArrayList<Integer>();
				while (rs2.next()) {
					if(numbers.get(getNamesIndex(rs.getString("name"))).get(0) == null) {
						numbers.get(getNamesIndex(rs.getString("name"))).set(0, rs2.getString("number"));
					}
					numbers.get(getNamesIndex(rs.getString("name"))).add(rs2.getString("number"));
					list2.add(rs2.getInt("id"));
				} numbersID.add(list2); rs2.close(); state2.close();

			} rs.close(); state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int looForNamesID(String name) {
		return personsID.get(getNamesIndex(name));
	}
	private int lastNumber() {
		try {
			java.sql.Statement state = connection.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM Numbers");
			int id = 0; while (rs.next()) { id = rs.getInt("id"); };
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private boolean addPersonToDB(String name, String number, Date birthday) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "INSERT INTO Persons (id,name,birthday,birthmonth,birthyear) " +
					"VALUES (" + personsID.size() + ", '" + name + "', " + birthday.getDay() +
					", " + birthday.getMonth() + ", " + birthday.getYear() + ");";
			state.executeUpdate(sql);
			personsID.add(personsID.size());
			
			java.sql.Statement state2 = connection.createStatement();
			ResultSet rs = state2.executeQuery("SELECT * FROM Numbers");
			int id = lastNumber() + 1; rs.close(); state2.close();
			numbersID.add(new ArrayList<Integer>(id));
			sql = "INSERT INTO Numbers (id, id_of_person, number)" +
					"VALUES(" + id + ", " + (personsID.get(getSize() - 1) + 1) + ", " + number + ");";
			state.executeUpdate(sql); state.close(); connection.commit();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean addPersonToDB(String name, String number) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "INSERT INTO Persons (id,name,birthday,birthmonth,birthyear) " +
					"VALUES (" + personsID.size() + ", '" + name + "', null, null, null);";
			state.executeUpdate(sql);
			personsID.add(personsID.size());
			
			java.sql.Statement state2 = connection.createStatement();
			ResultSet rs = state2.executeQuery("SELECT * FROM Numbers");
			int id = 0; while (rs.next()) { id = rs.getInt("id"); } id += 1;
			rs.close(); state2.close(); numbersID.add(new ArrayList<Integer>(id));
			sql = "INSERT INTO Numbers (id, id_of_person, number)" +
					"VALUES(" + id + ", " + (personsID.get(getSize() - 1) + 1) + ", " + number + ");";
			state.executeUpdate(sql); state.close(); connection.commit();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean addPersonToDB(String name, Date birthday) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "INSERT INTO Persons (id,name,birthday,birthmonth,birthyear) " +
					"VALUES (" + personsID.size() + ", '" + name + "', " + birthday.getDay() +
					", " + birthday.getMonth() + ", " + birthday.getYear() + ");";
			state.executeUpdate(sql); state.close(); connection.commit(); personsID.add(personsID.size());
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean addNamesNumberToDB(String name, String number) {
		try {
			java.sql.Statement state = connection.createStatement(); int id = lastNumber() + 1;
			String sql = "INSERT INTO Numbers (id, id_of_person, number) " +
					"VALUES (" + id + ", " + personsID.get(getNamesIndex(name)) +
							", " + number + ");";
			state.executeUpdate(sql); state.close(); connection.commit();
			numbersID.get(getNamesIndex(name)).add(id);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean setPersonsBirthdayToDB(String name, int day, String month, int year) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "UPDATE Persons SET birthday = " + day
					+ ", birthmonth = " + month + ", birthyear = " + year + " WHERE id = "
					+ personsID.get(getNamesIndex(name)) + ";";
			state.executeUpdate(sql); state.close(); connection.commit(); personsID.add(personsID.size());
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean setNameToDB(String name, String new_name) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "UPDATE Persons SET name = '" + new_name + "' WHERE name = '" + name + "';";
			state.executeUpdate(sql); state.close(); connection.commit(); personsID.add(personsID.size());
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean setNamesNumberToDB(String name, int position, String number) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "UPDATE Numbers SET number = '" + number + "' WHERE id = '"
			+ numbersID.get(getNamesIndex(name)).get(position) + "';";
			state.executeUpdate(sql); state.close(); connection.commit(); personsID.add(personsID.size());
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean removePersonFromDB(String name) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "DELETE FROM persons WHERE name = '" + name + "';";
			state.executeUpdate(sql);

			for (int i = 0; i < numbersID.get(getNamesIndex(name)).size(); i++) {
				sql = "DELETE FROM Numbers WHERE id =" + numbersID.get(getNamesIndex(name)).get(i) + ";";
				state.executeUpdate(sql);
			} personsID.remove(getNamesIndex(name)); numbersID.remove(getNamesIndex(name));
			state.close(); connection.commit();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}
	private boolean removeNumberFromDB(String name, int position) {
		try {
			java.sql.Statement state = connection.createStatement();
			String sql = "DELETE FROM Numbers WHERE id ="
			+ numbersID.get(getNamesIndex(name)).get(position) + ";";
			state.executeUpdate(sql); state.close(); connection.commit();
			numbersID.get(getNamesIndex(name)).remove(position);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
	}

	public void addPerson(String name, String number, Date birthday) {
		if(addPersonToDB(name, number, birthday)) {
			for (int i = 0; i < getSize(); i++)
				if(getNameAt(i) == name) {
					addNamesNumber(name, number);
					System.out.println("You've such person yet!"); return;
				}
			names.add(name); birthdays.add(birthday);
			ArrayList<String> list = new ArrayList<String>();
			list.add(number); numbers.add(list); return;
		} System.out.println("Couldn't add data!");
	}
	public void addPerson(String name, String number) {
		if(addPersonToDB(name, number)) {
			for (int i = 0; i < getSize(); i++)
				if(getNameAt(i) == name) {
					addNamesNumber(name, number);
					System.out.println("You've such person yet!"); return;
				}
			names.add(name); birthdays.add(null);
			ArrayList<String> list = new ArrayList<String>();
			list.add(number); numbers.add(list);
			addPersonToDB(name, number); return;
		} System.out.println("Couldn't add data!");
	}
	public void addPerson(String name, Date birthday) {
		if(addPersonToDB(name, birthday)) {
			for (int i = 0; i < getSize(); i++)
				if(getNameAt(i) == name) {
					System.out.println("You've such person yet!"); return;
				}
			names.add(name); birthdays.add(birthday);
			ArrayList<String> list = new ArrayList<String>();
			list.add(null); numbers.add(list);
			addPersonToDB(name, birthday); return;
		} System.out.println("Couldn't add data!");
	}
	public void addNamesNumber(String name, String number) {
		if(addNamesNumberToDB(name, number)) {
			if(numbers.get(getNamesIndex(name)).get(0) == null) {
				numbers.get(getNamesIndex(name)).set(0, number); return;
			}
			numbers.get(getNamesIndex(name)).add(number);
		}
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
	public String getNumberOf(String name, int i) {
		return numbers.get(getNamesIndex(name)).get(i);
	}
	public int getSize() {
		return names.size();
	}
	public void setPersonsBirthday(String name, int day, String month, int year) {
		if(setPersonsBirthdayToDB(name, day, month, year)) {
			birthdays.get(getNamesIndex(name)).setDay(day);
			birthdays.get(getNamesIndex(name)).setMonth(month);
			birthdays.get(getNamesIndex(name)).setYear(year); return;
		} System.err.println("Couldn't Set Birthday!");
	}
	public String getPersonsBirthday(String name) {
		return birthdays.get(getNamesIndex(name)).toString();
	}

	public void setName(String name, String new_name) {
		if(setNameToDB(name, new_name)){
			names.set(getNamesIndex(name), new_name); return;
		} System.err.println("Couldn't Set Name!");
	}
	public void setNamesNumber(String name, int position, String number) {
		if(setNamesNumberToDB(name, position, number)) {
			numbers.get(getNamesIndex(name)).set(position, number); return;
		} System.err.println("Couldn't Set Number");
	}
	public ArrayList<String> getNamesNumbers(String name) {
		return numbers.get(getNamesIndex(name));
	}
	public int getNamesNumbersSize(String name) {
		return numbers.get(getNamesIndex(name)).size();
	}
	
	public void removePerson(String name) {
		if (removePersonFromDB(name)) {
			birthdays.remove(getNamesIndex(name));
			numbers.remove(getNamesIndex(name));
			names.remove(getNamesIndex(name)); return;
		} System.out.println("Couldn't Remove!");
	}
	public void removeNumber(String name, int position) {
		if (removeNumberFromDB(name, position)) {
			getNamesNumbers(name).remove(position); return;
		} System.err.println("Couldn't Remove Number!");
	}

	public String toString() {
		String temp = new String("********");
		for (int i = 0; i < getSize(); i++) {
			temp += "\nName: " + getNameAt(i); temp += "\tBirthday: ";
			temp += birthdays.get(i); temp += "\n";
			temp += "Numbers: ";
			for (int j = new Integer(0); j < getSizeNamesNumbers(getNameAt(i)); j++) {
				temp += getNumberOf(getNameAt(i), j);
				if (j != getSizeNamesNumbers(getNameAt(i)) - 1)
					temp += ", ";
			} temp += "\n********";
		}
		return temp;
	}
	
	public void close() {
		try {connection.close();} catch (SQLException e) {}
	}
}
