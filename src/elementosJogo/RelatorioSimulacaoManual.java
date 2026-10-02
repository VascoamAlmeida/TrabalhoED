package elementosJogo;

import Collections.Lists.*;

/**
 * Metodo relatorio simulacao manual
 */
public class RelatorioSimulacaoManual {
    private String codMissao;
    private String versao;
    private ArrayUnorderedList<String> caminho;
    private int vidaFinal;
    private int armorFinal;

    /**
     * Contrutor de relatorio para simulacao manual
     *
     * @param codMissao  do relatorio da simulacao
     * @param versao     do relatorio da simulacao
     * @param caminho    do relatorio da simulacao
     * @param vidaFinal  do relatorio da simulacao
     * @param armorFinal do relatorio da simulacao
     */
    public RelatorioSimulacaoManual(String codMissao, int versao, ArrayUnorderedList<String> caminho, int vidaFinal, int armorFinal) {
        this.codMissao = codMissao;
        this.versao = Integer.toString(versao);
        this.caminho = caminho;
        this.vidaFinal = vidaFinal;
        this.armorFinal = armorFinal;
    }

    /**
     * Metodo para obter codMissao
     *
     * @return codMissao
     */
    public String getCodMissao() {
        return codMissao;
    }

    /**
     * Metodo para obter a versao
     *
     * @return versao
     */
    public String getVersao() {
        return versao;
    }

    /**
     * Metodo para obter o caminho feito
     *
     * @return getCaminho
     */
    public ArrayUnorderedList<String> getCaminho() {
        return caminho;
    }

    /**
     * Metodo para obter vida final
     *
     * @return vida final
     */
    public int getVidaFinal() {
        return vidaFinal;
    }

    /**
     * Metodo para obter armor final
     *
     * @return armor final
     */
    public int getArmorFinal() {
        return armorFinal;
    }


}
