import java.util.Scanner;

public class Main {

	private static Persons contact;
	public static Scanner scan;

	public static void main(String[] args) {
		contact = new Persons("Hi", "654689", new Date(1,  7, 1996));
		contact.addPerson("Fanis", "79503264385", new Date(2,  7, 1996));
		contact.addPerson("Lenar", "79503264388", new Date(10,  6, 1995));
		contact.addPerson("Danis", "79503264389");
		contact.addNamesNumber("Fanis", "79503264386");
		scan = new Scanner(System.in);
		while (true) {
			menuCUI();
			int input = scan.nextInt();
			processEvent(input);
			if(input == 9) break;
		}
	}

	public static void menuCUI() {
		System.out.println("1. Show All Persons");
		System.out.println("2. Add Person");
		System.out.println("3. Edit Person");
		System.out.println("4. Delete Person");
		System.out.println("9. Quit");
	}
	
	public static void processEvent(int input) {
		if (input == 1) System.out.println(contact);
		else if (input == 2) {
			String name, number, month; int day, year;
			System.out.println("Enter Name: "); name = scan.next();
			System.out.println("Enter Number: "); number = scan.next();
			System.out.println("Enter Birthday's day: "); day = scan.nextInt();
			System.out.println("Enter Birthday's month: "); month = scan.next();
			System.out.println("Enter Birthday's year: ");year = scan.nextInt();
			contact.addPerson(name, number, new Date(day,  month, year));
		}
		else if (input == 3) {
			System.out.println("Who would you like to change?");
			String name = scan.next(); 
			System.out.println("What would you like to change?");
			System.out.println("1. Name");
			System.out.println("2. Birthday");
			System.out.println("3. Number");
			int variant = scan.nextInt();
			if (variant == 1) {
				System.out.println("On Whom?");
				String new_name = scan.next(); contact.setName(name, new_name);
			}
			else if (variant == 2) {
				System.out.println("In Which?");
				System.out.println("Enter Birthday's day: ");
				contact.getNamesBirthday(name).setDay(scan.nextInt());
				System.out.println("Enter Birthday's month: ");
				contact.getNamesBirthday(name).setMonth(scan.next());
				System.out.println("Enter Birthday's year: ");
				contact.getNamesBirthday(name).setYear(scan.nextInt());
			}
			else if (variant == 3) {
				System.out.println("1. Add Number");
				System.out.println("2. Edit Number");
				System.out.println("3. Delete Number");
				int variant2 = scan.nextInt();
				if(variant2 == 1) {
					System.out.println("Enter the Number: ");
					String number = scan.next();
					contact.addNamesNumber(name, number);
				}
				else if (variant2 == 2) {
					System.out.println("Person " + name + "has:");
					for (int i = 0; i < contact.getSizeNamesNumbers(name); i++)
						System.out.print(i + ". " + contact.getNumberOf(name, i) + "\n");
					System.out.println("What Number would you like to change? ");
					int position = scan.nextInt();
					System.out.println("Wahat Number would you like to put there? ");
					String number = scan.next();
					contact.setNamesNumber(name, position, number);
				}
				else if (variant2 == 3) {
					System.out.println("Person " + name + "has:");
					for (int i = 0; i < contact.getSizeNamesNumbers(name); i++)
						System.out.print(i + ". " + contact.getNumberOf(name, i) + "\n");
					System.out.println("What Number would you like to delete? ");
					int position = scan.nextInt();
					contact.removeNumber(name, position);
				}
			}
		}
		else if (input == 4) {
			System.out.println("Who would you like to change?"); String name = scan.next();
			contact.removePerson(name);
		}
	}

}
