package elementosJogo;


/**
 * Class de kitVida com os pontos recuperados
 */
public class KitVida extends Item {

    /**
     * variavel pontos recuperados
     */
    private int pontosRecuperados;

    /**
     * Metodo construtor do kit vida com a sua localização, tipo e pontos recuperados
     *
     * @param location          do kit
     * @param pontosRecuperados do kit
     * @param tipo              kit
     */
    public KitVida(String location, int pontosRecuperados, String tipo) {
        super(location, tipo);
        this.pontosRecuperados = pontosRecuperados;
    }

    /**
     * Metodo permite applyEffects do item ao jogador
     *
     * @param jogador que recebe os Effects
     */
    @Override
    public void applyEffect(Jogador jogador) {
        int vida = jogador.getVida();
        int vidaMaxima = jogador.vidaMaxima;

        if (vida >= vidaMaxima) {
            return;
        }

        int vidaRecuperada = Math.min(pontosRecuperados, vidaMaxima - vida);
        jogador.setVida(vida + vidaRecuperada);

    }

    /**
     * Metodo string apresentar o kit vida
     *
     * @return string com info do kit vida
     */
    /**
     * Obtém os pontos recuperados pelo kit.
     *
     * @return pontos recuperados
     */
    public int getPontosRecuperados() {
        return pontosRecuperados;
    }

    @Override
    public String toString() {
        return "KitVida{" +
                "pontosRecuperados=" + pontosRecuperados +
                super.toString() +
                '}';
    }
}
