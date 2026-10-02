package Collections.Exceptions;

/**
 * Exception ConcurrentModification
 */
public class ConcurrentModificationException extends RuntimeException {
    /**
     * Exeception
     */
    public ConcurrentModificationException()
    {
        super ("Concurrent Modification Exception!");
    }

}
