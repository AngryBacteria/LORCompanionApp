package model.validation;

import model.validation.exceptions.InvalidInputException;
import model.validation.exceptions.SemanticException;

public class RegexValidator extends AbstractValidator<String>{

    public static final RegexValidator URL_VALIDATOR = new RegexValidator("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?");
    public static final RegexValidator CARDCODE_VALIDATOR = new RegexValidator("^\\d{2,3}[A-z]{2}[a-z,0-9]{2,6}$");
    public static final RegexValidator NOT_EMPTY_VALIDATOR = new RegexValidator("^(?!\\s*$).+");

    protected RegexValidator(String pattern) {
        super(pattern);
    }

    @Override
    protected String map(String string) throws InvalidInputException {
        return string;
    }

    @Override
    protected void checkSemantics(String string) throws SemanticException {
        if (string.length() == 0)
            throw new SemanticException("Input cannot be of length 0");
    }
}
