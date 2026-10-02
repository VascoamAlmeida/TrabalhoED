package Collections.Stacks;


import Collections.Exceptions.EmptyCollectionException;

/**
 * Class de ArraySmackStack
 *
 * @param <T> generico
 */
public class ArraySmackStack<T> extends ArrayStack<T> implements SmackStackADT<T> {

    /**
     *  inicializa o construtor com a capacidade inicial escolhida pelo
     *  utilisador
     *
     * @param capacity do array
     */
    public ArraySmackStack(int capacity) {
        super(capacity);
    }

    /**
     * inicializa o construtor com a default capacity da super class
     */
    public ArraySmackStack() {
        super();
    }

    /**
     * Metodo remover o elemento do fim da stack
     *
     * @return elemento no fim da stack
     * @throws EmptyCollectionException stack vazia
     */
    @Override
    public T smack() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        T result = stack[0];
        int i = 1;
        while (i < top) {
            stack[i - 1] = stack[i];
            i++;
        }
        stack[top] = null;
        top--;
        return result;
    }
}
