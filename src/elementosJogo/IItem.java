package elementosJogo;

/**
 * Interface item
 */
public interface IItem {

    /**
     * Metodo obter divisao
     *
     * @return divisao
     */
    String getDivisao();

    /**
     * Metodo obter item
     *
     * @param jogador com item
     */
    void getItem(Jogador jogador);
}
