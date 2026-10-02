package elementosJogo;

/**
 * Class que apresenta a armor que tem pontos extra
 */
public class Armor extends Item {
    private int pontosExtra;

    /**
     * Metodo construtor de armor para com divisao, pontosExtra e tipo
     *
     * @param divisao     que se encontra
     * @param pontosExtra que adiciona
     * @param tipo        seu tipo
     */
    public Armor(String divisao, int pontosExtra, String tipo) {
        super(divisao, tipo);
        this.pontosExtra = pontosExtra;

    }

    /**
     * Aplicar affects ao jogador
     *
     * @param jogador quem vai receber affects
     */
    @Override
    public void applyEffect(Jogador jogador) {
        jogador.setVida(jogador.getVida() + pontosExtra);
        System.out.println("Colete aplicado: +" + pontosExtra + " pontos de vida");
    }

    /**
     * Metodo que verifica se é do tipo armor
     *
     * @return true se é armor
     */
    /**
     * Obtém os pontos extra fornecidos pelo colete.
     *
     * @return pontos extra
     */
    public int getPontosExtra() {
        return pontosExtra;
    }

    @Override
    public boolean isArmor() {
        return true;
    }

    /**
     * Metodo string apresentar o armor
     *
     * @return string com info do armor
     */
    @Override
    public String toString() {
        return "Armor{" +
                "pontosExtra=" + pontosExtra +
                super.toString() +
                '}';
    }
}
