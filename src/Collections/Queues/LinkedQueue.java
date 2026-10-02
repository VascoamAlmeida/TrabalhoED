package Collections.Queues;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.*;

/**
 * Classe Linked Queue
 *
 * @author vasco
 * @param <T> the type of the element stored in the node
 */
public class LinkedQueue<T> implements QueueADT<T> {

    /**
     * variaveis count, head e tail
     */
    private int count;
    private LinearNode<T> head;
    private LinearNode<T> tail;

    /**
     * Construtor da linkedList com head tail vazios
     *
     */
    public LinkedQueue() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Vai adicionar 1 novo node a fila
     *
     * @param element ser adicionado
     */
    @Override
    public void enqueue(T element) {
        //Cria 1 novo node
        LinearNode<T> node = new LinearNode<>(element);

        if (isEmpty()) {
            head = node;
        } else {
            tail.setNext(node);
        }
        tail = node;
        count++;
    }

    /**
     * Vai retirar um node a fila
     *
     * @return elemento removido
     * @throws EmptyCollectionException se a fila estiver vazia
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }

        T result = head.getElement();
        //Atualiza head proximo node
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        count--;

        return result;
    }

    /**
     * Vai apresentar o node que esta no inicio da fila
     *
     * @return elemento na head
     * @throws EmptyCollectionException se a fila estiver vazia
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }
        return head.getElement();
    }

    /**
     * Vai verificar se a queue esta vazia
     *
     * @return true -> queue vazia, false -> se ñ tiver vazia
     */
    @Override
    public boolean isEmpty() {
        return tail == null;
    }

    /**
     *
     * Vai apresentar o tamanho da minha queue
     *
     * @return tamanho queue
     */
    @Override
    public int size() {
        return count;
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

        LinearNode<T> currentNode = head;

        while (currentNode != null) {
            result = result + currentNode.getElement().toString() + " ";
            currentNode = currentNode.getNext();
        }
        System.out.println();
        return result;
    }

}
