package Collections.Lists;

/**
 * Class implmenta 1 linkedList
 *
 * @param <T> gererico
 */
public class LinkedList<T> {

    private int count;
    private LinearNode<T> head;
    private LinearNode<T> tail;

    /**
     * Construtor da linkedList com head tail vazios
     *
     */
    public LinkedList() {
        head = tail = null;
        count = 0;
    }

    /**
     * Metodo verifica lista vazia
     *
     * @return se lista vazia ou nao
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * Metodo para adicionar 1 node
     *
     * @param element a ser adicionado
     */
    public void add(T element) {
        //Primeiro cria o novo nodo
        LinearNode<T> node = new LinearNode<T>(element);

        //Se head = null entao tail fica com o node
        if (isEmpty()) {
            tail = node;
        } else {
            node.setNext(head);

        }
        head = node;
        count++;
    }

    /**
     * Metodo apagar 1 elemento
     *
     * @param element a ser apagado
     */
    public void delete(T element) {
        if (isEmpty()) {
            throw new RuntimeException("Lista Vazia");
        }

        boolean found = false;
        LinearNode<T> previous = null;
        LinearNode<T> current = head;

        //Percorre ate encontrar o elemento ou chegar ao final da lista
        while (current != null && !found) {
            if (current.getElement().equals(element)) {
                found = true;
            } else {
                previous = current; //Guarda o current no node anterior
                current = current.getNext();//Avança current para proximo node
            }
        }
        //Se o elemento for encontrado, fazer remoçao
        if (found) {
            if (current == head) {
                // Remover o primeiro elemento
                head = current.getNext();
                if (head == null) {
                    // Se a lista ficar vazia após a remoção
                    tail = null;
                }
            } else if (current.equals(tail)) {
                // Remover o último elemento
                tail = previous;
                tail.setNext(null);
            } else {
                // Remover um elemento no meio
                previous.setNext(current.getNext());
            }

            count--;
        } else {
            System.out.println("Elemento não encontrado");
        }
    }

    /**
     * Metodo imprimir elementos recursivos
     *
     */
    public void printRecursively() {
        printRecursively(head);
    }

    /**
     * Metodo auxiliar ao print recursivo
     *
     * @param node atual
     */
    private void printRecursively(LinearNode<T> node) {
        if (node == null) { // Caso base: se o node for null, parar a recursão
            return;
        }
        System.out.print(node.getElement() + " ");
        printRecursively(node.getNext()); // Chamada recursiva com o próximo node
    }

    /**
     * Metodo apresentar string
     *
     * @return string de linkedList
     */
    @Override
    public String toString() {
        String result = "";
        LinearNode<T> current = head;

        while (current != null) {
            result += current.getElement().toString();
            if (current.getNext() != null) {
                result += " -> ";
            }
            current = current.getNext();
        }
        return "LinkedList: " + result;
    }

}
