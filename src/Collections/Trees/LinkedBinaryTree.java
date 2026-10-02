package Collections.Trees;

import Collections.Exceptions.ElementNotFoundException;
import Collections.Queues.*;
import Collections.Lists.*;
import java.util.Iterator;


/**
 * LinkedBinaryTree implements the BinaryTreeADT interface
 *
 * @param <T> generico
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    /**
     * variavel count
     */
    protected int count;
    /**
     * variavel root
     */
    protected BinaryTreeNode<T> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the new binary
     * tree
     */
    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);
    }

    @Override
    public T getRoot() {
        if (root == null) {
            return null;
        }

        return root.element;
    }

    /**
     * Metodo para verificar se arvore esta vazia
     *
     * @return true se vazia
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Metodo apresentar tamanho arvore
     *
     * @return tamanho arvore
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Metodo para apresentar se elemento esta na arvore
     *
     * @param targetElement the element being sought in the tree
     * @return se elemento existe ou nao
     */
    @Override
    public boolean contains(T targetElement) {
        return find(targetElement) != null;
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {

        BinaryTreeNode<T> current = findAgain(targetElement, root);
        if (current == null) {
            throw new ElementNotFoundException("binary tree");
        }

        return (current.element);
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {

        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);
        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }

        return temp;
    }

    /**
     * Retorna uma representação em string da árvore binária ligada. A
     * representação é composta pelos elementos da árvore em ordem "in-order"
     *
     * Este método chama o método auxiliar `stringOrder,que realiza a travessia
     * "in-order" na árvore e concatena os elementos em uma string.
     *
     * @return uma string representando a árvore binária em ordem "in-order"
     */
    @Override
    public String toString() {
        return printTree(root, "", true);
    }

    /**
     * Método auxiliar que percorre a árvore e gera uma string representando a
     * estrutura hierárquica.
     *
     * @param node o nó atual da árvore.
     * @param prefix o prefixo para formatar a impressão de cada nó.
     * @return a string representando a árvore.
     */
    private String printTree(BinaryTreeNode<T> node, String prefix, boolean isLeft) {
        if (node == null) {
            return prefix + (isLeft ? "L-- " : "R-- ") + "null\n";
        }

        StringBuilder result = new StringBuilder();
        result.append(prefix).append(isLeft ? "L-- " : "R-- ").append(node.element).append("\n");

        // Recursive calls for left and right children with increased indentation
        String newPrefix = prefix + (isLeft ? "|   " : "    ");
        result.append(printTree(node.left, newPrefix, true));
        result.append(printTree(node.right, newPrefix, false));

        return result.toString();
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {

        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inorder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addToRear(node.element);
            inorder(node.right, tempList);
        }
    }

    /**
     * Metodo retorna iterador percorre elementos em pré-ordem
     *
     * @return iterador com elementos em pré-ordem
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder(root, tempList);
        return tempList.iterator();
    }

    /**
     * Metodo faz percuros em pré-ordem
     * @param node indice no node da arvore
     * @param tempList lista onde elementos sao percorridos
     */
    protected void preorder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preorder(node.left, tempList);
            preorder(node.right, tempList);
        }
    }

    /**
     * Metodo retorna iterador percorre elementos em pós-ordem
     *
     * @return iterador com elementos em pós-ordem
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Metodo faz percuros em pós-ordem
     *
     * @param node indice no node da arvore
     * @param tempList lista onde elementos sao percorridos
     */
    protected void postorder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postorder(node.left, tempList);
            postorder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    /**
     * Metodo retorna iterador percorre elementos por niveis
     *
     * @return iterador com elementos por niveis
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>(); //lista results
        LinkedQueue<BinaryTreeNode<T>> nodes = new LinkedQueue<>();

        BinaryTreeNode<T> current = null;

        if (root != null) {
            nodes.enqueue(root); // adiciona a raiz na fila
        }

        while (!nodes.isEmpty()) {

            current = nodes.dequeue();

            if (nodes != null) {
                tempList.addToRear(current.element);

                if (current.left != null) {
                    nodes.enqueue(current.left);
                }
                if (current.right != null) {
                    nodes.enqueue(current.right);
                }
            } else {
                tempList.addToRear(null); //adiciona null aos resultados
            }
        }

        return tempList.iterator(); //Devolve iterador resultados
    }

}
