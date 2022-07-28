package ui.cli.readers;

import model.validation.Validator;
import model.validation.exceptions.InvalidInputException;

import java.time.DateTimeException;
import java.util.Scanner;

/**
 * Generic Console reader that is Capable of reading User Console Input. It validates the Input with the Validator it
 * is constructed with. The program goes on until there is a Valid Input from the user.
 * @param <V> Type of Variable to take as an Input from the User.
 */
public class ConsoleInput <V>{

    private final Validator<V> validator;
    private final String query;
    private final Scanner scanner;

    public ConsoleInput(Validator<V> validator, String query, Scanner scanner) {
        this.validator = validator;
        this.query = query;
        this.scanner = scanner;
    }

    public V enterValue(){
        boolean validInput;
        validInput = false;
        V result = null;
        while (!validInput) {
            try {
                System.out.print(this.query);
                String inputString = this.scanner.nextLine();
                result = this.validator.validate(inputString);
                validInput = true;
            } catch (InvalidInputException e) {
                System.out.println(e.toString());
            }
            catch (DateTimeException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
}
