package model.validation.exceptions;

/**
 * Exception is thrown when a number is not in the specified range.
 */
public class NotInRangeException extends InvalidInputException{

    private final Object min;
    private final Object max;

    public NotInRangeException(Object max, Object min) {
        this.max = max;
        this.min = min;
    }

    @Override
    public String toString() {
        return "Input is not in Range (Max:"+ this.max +" Min: "+ this.min +")";
    }
}

