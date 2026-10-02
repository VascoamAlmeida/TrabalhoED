package Collections.Trees;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.*;
import java.util.Iterator;

/**
 * Implementacao procura arvore binaria em array
 *
 * @param <T> generico
 */
public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    /**
     * Variavel altura
     */
    protected int height;

    /**
     * Variavel index maximo
     */
    protected int maxIndex;

    /**
     * Creates an empty binary search tree.
     */
    public ArrayBinarySearchTree() {
        super();
        height = 0;
        maxIndex = -1;
    }

    /**
     * Creates a binary search with the specified element as its root
     *
     * @param element the element that will become the root of the new tree
     */
    public ArrayBinarySearchTree(T element) {
        super(element);
        height = 1;
        maxIndex = 0; //Vai guarda o valor da ultima folha 
    }

    /**
     * Adds the specified object to this binary search tree in the appropriate
     * position according to its key value. Note that equal elements are added
     * to the right. Also note that the index of the left child of the current
     * index can be found by doubling the current index and adding 1. Finding
     * the index of the right child can be calculated by doubling the current
     * index and adding 2.
     *
     * @param element the element to be added to the search tree
     */
    @Override
    public void addElement(T element) {
        //Vai verificar se a ultima folha é menor que o tamanho
        if (tree.length < maxIndex * 2 + 3) {
            expandCapacity();
        }
        Comparable<T> tempelement = (Comparable<T>) element;
        if (isEmpty()) {
            tree[0] = element;
            maxIndex = 0;
        } else {
            boolean added = false;
            int currentIndex = 0;
            while (!added) {
                if (tempelement.compareTo((tree[currentIndex])) < 0) {
                    /**
                     * go left
                     */
                    if (tree[currentIndex * 2 + 1] == null) {
                        tree[currentIndex * 2 + 1] = element;
                        added = true;
                        if (currentIndex * 2 + 1 > maxIndex) {
                            maxIndex = currentIndex * 2 + 1;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 1;
                    }
                } else {
                    /**
                     * go right
                     */
                    if (tree[currentIndex * 2 + 2] == null) {
                        tree[currentIndex * 2 + 2] = element;
                        added = true;
                        if (currentIndex * 2 + 2 > maxIndex) {
                            maxIndex = currentIndex * 2 + 2;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 2;
                    }
                }
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;
        count++;
    }

    /**
     * Remove elemento da arvore
     *
     * @param targetElement elemento ser removido
     * @return element removido
     */
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        if (isEmpty()) {
            throw new ElementNotFoundException("binary search tree");
        }

        Comparable<T> tempElement = (Comparable<T>) targetElement;

        int targetIndex = findIndex(tempElement, 0);

        T result = tree[targetIndex];
        replace(targetIndex);
        count--;

        int temp = maxIndex;
        maxIndex = -1;
        for (int i = 0; i <= temp; i++) {
            if (tree[i] != null) {
                maxIndex = i;
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;
    }

    /**
     * Removes the node specified for removal and shifts the tree array
     * accordingly.
     *
     * @param targetIndex the node to be removed
     */
    protected void replace(int targetIndex) {
        int currentIndex, parentIndex, temp, oldIndex, newIndex;
        ArrayUnorderedList<Integer> oldlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> newlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> templist = new ArrayUnorderedList<>();
        Iterator<Integer> oldIt, newIt;

        /**
         * if target node has no children
         */
        if ((targetIndex * 2 + 1 >= tree.length) || (targetIndex * 2 + 2 >= tree.length)) {
            tree[targetIndex] = null;
        } /**
         * if target node has no children
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] == null)) {
            tree[targetIndex] = null;
        } /**
         * if target node only has a left child
         */
        else if ((tree[targetIndex * 2 + 1] != null) && (tree[targetIndex * 2 + 2] == null)) {
            /**
             * fill newlist with indices of nodes that will replace the
             * corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 1;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node only has a right child
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] != null)) {
            /**
             * fill newlist with indices of nodes that will replace the
             * corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 2;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();

                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node has two children
         */
        else {
            currentIndex = targetIndex * 2 + 2;

            while (tree[currentIndex * 2 + 1] != null) {
                currentIndex = currentIndex * 2 + 1;
            }

            tree[targetIndex] = tree[currentIndex];

            /**
             * the index of the root of the subtree to be replaced
             */
            int currentRoot = currentIndex;

            /**
             * if currentIndex has a right child
             */
            if (tree[currentRoot * 2 + 2] != null) {
                /**
                 * fill newlist with indices of nodes that will replace the
                 * corresponding indices in oldlist
                 */
                currentIndex = currentRoot * 2 + 2;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * fill oldlist
                 */
                currentIndex = currentRoot;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * do replacement
                 */
                oldIt = oldlist.iterator();
                newIt = newlist.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();

                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            } else {
                tree[currentRoot] = null;
            }
        }
    }

    /**
     * Remove todas as ocorrencias da arvore
     *
     * @param targetElement elemento ser removido
     */
    @Override
    public void removeAllOccurrences(T targetElement) {

        while (contains(targetElement)) {
            removeElement(targetElement);
        }
    }

    /**
     * Remove menor elemento arvore
     *
     * @return menor elemento arvore
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        T minimo = null;
        if (isEmpty()) {
            throw new EmptyCollectionException("Arvore Vazia");
        }
        minimo = findMax();

        return removeElement(minimo);
    }

    /**
     * Remove maior elemento arvore
     *
     * @return maior elemento arvore
     */
    @Override
    public T removeMax() throws EmptyCollectionException {
        T maximo = null;
        if (isEmpty()) {
            throw new EmptyCollectionException("Arvore Vazia");
        }
        maximo = findMax();

        return removeElement(maximo);
    }

    /**
     * Procura o menor elemento da arvore
     *
     * @return menor elemento da arvore
     */
    @Override
    public T findMin() throws EmptyCollectionException {

        if (isEmpty()) {
            throw new EmptyCollectionException("Arvore Vazia");
        }
        int currentIndex = 0;
        while (tree[currentIndex * 2 + 1] != null) {
            currentIndex = currentIndex * 2 + 1;
        }

        return tree[currentIndex];
    }

    /**
     * Procura o menor elemento da arvore
     *
     * @return menor elemento da arvore
     */
    @Override
    public T findMax() throws EmptyCollectionException  {
        if (isEmpty()) {
            throw new EmptyCollectionException("Arvore Vazia");
        }

        int currentIndex = 0;
        while (tree[currentIndex * 2 + 2] != null) {
            currentIndex = currentIndex * 2 + 2;
        }

        return tree[currentIndex];
    }

    /**
     * Permite expandir a capacidade do array
     */
    private void expandCapacity() {
        T[] larger = (T[]) (new Object[tree.length * 2]);

        for (int i = 0; i < tree.length; i++) {
            larger[i] = tree[i];
        }
        tree = larger;
    }

    /**
     * Encontra o indice do elemento
     *
     * @param tempElement elemento que estamos aprocura
     * @param i indice inicial procura
     * @return indice elemento encontrado
     */
    private int findIndex(Comparable<T> tempElement, int i) {

        //Vai incrementar a posição do array até ENCONTRAR a posição correta onde o novo elemento vai entrar
        //Aqui vai fazer: (digamos nosso count = 5) e (tmp=2) (array= [1,5])
        //0<5(check) && tmp 2 - 1 > 0 (check) ->PORTANTO FAZ  "pos++"
        //1<5(check) && tmp 2 - 2 >0 (false) ->PORTANTO SAI DO WHILE
        while (i < count && tempElement.compareTo(tree[i]) != 0) {
            if (tempElement.compareTo(tree[i]) < 0) {
                i = i * 2 + 1;
            } else {
                i = i * 2 + 2;
            }
        }

        return i;
    }
}
