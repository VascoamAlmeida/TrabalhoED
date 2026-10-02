package Collections.Exceptions;

/**
 * Exception no elementFound
 */
public class ElementNotFoundException extends RuntimeException {
    /**
     * Exception
     *
     * @param collection a collection
     */
    public ElementNotFoundException(String collection)
    {
        super ("O Elemento inserido não esta nesta" + collection);
    }

}
