package model.exceptions;

public class ArithmeticRelationalExpressionException extends RuntimeException {
    String message;

    public ArithmeticRelationalExpressionException(int code) {
        if (code == 1) {
            this.message = "ArithmeticRelationalExpressionException: The first operand is not an Integer!";
        } else if (code == 2) {
            this.message = "ArithmeticRelationalExpressionException: The second operand is not an Integer!";
        } else if (code == 3) {
            this.message = "ArithmeticRelationalExpressionException: The operator is not valid!";
        }
    }

    public String getMessage() {
        return message;
    }
}
