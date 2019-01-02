package com.revature.service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.InvalidEntryException;
import com.revature.pojos.Account;
import com.revature.pojos.User;

public class ServiceFunctions {
	
	//CHECKS IF THE USER CHOOSES TO REGISTER OR SIGN IN
	public static void start() {
		//SCANNER - Register or email
		Scanner sc = new Scanner(System.in);
		String in = sc.nextLine();
		if (in.toLowerCase().equals("r") || in.toLowerCase().equals("register")) {
			newUser();
		}
		else { 
			User u = checkPassword(in);
			Account a = findAccount(u);
			userFun(a);
		}
	}
	
	//FUNCTION TO CREATE NEW USER
	public static void newUser() {
		System.out.println("Welcome New User!");
		User u = new User();
		System.out.print("Enter an email: ");
		Scanner sc = new Scanner(System.in);
		String email = sc.nextLine();

		//INPUT VALIDATION, CHECK IS USER EXISTS
		while (email.equals("") || existUser(email)) {
			System.out.println("Must Enter a Unique Email!");
			email = sc.nextLine();
		}
		
		System.out.print("Enter a password: ");
		String password = sc.nextLine();
		while (password.equals("")) {
			System.out.println("Must Enter a Valid Password!");
			password = sc.nextLine();
		}
		
		System.out.print("What is your First Name: ");
		String fn = sc.nextLine();
		while (fn.equals("")) {
			System.out.println("Must Enter a Name!");
			fn = sc.nextLine();
		}
		
		System.out.print("What is your Last Name: ");
		String ln = sc.nextLine();
		while (ln.equals("")) {
			System.out.println("Must Enter a Name!");
			ln = sc.nextLine();
		}
		
		//SETS FIELDS IN NEW USER
		u.setEmail(email);
		u.setPassword(password);
		u.setFirstName(fn);
		u.setLastName(ln);
		
		//Write to database
		UserDao uDao = new UserDao();
		uDao.insert(u);
		
		//CALLS NEW ACCOUNT FUNCTION
		newAccount(u);
	}
	
