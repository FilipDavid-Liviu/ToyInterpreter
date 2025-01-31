package model.exceptions;

public class LockException extends RuntimeException {
    String message;
    public LockException(int code, String id) {
        if (code == 1)
            this.message = "LockException: Variable " + id + " must have integer type!";
        else if (code == 2)
            this.message = "LockException: Variable " + id + " is not defined!";
        else if (code == 3)
            this.message = "LockException: Address " + id + " is not defined!";
    }
    public String getMessage() {
        return this.message;
    }
}