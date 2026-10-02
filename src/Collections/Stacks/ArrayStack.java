package Collections.Stacks;


import Collections.Exceptions.EmptyCollectionException;

/**
 *
 * Implementação da stack utilizando 1 array de elementos genericos Stack Last
 * in, First Out
 *
 * @param <T> Tipo de elemento a ser armazenado na Stack.
 */
public class ArrayStack<T> implements StackADT<T> {

    /**
     * Capacidade da Stack
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * int that represents both the number of elements and the next available
     * position in the array
     */
    protected int top;

    /**
     * array of generic elements to represent the stack
     */
    protected T[] stack;

    /**
     * Creates an empty stack using the default capacity.
     */
    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Creates an empty stack using the specified capacity.
     *
     * @param initialCapacity represents the specified capacity
     */
    public ArrayStack(int initialCapacity) {
        top = 0;
        stack = (T[]) (new Object[initialCapacity]);
    }

    /**
     * Adds the specified element to the top of this stack, expanding the
     * capacity of the stack array if necessary.
     *
     * @param element generic element to be pushed onto stack
     */
    @Override
    public void push(T element) {

        if (size() == stack.length) {
            expandCapacity();
        }

        stack[top] = element;
        top++;
    }

    /**
     * Removes the element at the top of this stack and returns a reference to
     * it. Throws an EmptyCollectionException if the stack is empty.
     *
     * @return T element removed from top of stack
     * @throws EmptyCollectionException if a pop is attempted on empty stack
     */
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        top--;
        T result = stack[top];
        stack[top] = null;

        return result;
    }

    /**
     * Returns a reference to the element at the top of this stack. The element
     * is not removed from the stack. Throws an EmptyCollectionException if the
     * stack is empty.
     *
     * @return T element on top of stack
     * @throws EmptyCollectionException if a peek is attempted on empty stack
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        return stack[top - 1];
    }

    /**
     *
     * Verifica se a nossa Stack esta vazia. Se tiver vazia devolve true se nao
     * estiver devolve false
     *
     * @return false se tiver vazia true se tiver cheia
     */
    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    /**
     *
     * Vai apresentar o tamanho da nossa Stack
     *
     * @return top e com isso indica o tamanho da nossa Stack
     */
    @Override
    public int size() {
        return top;
    }

    /**
     *
     * Vai permitir dobrar a capacidade da nossa Stack se esta tiver cheia T[]
     * larger .... -> estamos a criar uma nova Stack com o dobro Depois de
     * seguida copiamos a stack que temos para a nova Stack Seguida passamos
     * essa Stack para a variavel Stack
     */
    private void expandCapacity() {
        T[] larger = (T[]) (new Object[stack.length * 2]);

        for (int i = 0; i < stack.length; i++) {
            larger[i] = stack[i];
        }

        stack = larger;

    }

    /**
     *
     * Vai apresentar por String como esta a nossa Stack P.S: Metodo só
     * utilizado para TESTES!!!!!
     *
     * @return result que apresenta o resultado da String
     */
    @Override
    public String toString() {
        String result = "";

        for (int i = top - 1; i >= 0; i--) {
            result = result + stack[i].toString() + "\n";

        }

        return result;
    }
}
