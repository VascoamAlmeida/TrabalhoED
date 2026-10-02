package Collections.Lists;


import Collections.Exceptions.ElementNotFoundException;
import Collections.Exceptions.EmptyCollectionException;

/**
 * Classe da lista duplamente ligada ñ ordenada
 *
 * @param <T> the type of the element stored in the node
 */
public class DoubleLinkedUnorderedList<T> extends DoubleList<T> implements UnorderedListADT<T> {

    /**
     * Lista duplamente ligada vazia
     */
    public DoubleLinkedUnorderedList() {
        super();
    }

    /**
     * Aficiona elemento ao inicio da lista
     *
     * @param element ser adicionado
     */
    @Override
    public void addToFront(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            //nó novo vai apontar para a head
            newNode.setNext(head);
            //head vai apontar para novo nó
            head.setPrevious(newNode);
            //atualizar head com novo 
            head = newNode;
        }

        count++;
        modCount++;
    }

    /**
     * Adiciona elemento ao fim da lista
     *
     * @param element ser adicionado
     */
    @Override
    public void addToRear(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        //Se head = null entao tail fica com o node
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            //nó novo vai apontar para a tail            
            tail.setNext(newNode);
            //tail vai apontar para novo nó
            newNode.setPrevious(tail);
            //atualizar tail com novo 
            tail = newNode;
        }

        count++;
        modCount++;
    }

    /**
     * Adiciona elmento a seguir a 1 elemento especifico
     *
     * @param element a ser adicionado
     * @param target elmento apos o novo elemento vai ser adicionado
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (isEmpty()) {
            throw new EmptyCollectionException("List");
        }
        DoubleNode<T> nodeToAdd = find(target);

        if (nodeToAdd == null) {
            throw new ElementNotFoundException("Elemento alvo não encontrado na lista.");
        }

        //novo node aponta para proximo node
        newNode.setNext(nodeToAdd.getNext());
        // novo node aponta node target
        newNode.setPrevious(nodeToAdd);

        if (nodeToAdd.getNext() != null) {
            //Atualiza proximo node para apontar para novo node
            nodeToAdd.getNext().setPrevious(newNode);
        }
        //node target aponta novo node
        nodeToAdd.setNext(newNode);

        count++;
        modCount++;
    }

}
