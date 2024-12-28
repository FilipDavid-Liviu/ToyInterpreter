package model.exceptions;

public class LogicExpressionException extends RuntimeException {
    String message;

    public LogicExpressionException(int code) {
        if (code == 1) {
            this.message = "LogicExpressionException: The first operand is not a Boolean!";
        } else if (code == 2) {
            this.message = "LogicExpressionException: The second operand is not a Boolean!";
        } else if (code == 3) {
            this.message = "LogicExpressionException: The operator is not valid!";
        }
    }

    public String getMessage() {
        return message;
    }
}
