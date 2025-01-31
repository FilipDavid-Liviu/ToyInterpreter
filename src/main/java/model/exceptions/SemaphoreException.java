package model.exceptions;

public class SemaphoreException extends RuntimeException {
    String message;
    public SemaphoreException(int code, String id) {
        if (code == 1)
            this.message = "SemaphoreException: Variable " + id + " must have integer type!";
        else if (code == 2)
            this.message = "SemaphoreException: Variable " + id + " is not defined!";
        else if (code == 3)
            this.message = "SemaphoreException: Semaphore " + id + " size must be integer!";
        else if (code == 4)
            this.message = "SemaphoreException: Address " + id + " is not defined!";
    }
    public SemaphoreException(int code, String id, int thread) {
        if (code == 1)
            this.message = "SemaphoreException: Thread " + thread + " is already in semaphore list " + id + "!";
        else if (code == 2)
            this.message = "SemaphoreException: Thread " + thread + " is not in semaphore list " + id + "!";
    }
    public String getMessage() {
        return this.message;
    }
}
