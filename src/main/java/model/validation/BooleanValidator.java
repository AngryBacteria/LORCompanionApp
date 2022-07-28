package model.validation;
import model.validation.exceptions.RegexMismatchException;

/**
 * Validates if the Input is a boolean and maps it to either True or False
 */
public class BooleanValidator implements Validator<Boolean> {

    public final static Validator<Boolean> INSTANCE = new BooleanValidator();
    private final static String trueRegex = "^(?i)([T]|True|Yes|Positive)$";
    private final static String falseRegex = "^(?i)([F]|False|No|Negative)$";

    public Boolean validate(String inputString) throws RegexMismatchException {
        if (inputString.matches(trueRegex)){
            return true;
        }
        if (inputString.matches(falseRegex)){
            return false;
        }
        throw new RegexMismatchException(inputString, "A true or false statement");
    }
}
