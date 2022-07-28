package model.validation;
import model.validation.exceptions.InvalidInputException;
import model.validation.exceptions.NotInRangeException;
import model.validation.exceptions.RegexMismatchException;

/**
 * Validates if a given Input is an Integer between max and min.
 */
public class IntRangeValidator implements Validator<Integer> {

    private final int max;
    private final int min;
    String regex = "[+-]?[0-9]*";

    public IntRangeValidator(int max, int min) {
        this.max = max;
        this.min = min;

        if (this.max <= this.min)
            throw new IllegalStateException("Max cannot be smaller than Min");
    }

    public Integer validate(String string) throws InvalidInputException {

        if (string.length() == 0)
            throw new RegexMismatchException(string, this.regex);

        if(string.matches(this.regex)){
            string = string.trim();
            int x = Integer.parseInt(string);
            if (this.min <= x && x <= this.max){
                return x;
            }
            else
                throw new NotInRangeException(this.max, this.min);
        }
        else
            throw new RegexMismatchException(string, this.regex);
    }
}
