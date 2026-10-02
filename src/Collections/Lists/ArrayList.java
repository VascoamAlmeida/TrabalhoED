package Collections.Lists;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import java.util.ConcurrentModificationException;

import Collections.Lists.*;
import Collections.Queues.*;
import Collections.Stacks.*;
import Collections.Trees.*;
import Collections.Graphs.*;
import java.util.Iterator;

/**
 * Abstract class representing a array list.
 *
 * @param <T> the type of elements in this list
 */
public abstract class ArrayList<T> implements ListADT<T> {

    /**
     * variavel capacida
     */
    private final int DEFAULT_CAPACITY = 100; //capacidade array
    /**
     * variavel count
     */
    protected int count = 0;
    /**
     * variavel array
     */
    protected T[] array; //array elementos
    /**
     * variavel modCount
     */
    protected int modCount = 0; //contador modificaçoes

    /**
     * Constrói uma lista vazia.
     */
    public ArrayList() {
        count = 0;
        array = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Remove o primeiro elemento da lsita
     *
     * @return primeiro elemento da lista
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {

        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        T result = array[0];

        //Move todos elementos 1 posçao para frente
        for (int i = 0; i < count - 1; i++) {
            array[i] = array[i + 1];
        }

        array[count - 1] = null; // Limpa o último elemento
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
            throw new EmptyCollectionException("ArrayList");
        }

        // Armazena o último elemento
        T result = array[count - 1];
        array[count - 1] = null;  // Limpa o último elemento
        count--;
        modCount++;

        return result;

    }

    /**
     * Remove elemento especifico da lista
     *
     * @param element a remover
     * @return o elemento removido
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {

        int index = find(element);

        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        if (index == -1) {
            throw new ElementNotFoundException("ArrayList");
        }

        //Guarda elemento ser removido
        T result = array[index];

        //Move todos elementos para tras
        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
        }

        array[count - 1] = null;  // Limpa o último elemento
        count--;
        modCount++;

        return result;
    }

    /**
     * Retorna primeiro elemento da lista
     *
     * @return primeiro elemento da lista
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        return array[0];
    }

    /**
     * Retorna ultimo elemento da lista
     *
     * @return ultimo elemento da lista
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        return array[count - 1];
    }

    /**
     * Verifica se a lista tem o elemento especifico
     *
     * @param target elemento a verificar
     * @return true -> lista tem elemento, false -> se nao tem elemento
     */
    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }
        for (int i = 0; i < count; i++) {
            if (array[i].equals(target)) {
                return true;
            }
        }
        return false;
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
     * Expande array se for necessario
     */
    protected void expandCapacity() {
        T[] larger = (T[]) (new Object[array.length * 2]);

        //Copia array
        for (int i = 0; i < array.length; i++) {
            larger[i] = array[i];
        }

        //Guarda array maior no array anterior
        array = larger;
    }

    /**
     * Encontra node tem elemento especifico
     *
     * @param element a encontrar
     * @return node contem elemento, null se nao houver
     */
    protected int find(T element) {
        int index;
        for (int i = 0; i < count; i++) {
            if (array[i].equals(element)) {
                index = i;
                return index;
            }
        }
        return -1;
    }

    /**
     * Apresenta a lista de elementos do array
     *
     * @return String elementos da array
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += array[i];
            if (i < count - 1) {
                result += " ";
            }
        }
        return result;
    }

    /**
     * Class do meu iterador
     *
     * @param <E> the type of elements in this list
     */
    private class MyIterator<E> implements Iterator<E> {

        private int currentCount;
        private int expectedCount;
        boolean okToRemove;

        public MyIterator() {
            this.currentCount = 0;
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

            return currentCount < count;
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

            okToRemove = true;

            return (E) array[currentCount++];
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
                throw new IllegalStateException("Não há mais elementos.");
            }

            okToRemove = false;
            currentCount--;
            ArrayList.this.remove(array[currentCount - 1]);
            expectedCount++;
        }
    }

    /**
     * Metodo para obter o index de T
     *
     * @param index que preciso
     * @return index do array
     */
    public T get(int index) {
        if (index <0 || index >= count) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        return array[index];
    }
}
