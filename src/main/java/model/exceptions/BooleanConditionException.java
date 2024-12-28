package model.exceptions;


public class BooleanConditionException extends RuntimeException {
    String message;
    public BooleanConditionException(int code) {
        if (code == 1)
            this.message = "BooleanConditionException: The condition of the if statement is not a boolean expression!";
        else if (code == 2)
            this.message = "BooleanConditionException: The condition of the (do)while statement is not a boolean expression!";
        else if (code == 3)
            this.message = "BooleanConditionException: The condition of the for statement is not a boolean expression!";
    }

    public String getMessage() {
        return message;
    }
}
