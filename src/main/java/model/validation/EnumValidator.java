package model.validation;
import model.validation.exceptions.InvalidInputException;
import model.validation.exceptions.NotFoundException;

/**
 * Generic EnumValidator that checks if the Input matches one of the Enums constants.
 * @param <V> Enum
 */
public class EnumValidator<V extends Enum<V>> implements Validator<V> {

	private final V[] values;

	public EnumValidator(V[] values) {
		this.values = values;
	}

	@Override
	public V validate(String inputString) throws InvalidInputException {
		for (V object : this.values){
			if (object.name().equalsIgnoreCase(inputString))
				return object;
		}
		throw new NotFoundException("Enum", inputString);
	}
}
