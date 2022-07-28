package ui.cli.menu;

import java.util.Objects;

/**
 * Menu Item Class that is built to be used with the Menu Class. A menu can hold multiple Menu Items which represent
 * Methods or functions that are called if they are chosen.
 */
public class MenuItem {

    private final int commandNumber;
    private final String commandDescription;
    private final Runnable action;

    public MenuItem(int commandNumber, String commandDescription, Runnable action) {
        this.commandNumber = commandNumber;
        this.commandDescription = commandDescription;
        this.action = action;
    }

    public void performAction(){
        this.action.run();
    }

    public int getCommandNumber() {
        return commandNumber;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public Runnable getAction() {
        return action;
    }

    @Override
    public String toString() {
        return commandNumber + ". " + commandDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(commandNumber, menuItem.commandNumber) && Objects.equals(commandDescription, menuItem.commandDescription) && Objects.equals(action, menuItem.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandNumber, commandDescription, action);
    }
}
