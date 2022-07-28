package model.validation.exceptions;

/**
 * Exception is thrown when an Object is not found.
 */
public class NotFoundException extends InvalidInputException{

    private final String className;
    private final String objectName;

    public NotFoundException(String className, String objectName) {
        this.className = className;
        this.objectName = objectName;
    }


    @Override
    public String toString() {
        return this.className + "[" + this.objectName + "] Not found";
    }
}
