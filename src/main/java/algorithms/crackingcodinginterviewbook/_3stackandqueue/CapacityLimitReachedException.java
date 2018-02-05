package algorithms.crackingcodinginterviewbook._3stackandqueue;

// This has to be Exception (not RuntimeException), but for simplicity I have used RuntimeException
public class CapacityLimitReachedException extends RuntimeException {
    public CapacityLimitReachedException(String message) {
        super(message);
    }
}
