package elementosJogo;


/**
 * Class do item com divisao e tipo
 */
public abstract class Item implements IItem {
    private String divisao;
    private String tipo;

    /**
     * Metodo contrutor do item
     *
     * @param location do item
     * @param tipo     de item
     */
    public Item(String location, String tipo) {
        this.divisao = location;
        this.tipo = tipo;
    }

    /**
     * Metodo permite obter a divisao do item
     *
     * @return divisao do item
     */
    @Override
    public String getDivisao() {
        return divisao;
    }

    /**
     * Metodo permite colocar item na divisao
     *
     * @param divisao que vai estar o item
     */
    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    /**
     * Obtém o tipo do item.
     *
     * @return tipo do item
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Metodo permite applyEffects do item ao jogador
     *
     * @param jogador que recebe os Effects
     */
    public abstract void applyEffect(Jogador jogador);

    /**
     * Metodo permite jogador ter item
     *
     * @param jogador que obtem item
     */
    @Override
    public void getItem(Jogador jogador) {
    }

    /**
     * Metodo que verifica se é armor
     *
     * @return false pois nao é armor
     */
    public boolean isArmor() {
        return false;
    }

    /**
     * Metodo string apresentar o item
     *
     * @return string com info do item
     */
    @Override
    public String toString() {
        return "Item{" +
                "divisao='" + divisao + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
