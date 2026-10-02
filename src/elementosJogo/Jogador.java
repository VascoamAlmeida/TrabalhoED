package elementosJogo;

import Collections.Stacks.*;

/**
 * Class que representa jogador com seu inventario, ataque, alvo, vida e vidaMaxima
 */
public class Jogador extends Personagens {
    /**
     * variavel array inventario
     */
    private ArrayStack<Item> inventario;
    /**
     * variavel ataque
     */
    private int ataque;
    /**
     * variavel alvo
     */
    private boolean alvo;
    /**
     * variavel vida
     */
    private int vida;
    /**
     * variavel vidaMaxima
     */
    protected int vidaMaxima;
    /**
     * variavel armor
     */
    protected int armor;

    /**
     * Metodo para obter armor
     *
     * @return armor do jogador
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Metodo para por armadura ao jogador
     *
     * @param armor do jogador
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * Metodo contrstrutor do jogador com nome, ataque, alvo, vida e vida maxima
     *
     * @param name   jogador
     * @param ataque jogador
     * @param alvo   se jogador tem ou nao alvo
     * @param vida   do jogador
     * @param armor  do jogador
     */
    public Jogador(String name, int ataque, boolean alvo, int vida, int armor) {
        super(name);
        this.inventario = new ArrayStack<>();
        this.ataque = ataque;
        this.alvo = alvo;
        this.vida = vida;
        this.vidaMaxima = 100;
        this.armor = armor;
    }

    /**
     * Metodo permite obter vida do jogador
     *
     * @return vida que jogador tem
     */
    public int getVida() {
        return vida;
    }

    /**
     * Metodo permite por a vida no jogador
     *
     * @param vida dada ao jogador
     */
    public void setVida(int vida) {
        this.vida = vida;
        this.armor = Math.max(0, vida - vidaMaxima);
    }

    /**
     * Metodo permite obter vida maxima do jogador
     *
     * @return vida maxima do jogador
     */
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    /**
     * Metodo permite por a vida maxima no jogador
     *
     * @param vidaMaxima do jogador
     */
    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    /**
     * Metodo permite ao jogador ver se tem alvo
     *
     * @return alvo
     */
    public boolean isAlvo() {
        return alvo;
    }

    /**
     * Metodo para colocar o alvo
     *
     * @param alvo do jogador
     */
    public void setAlvo(boolean alvo) {
        this.alvo = alvo;
    }

    /**
     * Adicionar item ao inventario
     *
     * @param item adicionar inventario
     */
    public void addItem(Item item) {
        inventario.push(item);
        System.out.println("Item adicionado.");
    }

    /**
     * Usar item do iventario
     *
     * @return item usado
     */
    public Item useItem() {
        if (inventario.isEmpty()) {
            System.out.println("Inventario vazio");
            return null;
        }
        Item item = inventario.pop();
        return item;
    }

    /**
     * Permite inserir o atque no jogador
     *
     * @param ataque do jogador
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Metodo permite obter ataque do jogador
     *
     * @return ataque do jogador
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Metodo para mostrar inventario ao jogador
     *
     * @return true se tem itens, false se nao tem
     */
    public boolean mostrarInventario() {
        if (inventario.isEmpty()) {
            System.out.println("Inventario vazio");
            return false;
        }
        System.out.println("Inventario tem: " + inventario);
        return true;
    }

    /**
     * Metodo personagem recebe dano sendo que primeiro tira da armadura e depois da vida
     *
     * @param dano recebido
     */
    public void recebeDano(int dano) {
        vida -= dano;
        armor = Math.max(0, vida - vidaMaxima);
    }

    /**
     * Metodo string apresentar o jogador
     *
     * @return string com info do jogador
     */
    @Override
    public String toString() {
        return "Jogador{" +
                "inventario=" + inventario +
                '}';
    }

}
