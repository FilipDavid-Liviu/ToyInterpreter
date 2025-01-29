package model.exceptions;

public class SemaphoreException extends RuntimeException {
    String message;
    public SemaphoreException(int code, String id) {
        if (code == 1)
            this.message = "Semaphore " + id + " is already defined!";
        else if (code == 2)
            this.message = "Semaphore " + id + " is not defined!";
        else if (code == 3)
            this.message = "Semaphore " + id + " size must be integer!";
    }
    public SemaphoreException(int code, String id, int thread) {
        if (code == 1)
            this.message = "Thread " + thread + " is already in semaphore list " + id + "!";
        else if (code == 2)
            this.message = "Thread " + thread + " is not in semaphore list " + id + "!";
    }
    public String getMessage() {
        return this.message;
    }
}
