package Collections.Queues;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Stacks.*;

/**
 * Classe que permite fazer uma queue com 2 stack
 *
 * @param <T> generico
 */
public class QueueStack<T> implements QueueADT<T> {

    //Stack principal
    private LinkedStack<T> stack1;
    //Stack secundaria
    private LinkedStack<T> stack2;

    /**
     * Metodo Construtuor das stacks
     *
     */
    public QueueStack() {
        stack1 = new LinkedStack<>();
        stack2 = new LinkedStack<>();
    }

    /**
     * Metodo de adicionar elementos
     *
     * @param element the element to be added to the rear of this queue
     */
    @Override
    public void enqueue(T element) {
        //Adiciona elemento a stack1
        stack1.push(element);
    }

    /**
     * Metodo retirar elemento
     *
     * @return elemento top de stack2
     * @throws EmptyCollectionException se for vazia coleçao
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        //Remove elemento da stack1
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue Vazia");
        }

        transferStack1ToStack2IfNeeded();

        //Return elemento top de stack2 
        return stack2.pop();
    }

    /**
     * Permite ver o primeiro valor stack2
     *
     * @return elemento do top da stack2
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue Vazia");
        }
        transferStack1ToStack2IfNeeded();

        return stack2.peek();
    }

    /**
     * Metodo que permite realizar a transferencia de elementos da Stack1 para a
     * Stack2
     *
     */
    private void transferStack1ToStack2IfNeeded() {
        // Move os elementos de stack1 para stack2 se stack2 estiver vazia
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                //Remove do top stack1 e pop para stack2
                stack2.push(stack1.pop());
            }
        }
    }

    /**
     * Verifica se a queue esta vazia
     *
     * @return true se queue = vazia, false = full
     */
    @Override
    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    /**
     * Retorna numero de elementos na queue
     *
     * @return o numero de elementos na queue
     */
    @Override
    public int size() {
        return stack1.size() + stack2.size();
    }

}
