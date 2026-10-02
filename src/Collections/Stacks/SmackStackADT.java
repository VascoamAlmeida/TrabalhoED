package Collections.Stacks;

/**
 * Interface do SmackStack
 *
 * @param <T> generico
 */
public interface SmackStackADT<T> extends StackADT<T>{

    /**
     * Remove and returns  the last element of this stack.
     *
     * @return T element on bottom of the stack
     * smack() -  Devolve e remover o elemento que esta no fundo da Stack (Pilha)
     */
    public T smack();
}
