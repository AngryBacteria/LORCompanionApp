package model.validation.exceptions;

/**
 * Exception is thrown when a String doesn't match the given Regex.
 */
public class RegexMismatchException extends InvalidInputException {
    private final String string;
    private final String regex;

    public RegexMismatchException(String string, String regex) {
        this.string = string;
        this.regex = regex;
    }

    @Override
    public String toString() {
        return this.string + " does not match " + this.regex;
    }
}
