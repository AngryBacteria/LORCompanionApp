package model.validation;
import model.validation.exceptions.NotInRangeException;
import model.validation.exceptions.RegexMismatchException;

/**
 * Validates if a given Input is a Double between max and min.
 */
public class DoubleRangeValidator implements Validator<Double> {

    private final double max;
    private final double min;
    String regex = "^[+-]?([0-9]*[.])?[0-9]+$";

    public DoubleRangeValidator(double max, double min) {
        this.max = max;
        this.min = min;

        if (this.max <= this.min)
            throw new IllegalStateException("Max cannot be smaller than Min");
    }

    public Double validate(String inputString) throws NotInRangeException, RegexMismatchException {

        if (inputString.matches(this.regex)){
            inputString = inputString.trim();
            double x = Double.parseDouble(inputString);
            if (this.min <= x && x <= this.max){
                return x;
            }
            else
                throw new NotInRangeException(this.max, this.min);
        }
        else
            throw new RegexMismatchException(inputString, this.regex);
    }
}
