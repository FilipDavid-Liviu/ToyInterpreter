package model.exceptions;

public class MyFileNotFoundException extends RuntimeException {
    String message;
    public MyFileNotFoundException(String name) {
        this.message = "FileNotFoundException: File to be opened not found: \"" + name +"\"";
    }
    public String getMessage() {
        return this.message;
    }
}
