package model.exceptions;

import java.util.Objects;

public class AssignException extends RuntimeException {
    String message;
    String id;
    public AssignException(int code, String id) {
        this.id = id;
        if (Objects.equals(code, 1)){
            this.message = "AssignException: Declared type of variable \"" + this.id + "\" and type of the assigned expression do not match!";
        }
        else if (Objects.equals(code, 2)){
            this.message = "AssignException: The used variable \"" + this.id + "\" was not declared before!";
        }
        else if (Objects.equals(code, 3)){
            this.message = "AssignException: The type of the variable \"" + this.id + "\" must be Integer!";
        }
        else if (Objects.equals(code, 4)){
            this.message = "AssignException: The type of the variable \"" + this.id + "\" must be Reference!";
        }
        else if (Objects.equals(code, 5)){
            this.message = "AssignException: The two variables \"" + this.id + "\" are of different types!";
        }
    }
    public String getMessage() {
        return message;
    }
}
