package model.validation.exceptions;

/**
 * Exception is thrown when an Object's semantics doesn't match with the desired semantics.
 */
public class SemanticException extends InvalidInputException{

    private final String reason;

    public SemanticException(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Semantic Exception with reason: " + reason;
    }
}
