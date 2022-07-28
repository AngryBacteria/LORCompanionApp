package model.validation;
import model.validation.exceptions.InvalidInputException;

/**
 * Interface for the Validators
 * @param <V> Type of the Variable
 */
public interface Validator <V> {
    public V validate(String inputString) throws InvalidInputException;
}
