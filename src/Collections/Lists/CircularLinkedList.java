package Collections.Lists;

/**
 * Class de CircularLinkedList
 *
 * @param <T> generico
 */
public class CircularLinkedList<T> extends DoubleList<T> {

    /**
     * Metodo construtor do circularLinkedList
     */
    public CircularLinkedList() {
        super();
    }

    /**
     * Metodo de adicionar element ao CircularLinkedList
     *
     * @param element adicionar
     */
    public void add(T element) {
        DoubleNode<T> node = new DoubleNode<>(element);

        if (isEmpty()) {
            tail = head = node;
            node.setNext(tail);
        } else {
            tail.setNext(node);
            tail = node;
            tail.setNext(head);
        }

    }

}
