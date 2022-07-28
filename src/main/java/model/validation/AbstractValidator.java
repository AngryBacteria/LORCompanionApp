package model.validation;
import model.validation.exceptions.InvalidInputException;
import model.validation.exceptions.RegexMismatchException;
import model.validation.exceptions.SemanticException;

/**
 * Generic abstract Input Validator with the basic Structure of how a Validator works. It checks syntax, semantics and maps
 * the input to the desired Type. Not Every Validator extends from this Class, only the ones benefiting from this
 * Structure.
 * @param <V>
 */
public abstract class AbstractValidator<V> implements Validator<V> {
    private final String pattern;

    protected AbstractValidator(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public V validate(String string) throws InvalidInputException {
        string = string.trim();
        this.checkSyntax(string);
        V res = this.map(string);
        this.checkSemantics(res);
        return res;
    }

    private void checkSyntax(String string) throws RegexMismatchException {
        if (!string.matches(this.pattern)) {
            throw new RegexMismatchException(string, this.pattern);
        }
    }

    protected abstract V map(String string) throws InvalidInputException;

    protected void checkSemantics(V string) throws SemanticException {
        // intentionally left empty
    }
}
