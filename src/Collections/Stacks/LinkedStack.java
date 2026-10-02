package Collections.Stacks;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.*;

/**
 * Implmentaçao de uma Stack, por linked list
 *
 * @param <T> tipo generico dos elementos da Stack
 */
public class LinkedStack<T> implements StackADT<T> {

    /**
     * variaveis count e top
     */
    private int count;
    private LinearNode<T> top;

    /**
     * Construtor da linkedList com head tail vazios
     *
     */
    public LinkedStack() {
        top = null;
        count = 0;
    }

    /**
     *
     * Adiciona um elemento node ao top da Stack
     *
     * @param element a fazer push
     */
    @Override
    public void push(T element) {
        //Cria novo node
        LinearNode<T> node = new LinearNode<>(element);

        // new node aponta para o node atual do top
        node.setNext(top);
        // Atualiza o top para ser new node
        top = node;
        count++;
    }

    /**
     *
     * Remove o elemento do topo da Stack
     *
     * @return o result do valore removido do top da Stack
     * @throws EmptyCollectionException se a Stack estiver vazia
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        //Guarda valor do top
        T result = top.getElement();
        //Atualiza top para o proximo node
        top = top.getNext();
        count--;

        return result;
    }

    /**
     * Vai returnar o elemento que esta no topo da stack
     *
     * @return top.getElement vai apresentar o valor que esta no topo da stack
     * @throws EmptyCollectionException se a Stack estiver vazia
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        return top.getElement();
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
        return top == null;
    }

    /**
     *
     * Vai apresentar o tamanho da nossa LinkedStack
     *
     * @return count e isto indica o tamanho da nossa stack
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Metodo pirnt recursivo
     */
    public void printRecursively() {
        printRecursively(top);
    }

    /**
     * Metodo auxiliar print recursivo
     *
     * @param node que recebe
     */
    private void printRecursively(LinearNode<T> node) {
        if (node == null) { // Caso base: se o node for null, parar a recursão
            return;
        }
        System.out.print(node.getElement() + " ");
        printRecursively(node.getNext()); // Chamada recursiva com o próximo node
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

        LinearNode<T> currentNode = top;

        while (currentNode != null) {
            result = result + currentNode.getElement().toString() + "\n";
            currentNode = currentNode.getNext();
        }
        return result;
    }
}
