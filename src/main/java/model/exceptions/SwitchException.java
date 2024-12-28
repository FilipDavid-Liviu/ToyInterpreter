package model.exceptions;

public class SwitchException extends RuntimeException {
    private String message;
    public SwitchException() {
        this.message = "SwitchException: Type of case does not match type of switch";
    }
    @Override
    public String getMessage() {
        return message;
    }
}
