package ui.cli.menu;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import model.validation.IntRangeValidator;
import ui.cli.cliHelper;
import ui.cli.mainCLI;
import ui.cli.readers.ConsoleInput;

/**
 * Menu Class to display a User friendly choice menu in a CLI application.
 * The Menu Item "0" is standard for every Menu and it will either Exit out of the program or if there is a top Level
 * Menu it will switch to that
 */
public class Menu implements Runnable{

	private final Map<Integer, MenuItem> menuItemMap;
	private boolean running;
	private final Scanner scanner;
	private final int level;

	public Menu(Scanner scanner, int level) {
		this.menuItemMap = new TreeMap<>();
		this.level = level;

		if (level == 0)
			this.addMenuItem(new MenuItem(0, "exit", this::exit));
		else
			this.addMenuItem(new MenuItem(0, "Return to upper level", () -> {}));
		this.running = true;
		this.scanner = scanner;
	}

	public void addMenuItem(MenuItem menuItem){

		if (this.menuItemMap.containsKey(menuItem.getCommandNumber())){
			throw new IllegalArgumentException("Menu item name (Key) exists already");
		}
		else
			this.menuItemMap.put(menuItem.getCommandNumber(), menuItem);
	}

	public void addMenuItems(List<MenuItem> items){

		for (MenuItem item : items){
			if (this.menuItemMap.containsKey(item.getCommandNumber())){
				throw new IllegalArgumentException("Menu item name (Key) exists already");
			}
			else
				this.menuItemMap.put(item.getCommandNumber(), item);
		}

	}

	@Override
	public void run() {

		do {
			this.displayMenuItems();
			ConsoleInput<Integer> input = new ConsoleInput<>(new IntRangeValidator(this.menuItemMap.size()-1, 0), "Enter a command: ", this.scanner);
			int commandNumber = input.enterValue();

			if (this.menuItemMap.containsKey(commandNumber))
				this.menuItemMap.get(commandNumber).performAction();
			else{
				System.out.println("What you wanted to do doesn't exist. Please chose a number from the commands :)");
			}
			if (commandNumber == 0)
				this.running = false;
		} while (this.running);
	}

	public void displayMenuItems(){
		System.out.println(cliHelper.spacer);
		this.menuItemMap.values().forEach(System.out::println);
		System.out.println(cliHelper.spacer);
	}

	public void exit(){
		this.scanner.close();
		System.out.println("Danke fürs Benutzen");
		System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
				"░░░█▀▀▀░█▀▀▀░░█▀▀░▀▀█░░█░░░░\n" +
				"░░░█░▀█░█░▀█░░█▀▀░▄▀░░░▀░░░░\n" +
				"░░░▀▀▀▀░▀▀▀▀░░▀▀▀░▀▀▀░░▀░░░░");
	}

	public static void main(String[] args) {

		//mainMenu
		Menu mainMenu = new Menu(mainCLI.pubScanner, 0);


		//Submenu1
		Menu submenu1 = new Menu(mainMenu.scanner, 1);
		submenu1.addMenuItem(new MenuItem(1, "sout hello", () -> System.out.println("hello")));

		//Submenu2
		Menu submenu2 = new Menu(mainMenu.scanner, 1);
		submenu2.addMenuItem(new MenuItem(1, "sout hello", () -> System.out.println("hello")));

		mainMenu.addMenuItem(new MenuItem(1, "Submenu1", submenu1));
		mainMenu.addMenuItem(new MenuItem(2, "Submenu2", submenu2));

		System.out.println(System.getProperty("user.home"));
		System.out.println(File.separator);

		mainMenu.run();

	}
}
