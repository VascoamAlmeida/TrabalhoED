package elementosJogo;


/**
 * Class personagem com nome e poder
 */
public abstract class Personagens implements IPersonagens {
    private String name;
    private int poder;

    /**
     * Metodo construtor com nome e poder
     *
     * @param name  personagem
     * @param poder personagem
     */
    public Personagens(String name, int poder) {
        this.name = name;
        this.poder = poder;
    }

    /**
     * Metodo construtor com nome
     *
     * @param name personagem
     */
    public Personagens(String name) {
        this.name = name;
    }

    /**
     * Metodo obter nome personagem
     *
     * @return nome personagem
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Metodo obter poder personagem
     *
     * @return poder personagem
     */
    @Override
    public int getPoder() {
        return poder;
    }

    /**
     * Metodo para obter poder personagem
     *
     * @param poder personagem
     */
    public void setPoder(int poder) {
        // a Math.max (0, poder) para ter a ssrteza que o valor devolvodo é 0 ou maior que 0
        this.poder = Math.max(0, poder); //poder nao poder ser negativo
    }


    /**
     * Metodo personagem recebe dano
     *
     * @param dano recebido
     */
    public void recebeDano(int dano) {
        this.poder -= dano;
        if (this.poder < 0) {
            this.poder = 0; // Prevenir que o poder fique negativo
        }
    }

    /**
     * Metodo string apresentar a personagem
     *
     * @return string com info da personagem
     */
    @Override
    public String toString() {
        return "Personagens{" +
                "name='" + name + '\'' +
                ", poder=" + poder +
                '}';
    }
}
