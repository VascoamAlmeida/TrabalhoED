package Collections.Queues;


import Collections.Exceptions.EmptyCollectionException;

/**
 * Implementaçao de uma queue circular
 *
 * @param <T> o tipo de elemento armazenado na queue
 */
public class CircularArrayQueue<T> implements QueueADT<T> {

    /**
     * Capacidade da queue
     */
    private final int DEFAULT_CAPACITY = 3;

    /**
     * int head representa a primeira posiçao da queue int tail representa a
     * ultima posiçao da queue int count contador para contar elementos da fila
     *
     */
    private int head;
    private int tail;
    private int count;

    /**
     * array of generic elements to represent the stack
     */
    private T[] queue;

    /**
     * Creates an empty stack using the default capacity.
     */
    public CircularArrayQueue() {
        head = 0;
        tail = 0;
        count = 0;
        queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Creates an empty stack using the specified capacity.
     *
     * @param initialCapacity represents the specified capacity
     */
    public CircularArrayQueue(int initialCapacity) {
        head = 0;
        tail = 0;
        count = 0;
        queue = (T[]) (new Object[initialCapacity]);
    }

    /**
     * Adiciona um elemento ao final da queue
     *
     * @param element o elemento a ser adicionado
     */
    @Override
    public void enqueue(T element) {
        if (size() == queue.length) {
            expandCapacity();
        }

        //Adiciona elemento ao tail
        queue[tail] = element;

        //Atualiza o tail para o proximo espaço disponivel
        tail = (tail + 1) % queue.length;

        count++;

    }

    /**
     * Remove e retorna elemento no inicio da queue
     *
     * @return elemento removido da queue
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue Vazia");
        }
        //Armazena elemento a ser devolvido
        T result = queue[head];
        //Atualiza head para proximo elemento
        head = (head + 1) % queue.length;
        count--;

        return result;
    }

    /**
     * Retorna o elemento do inicio da queue sem remover
     *
     * @return o primeiro elemento da queue
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue Vazia");
        }

        return queue[head];
    }

    /**
     * Verifica se a queue esta vazia
     *
     * @return true se queue = vazia, false = full
     */
    @Override
    public boolean isEmpty() {
        return count == 0;

    }

    /**
     * Retorna numero de elementos na queue
     *
     * @return o numero de elementos na queue
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Expande a capacidad da fila para o dobro do tamanho atual
     */
    private void expandCapacity() {
        T[] larger = (T[]) (new Object[queue.length * 2]);

        //copia elementos do array original para o novo 
        for (int i = 0; i < count; i++) {
            larger[i] = queue[(head + i) % queue.length];
        }

        //Atualiza referencia para novo array
        queue = larger;
        //Reset o head para inicio array
        head = 0;
        //tail fica a apontar para proximo espaço livre
        tail = count;

    }

    /**
     * Faz uma representaçao da queue
     *
     * @return representaçao da queue
     */
    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < count; i++) {
            result = result + queue[(head + i) % queue.length].toString() + "\n";

        }
        return result;
    }
}
