package exercise.exceptions;

import java.util.List;

public class ValidationException extends Exception {

    private final List<String> errors;
    public ValidationException(List<String> errors) {
        super("Problem creating ReceiptItem - invalid arguments");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return super.getMessage().concat(": ").concat(errors.stream().reduce(String::concat).orElseGet(String::new));
    }
}
