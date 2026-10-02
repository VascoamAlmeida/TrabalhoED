package elementosJogo;

import Collections.Lists.*;
import Collections.Graphs.*;

import java.util.Iterator;

/**
 * Class edificio com graph edificio e 1 array entradas e saidas
 */
public class Edificio {
    private Graph<String> edificio;
    private ArrayUnorderedList<String> entradasSaidas;
    private int numDivisoes;

    /**
     * Metodo contrutor edificio
     */
    public Edificio() {
        edificio = new Graph<>();
        entradasSaidas = new ArrayUnorderedList<String>();
    }

    /**
     * Metodo adicionar as divisões
     *
     * @param divisao indicada
     */
    public void addDivisao(String divisao) {
        edificio.addVertex(divisao);
    }

    /**
     * Metodo obter divisoes
     *
     * @return divioes
     */
    public Graph<String> getDivisao() {
        return edificio;
    }

    /**
     * Metodo adiciona ligação
     *
     * @param inicio ligação
     * @param fim    ligação
     */
    public void addligacao(String inicio, String fim) {
        edificio.addEdge(inicio, fim);
    }


    /**
     * Metodo para adicionar as entradas e saidas
     *
     * @param entradasSaida existentes
     */
    public void setEntradasSaidas(String entradasSaida) {
        this.entradasSaidas.addToFront(entradasSaida);
    }

    /**
     * Metodo em array para obter as entradas e saidas
     *
     * @return as entradas e saidas
     */
    public ArrayUnorderedList<String> getEntradasSaidas() {
        return entradasSaidas;
    }


    /**
     * Metodo para obter os vizinhos da divisâo pedida
     *
     * @param divisao recebida
     * @return os vizinhos dessa divisão
     */
    public ArrayUnorderedList<String> getVizinhos(String divisao) {
        ObterVizinhos<String> obterVizinhos = new ObterVizinhos<>();
        //Pede os vizinhos da divisao
        ArrayUnorderedList<String> vizinhos = obterVizinhos.getVizinhos(edificio, divisao);

        return vizinhos;
    }

    /**
     * Metodo para ter o caminho mais curto
     *
     * @param posAtual indica a posicao atual
     * @param alvo     indica o alvo pretendido
     * @return caminho mais curto entre posAtual e alvo
     */
    public Iterator<String> shortpath(String posAtual, String alvo) {
        return edificio.iteratorShortestPath(posAtual, alvo);


    }

    /**
     * Metodo string apresentar o edificio
     *
     * @return string com info do edificio
     */
    @Override
    public String toString() {
        return "Edificio{" +
                "edificio=" + edificio +
                "\nentradasSaidas=" + entradasSaidas + '}' + '\n';
    }
}



