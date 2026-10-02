package Collections.Lists;

/**
 * Classe array ordenada
 *
 * @param <T> the type of the element stored in the node
 */
public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    /**
     * Cria 1 array ordenada vazia
     */
    public ArrayOrderedList() {
        super();
    }

    /**
     * Adiciona 1 elemento ao array e mantem ordem
     *
     * @param element ser adicionado
     * @throws ClassCastException se o elemento não for comparável
     */
    @Override
    public void add(T element) {

        if (size() == array.length) {
            expandCapacity();
        }

        //Passa o nosso elemento para usar interface Comparable e para variavel "tmp"
        Comparable<T> tmp = (Comparable<T>) element;

        int pos = 0;
        //Vai incrementar a posição do array até ENCONTRAR a posição correta onde o novo elemento vai entrar
        //Aqui vai fazer: (digamos nosso count = 5) e (tmp=2) (array= [1,5])
        //0<5(check) && tmp 2 - 1 > 0 (check) ->PORTANTO FAZ  "pos++"
        //1<5(check) && tmp 2 - 2 >0 (false) ->PORTANTO SAI DO WHILE
        while (pos < count && tmp.compareTo(array[pos]) > 0) {
            pos++;
        }

        //Desloca os elementos para frente para abrir espaço para o novo elemento
        for (int pos2 = count; pos2 > pos; pos2--) {
            //Se necessario vai passar o valor da frente para tras  
            array[pos2] = array[pos2 - 1];
        }
        //Vai adicionar o element a posição correta
        array[pos] = element;

        count++;
        modCount++;
    }

}