	//CHECKS IF USER EXISTS
	public static boolean existUser(String email) {
		UserDao uDao = new UserDao();
		List<User> users = uDao.findAll();
		for (User u : users) {
			if (u.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	//PRINTS MENU
	public static void printMenu() {
		System.out.println("##############################################################");
		System.out.println("#------------------------------------------------------------#");
		System.out.println("#| Please select an option:                                 |#");
		System.out.println("#| 1) Make a deposit                                        |#");
		System.out.println("#| 2) Withdraw funds                                        |#");
		System.out.println("#| 3) Check your balance                                    |#");
		System.out.println("#| 4) Create a new account                                  |#");
		System.out.println("#| 5) Quit Bank App                                         |#"); 
		System.out.println("#------------------------------------------------------------#");
	}
	
	//CHECKS PASSWORD, OR ASKS TO RE-INPUT EMAIL
	public static User checkPassword(String email) {
		UserDao uDao = new UserDao();
		User u = uDao.findByEmail(email);
		Scanner sc = new Scanner(System.in);
		while (u == null) {
			System.out.println("Enter a valid email:");
			email = sc.nextLine();
			u = uDao.findByEmail(email);
		}
		System.out.println("Enter your password: ");
		String pass = sc.nextLine();
		while (pass.equals(u.getPassword()) == false) {
			System.out.println("Wrong password!");
			System.out.print("Enter again (or press q to re-enter email):");
			pass = sc.nextLine();
			if (pass.toLowerCase().equals("q")) {
				System.out.println("Enter your email address:");
				String e2 = sc.nextLine();
				u = checkPassword(e2);
				return u;
			}
		}
		return u;
	}
	
	//MAIN USER FUNCTION THAT PRINTS MENU, ASKS FOR COMMANDS
	public static void userFun(Account a) {
		UserDao uDao = new UserDao();
		User u = uDao.findByID(a.getUserID());
		printMenu();
		Scanner sc = new Scanner(System.in);
		String opt = sc.nextLine();
		switch (opt) {
			case "1":
				deposit(a);
				break;
			case "2":	
				withdraw(a);
				break;
			case "3":
				checkBalance(a);
				break;
			case "4":
				newAccount(u);
				break;
			case "5":
				System.exit(0);
			case "Q":
			case "q":
				a=findAccount(u);
				userFun(a);
				break;
			default :
				System.out.println("Not a valid input!");
				userFun(a);
		}		
	}
	
	//FINDS ACCOUNTS FOR EACH USER, STORES IN ARRAY OF ACCOUNTS
	public static Account findAccount(User u) {
		int id = u.getId();
		AccountDao aDao = new AccountDao();
		List<Account> accounts = aDao.findAll();
		Account useracts[] = new Account[3];
		System.out.println("Which account would you like?");
 		for (Account a : accounts) {
			if (a.getUserID()==id) {
				if (a.getType()==1) {
					System.out.println("1) Savings");
					useracts[0]=a;
				}
				if (a.getType()==2) {
					System.out.println("2) Checking");
					useracts[1]=a;
				}
				if (a.getType()==3) {
					System.out.println("3) Credit");
					useracts[2]=a;
				}
			}
		}
 		//Selecting account type
 		Scanner sc = new Scanner(System.in);
 		System.out.println("Or press Q to quit");
 		String opt = sc.nextLine();
 		Account a = new Account();
 		opt = opt.toLowerCase();
 		switch (opt) {
 			case "1" :
 				if (useracts[0]==null) {
 					System.out.println("Select correct account type!");
 					a=findAccount(u);
 					return a;
 				}
 				return useracts[0];
 			case "2" :
 				if (useracts[1]==null) {
 					System.out.println("Select correct account type!");
 					a=findAccount(u);
 					return a;
 				}
 				return useracts[1];
 			case "3" :
 				if (useracts[2]==null) {
 					System.out.println("Select correct account type!");
 					a=findAccount(u);
 					return a;
 				}
 				return useracts[2];
 			case "quit" :
 			case "q" :
 				System.exit(0);
 			default :
 				System.out.println("Select correct account type!");
 				a = findAccount(u);
 				return a;		
 		}
	}
	
	//DEPOSIT FUNCTION
	public static void deposit (Account a) {
		System.out.println("How much money would you like to deposit?");
		Scanner sc = new Scanner(System.in);
		double amount;
		try {
			amount = sc.nextDouble();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Enter valid amount!");
			sc.nextLine();
			amount = sc.nextDouble();
		}
		while (amount <= 0) {
			try {
				throw new InvalidEntryException();
			} catch (InvalidEntryException e) {
				System.out.println("Enter positive number!");
				amount = sc.nextDouble();
			}
		}
		if (a.getType()==1) {
			System.out.println("Interest Added!");
			a.setBalance(a.getBalance()+(amount*1.1));
		}
		else {
			a.setBalance(a.getBalance()+amount);
		}
		System.out.println("Success! Current Balance = " + a.getBalance());
		System.out.println("Press Enter to continue");
		
		//WRITE TO DATABASE
		AccountDao aDao = new AccountDao();
		aDao.update(a);
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	
		userFun(a);
	}
	
	//WITHDRAW METHOD
	public static void withdraw (Account a) {
		Scanner sc = new Scanner(System.in);
		if (a.getBalance() == 0 && a.getType()!=3) {
			System.out.println("Must have funds to withdraw!");
			sc.nextLine();
			userFun(a);
			return;
		}
		System.out.println("How much money would you like to withdraw?");
		double amount;
		try {
			amount = sc.nextDouble();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Enter valid amount!");
			sc.nextLine();
			amount = sc.nextDouble();
		}
		while (amount < 0) {	
			System.out.println();
			System.out.println("Enter positive number!");
			amount = sc.nextDouble();
		}
		while (a.getBalance()<amount) {
			if (a.getType()==3) {
				System.out.println("OVERDRAW FEE DEDUCTED");
				a.setBalance(a.getBalance()-10);
				break;
			}
			else {
				System.out.println("Invalid Entry, try again");
				amount = sc.nextDouble();
			}
		}
		a.setBalance(a.getBalance()-amount);
		System.out.println("Dispensing Cash. Current Balance = " + a.getBalance());
		System.out.println("Press Enter to continue");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		
		//WRITE TO DATABASE
		AccountDao aDao = new AccountDao();
		aDao.update(a);
		userFun(a);
	}
	
	//CHECK BALANCE FUNCTION
	public static void checkBalance (Account a) {
		System.out.println("Your balance is: "+a.getBalance());
		System.out.println("Press Enter to continue");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		userFun(a);
	}
	
	//CREATES NEW ACCOUNT
	public static void newAccount (User u) {
		int id = u.getId();
		AccountDao aDao = new AccountDao();
		List<Account> accounts = aDao.findAll();
		Account useracts[] = new Account[3];
 		for (Account a : accounts) {
			if (a.getUserID()==id) {
				if (a.getType()==1) {
					useracts[0]=a;
				}
				if (a.getType()==2) {
					useracts[1]=a;
				}
				if (a.getType()==3) {
					useracts[2]=a;
				}
			}
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("What type of account would you like?");
		System.out.println("1) Savings");
		System.out.println("2) Checking");
		System.out.println("3) Credit");
		Account a = new Account();
		String type = sc.nextLine();
		boolean tof = true;
		switch (type) {
		case "1" :
			if (useracts[0]!=null) {
				System.out.println("ERROR! Cannot have more than one Savings Account");
				tof = false;
			}
			else {
				a.setType(1);
			}
			break;
		case "2" :
			if (useracts[1]!=null) {
				System.out.println("ERROR! Cannot have more than one Checking Account");
				tof = false;
			}
			else {
				a.setType(2);
			}
			break;
		case "3" :
			if (useracts[2]!=null) {
				System.out.println("ERROR! Cannot have more than one Credit Account");
				tof = false;
			}
			else {
				a.setType(3);
			}
			break;
		case "Q":
		case "q":
			System.exit(0);
		default :
			System.out.println("Enter a valid option!");
			newAccount(u);
		}
		if (tof) {
			a.setBalance(0);
			a.setUserID(u.getId());
		
			//write to database insert(a)
			aDao.insert(a);
			userFun(a);
		}
		else {
			sc.nextLine();
			newAccount(u);
		}
		
	}

}
