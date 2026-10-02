package elementosJogo;

/**
 * Interface de missao
 */
public interface IMissao {

    /**
     * Obter vesao
     *
     * @return versao
     */
    int getVersao();

    /**
     * Obter edificio
     *
     * @return edificio
     */
    Edificio getEdificio();

    /**
     * Obter codMissao
     *
     * @return codMissao
     */
    String getCodMissao();

    /**
     * Obter alvo
     *
     * @return alvo
     */
    Alvo getAlvo();


}
