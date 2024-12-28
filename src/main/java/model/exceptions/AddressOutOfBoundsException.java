package model.exceptions;

public class AddressOutOfBoundsException extends RuntimeException {
    String message;
    public AddressOutOfBoundsException(int address){
        this.message = "AddressOutOfBoundsException: Address " + address + " out of bounds.";
    }
    public String getMessage() {
        return message;
    }
}
