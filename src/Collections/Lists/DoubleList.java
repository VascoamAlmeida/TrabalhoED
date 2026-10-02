package Collections.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Abstract class representing a doubly linked list.
 *
 * @param <T> the type of elements in this list
 */
public abstract class DoubleList<T> implements ListADT<T> {

    /**
     * Variaveis count
     */
    protected int count = 0; // Number of elements in the list
    /**
     * variavel modcount
     */
    protected int modCount = 0; // Modification count for concurrent modification checks
    /**
     * variavel head
     */
    protected DoubleNode<T> head; // Reference to the first node in the list
    /**
     * variavel tail
     */
    protected DoubleNode<T> tail; // Reference to the last node in the list

    /**
     * Constrói uma lista vazia.
     */
    public DoubleList() {
        count = 0;
        head = tail = null;
    }

    /**
     * Remove o primeiro elemento da lsita
     *
     * @return primeiro elemento da lista
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {

        if (isEmpty()) {
            throw new EmptyCollectionException("Lista");
        }

        T result = head.getElement();
        //Atuzaliza head para proximo node
        head = head.getNext();

        //Se head nao for null set.head = null
        if (head != null) {
            head.setPrevious(null);
        } else {
            tail = null;
        }

        count--;
        modCount++;

        return result;
    }

    /**
     * Remove e Return o ultimo elemento da lista
     *
     * @return ultimo elemento da lista
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista");
        }

        T result = tail.getElement();

        //Atualiza tail para proximo node
        tail = tail.getPrevious();

        //tail !=null, atualiza anterior
        if (tail != null) {
            tail.setNext(null);
        } else {
            //Se tail == null, head fica null
            head = null;
        }

        count--;
        modCount++;

        return result;
    }

    /**
     * Remove elemento especifico da lista
     *
     * @param element a remover
     * @return o elemento removido
     *
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {

        if (isEmpty()) {
            throw new EmptyCollectionException("Lista");
        }

        DoubleNode<T> nodeRemove = find(element);

        if (nodeRemove == null) {
            throw new ElementNotFoundException("Lista");
        }

        if (nodeRemove == head) {
            return removeFirst();
        }

        if (nodeRemove == tail) {
            return removeLast();
        }

        ///METODO REMOVER NO MEIO
        nodeRemove.getPrevious().setNext(nodeRemove.getNext());
        nodeRemove.getNext().setPrevious(nodeRemove.getPrevious());

        count--;
        modCount++;

        return nodeRemove.getElement();

    }

    /**
     * Retorna primeiro elemento da lista
     *
     * @return primeiro elemento da lista
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista");
        }

        return head.getElement();
    }

    /**
     * Retorna ultimo elemento da lista
     *
     * @return ultimo elemento da lista
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista");
        }

        return tail.getElement();
    }

    /**
     * Verifica se a lista tem o elemento especifico
     *
     * @param target elemento a verificar
     * @return true -> lista tem elemento, false -> se nao tem elemento
     */
    @Override
    public boolean contains(T target) {
        return find(target) != null;
    }

    /**
     * Verifica se lista esta vazia
     *
     * @return true -> lista vazia, false -> ñ vazia
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * retorna tmanho lista
     *
     * @return tamanho da lista
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Meu iterador
     *
     * @return o meu iterador
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<T>();
    }

    /**
     * Encontra node tem elemento especifico
     *
     * @param element a encontrar
     * @return node contem elemento, null se nao houver
     */
    protected DoubleNode<T> find(T element) {

        DoubleNode<T> current = head;

        while (current != null) {
            if (current.getElement().equals(element)) {
                return current;
            }
            current = current.getNext();
        }

        return null;
    }

    /**
     * Class do meu iterador
     *
     * @param <E> the type of elements in this list
     */
    private class MyIterator<E> implements Iterator<E> {

        private DoubleNode<E> current;
        private int expectedCount;
        boolean okToRemove;

        public MyIterator() {
            this.current = (DoubleNode<E>) head;
            this.expectedCount = modCount;
            this.okToRemove = false;
        }

        /**
         * Verifica se tem proximo elemento
         *
         * @return true -> tem proximo lemento, false -> ñ tem proximo elemento
         * @throws ConcurrentModificationException se a lista foi modificada
         * durante a iteração
         */
        @Override
        public boolean hasNext() {
            if (expectedCount != modCount) {
                throw new ConcurrentModificationException("Lista foi modificada durante a iteração.");
            }

            return current != null;
        }

        /**
         * Return proximo elemento
         *
         * @return proximo elemento lista
         * @throws ConcurrentModificationException se a lista foi modificada
         * durante a iteração
         * @throws IllegalStateException se não há mais elementos para iterar
         */
        @Override
        public E next() {
            if (expectedCount != modCount) {
                throw new ConcurrentModificationException("Lista foi modificada durante a iteração.");
            }

            if (!hasNext()) {
                throw new IllegalStateException("Não há mais elementos.");
            }

            E result = current.getElement();
            current = current.getNext();
            okToRemove = true;

            return result;
        }

        /**
         * Remove ultimo elemento
         *
         * @throws ConcurrentModificationException se a lista foi modificada
         * durante a iteração
         * @throws IllegalStateException se não há mais elementos para iterar
         */
        @Override
        public void remove() {
            if (expectedCount != modCount) {
                throw new ConcurrentModificationException("Lista foi modificada durante a iteração.");
            }
            if (okToRemove) {
                throw new IllegalStateException("O método next() não foi chamado ou o elemento já foi removido.");
            }

            okToRemove = false;
            DoubleList.this.remove((T) current.getPrevious().getElement());
            expectedCount++;
        }
    }
}
