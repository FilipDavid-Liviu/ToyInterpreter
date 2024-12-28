package model.exceptions;

public class FileException extends RuntimeException {
    String message;
    public FileException(int code) {
        if(code == 1) {
            this.message = "FileException: File name must be a string!";
        }
        if(code == 2) {
            this.message = "FileException: File already opened!";
        }
        if(code == 3) {
            this.message = "FileException: File does not exist to be closed!";
        }
        if (code == 4) {
            this.message = "FileException: File is not open!";
        }
    }
    public String getMessage() {
        return message;
    }
}
