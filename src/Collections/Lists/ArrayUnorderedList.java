package Collections.Lists;

/**
 * Classe array ñ ordenada
 *
 * @param <T> the type of the element stored in the node
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    /**
     * array vazio
     */
    public ArrayUnorderedList() {
        super();
    }

    /**
     * Aficiona elemento ao inicio do array
     *
     * @param element ser adicionado
     */
    @Override
    public void addToFront(T element) {
        if (size() == array.length) {
            expandCapacity();
        }

        int pos = 0;
        for (int pos2 = count; pos2 > pos; pos2--) {
            //Se necessario vai passar o valor da frente para tras  
            array[pos2] = array[pos2 - 1];
        }
        array[0] = element;

        count++;
        modCount++;
    }

    /**
     * Adiciona elemento ao fim do array
     *
     * @param element ser adicionado
     */
    @Override
    public void addToRear(T element) {
        if (size() == array.length) {
            expandCapacity();
        }

        array[count] = element;

        count++;
        modCount++;
    }

    /**
     * Adiciona elmento a seguir a 1 elemento especifico
     *
     * @param element a ser adicionado
     * @throws IllegalArgumentException Se o elemento alvo não for encontrado.
     */
    @Override
    public void addAfter(T element, T target) {
        if (size() == array.length) {
            expandCapacity();
        }

        int index = find(target);

        if (index == -1) {
            throw new IllegalArgumentException("Elemento não encontrado.");
        }

        for (int i = count; i > index + 1; i--) {
            array[i] = array[i - 1];
        }
        array[index + 1] = element;

        count++;
        modCount++;
    }
}
