package model.exceptions;

public class ProcedureException extends RuntimeException {
    String message;
    public ProcedureException(int code, String name) {
        if (code == 1)
            this.message = "Procedure " + name + " is not defined";
        else if (code == 2)
            this.message = "Procedure " + name + " has wrong number of arguments";
    }
    public String getMessage() {
        return this.message;
    }
}
