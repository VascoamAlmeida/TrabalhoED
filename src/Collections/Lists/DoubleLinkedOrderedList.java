package Collections.Lists;

/**
 * Classe lista duplamente ligada ordenada
 *
 * @param <T> the type of the element stored in the node
 */
public class DoubleLinkedOrderedList<T> extends DoubleList<T> implements OrderedListADT<T> {

    /**
     * Cria 1 lista duplamente ligada ordenada vazia
     */
    public DoubleLinkedOrderedList() {
        super();
    }

    /**
     * Adiciona 1 elemento ao array e mantem ordem
     *
     * @param element ser adicionado
     */
    @Override
    public void add(T element) {

        //Passa o nosso elemento para usar interface Comparable e para variavel "tmp"
        Comparable<T> tmp = (Comparable<T>) element;

        DoubleNode<T> newNode = new DoubleNode<>(element);

        // Se a lista estiver vazia, adiciona como primeiro e último nó
        if (isEmpty()) {
            head = tail = newNode;
        } else if (tmp.compareTo(head.getElement()) <= 0) {
            // o proximo node novo vai apontar para a head
            newNode.setNext(head);
            //head vai apontar para novo node
            head.setPrevious(newNode);
            //atualizar head com novo 
            head = newNode;

        } else if (tmp.compareTo(tail.getElement()) >= 0) { //Vai ver se o elemento é menor ou igual ao elemento do tail
            //tail vai apontar para novo node
            newNode.setPrevious(tail);
            //node novo vai apontar para a tail            
            tail.setNext(newNode);
            //atualizar tail com novo 
            tail = newNode;

        } else {
            DoubleNode<T> current = head;
            while (current != null && tmp.compareTo(current.getElement()) > 0) {
                current = current.getNext();
            }
            // Insere o novo nó antes do node `current`
            //Proximo NewNode é o current node
            if (current != null) {
                newNode.setNext(current);
                //newNode tem nó anterior current tinha antes
                newNode.setPrevious(current.getPrevious());
                if (current.getPrevious() != null) {
                    //nó anterior ao current, tem newNode como proximo nó
                    current.getPrevious().setNext(newNode);
                }
                //nó current tem newNode com node anterior
                current.setPrevious(newNode);
            }
        }
        count++;
        modCount++;
    }

    /**
     * Mostra elementos Reverse
     */
    public void backward() {
        DoubleNode<T> current = tail;

        while (current != null) {
            System.out.print(current.getElement() + " ");
            current = current.getPrevious();
        }
        System.out.println();
    }
}
/*
getPrevious()

    O que faz: Retorna o nó que está antes do nó atual.
    Quando usar: Usamos quando queremos saber "quem está antes" do nó atual.

setPrevious(node)

    O que faz: Define o nó que está antes do nó atual.
    Quando usar: Usamos quando estamos inserindo ou removendo um nó e precisamos atualizar qual nó estará "antes" do nó atual.

getNext()

    O que faz: Retorna o nó que está depois do nó atual.
    Quando usar: Usamos quando queremos saber "quem está depois" do nó atual.

setNext(node)

    O que faz: Define o nó que está depois do nó atual.
    Quando usar: Usamos quando estamos inserindo ou removendo um nó e precisamos atualizar qual nó estará "depois" do nó atual.

Como fica a estrutura:

    A.setNext(X) → O A aponta para o X.
    X.setPrevious(A) → O X aponta para o A.
    X.setNext(B) → O X aponta para o B.
    B.setPrevious(X) → O B aponta para o X.

Dica para Evitar Confusão:

    setNext(node): Estou dizendo "quem vem depois de mim".
    setPrevious(node): Estou dizendo "quem vem antes de mim".
    getNext(): Quero saber "quem vem depois de mim".
    getPrevious(): Quero saber "quem vem antes de mim".
 */
