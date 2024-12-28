package model.exceptions;

public class KeyNotFoundMyDictionaryException extends RuntimeException {
    String message;
    public KeyNotFoundMyDictionaryException(String key) {
        this.message = "KeyNotFoundException: Key \"" + key + "\" not found in dictionary!";
    }
    public String getMessage() {
        return message;
    }
}
