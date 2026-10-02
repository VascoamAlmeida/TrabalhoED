package Collections.Exceptions;

/**
 * Exception Collection vazia
 *
 */
public class EmptyCollectionException extends RuntimeException {
    /**
     * Exception
     *
     * @param collection recebe
     */
    public EmptyCollectionException(String collection)
    {
        super ("The " + collection + " is empty.");
    }
}
