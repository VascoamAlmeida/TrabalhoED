package elementosJogo;

/**
 * Class para o movimento
 */
public class Movimento {
    String sala;
    int distancia;

    /**
     * Metodo construtor do movimento com sala e distancia
     *
     * @param sala      inicia movimento
     * @param distancia maxima desde sala inicial
     */
    public Movimento(String sala, int distancia) {
        this.distancia = distancia;
        this.sala = sala;
    }

    /**
     * Metodo para obter sala
     *
     * @return sala inicial
     */
    public String getSala() {
        return sala;
    }

    /**
     * Metodo para obter distancia
     *
     * @return distancia
     */
    public int getDistancia() {
        return distancia;
    }
}
