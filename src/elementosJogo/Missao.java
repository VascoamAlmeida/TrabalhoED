package elementosJogo;

import Collections.Lists.*;
import Collections.Graphs.*;

/**
 * Class de missao com codMissao, versao e edificio
 */
public class Missao {
    private String codMissao;
    private int versao;
    private Edificio edificio;

    //esta lista deve pasar para divisoes
    private ArrayUnorderedList<Inimigo> inimigos; // array de inimigos

    private Alvo alvo; // o objectivo da misso
    private ArrayUnorderedList<Item> itens; // lista de items


    /**
     * Metodo construtor de missao
     */
    public Missao() {
        this.inimigos = new ArrayUnorderedList<Inimigo>();
        this.itens = new ArrayUnorderedList<Item>();
        this.edificio = new Edificio();
    }

    /**
     * Metodo para passar a informacao sobre o entradas e sadidas
     *
     * @param entradasSaida da missao
     */
    public void setEntradasSaidas(String entradasSaida) {
        edificio.setEntradasSaidas(entradasSaida);
    }

    /**
     * Metodo para obter as entradas e saidas
     *
     * @return entradas e saidas missao
     */
    public ArrayUnorderedList<String> getEntradasSaidas() {
        return edificio.getEntradasSaidas();
    }

    /**
     * Metodo obter divisoes
     *
     * @return divisoes
     */
    public Graph<String> getDivisao() {
        return edificio.getDivisao();
    }

    /**
     * Metodo adicionar divisoes na missao
     *
     * @param divisoes adicionadas na missao
     */
    public void addDivisao(String divisoes) {
        edificio.addDivisao(divisoes);
    }


    /**
     * Metodo adicionar ligacoes na missao
     *
     * @param start da ligacao
     * @param end   da ligacao
     */
    public void addligacao(String start, String end) {
        edificio.addligacao(start, end);
    }


    /**
     * Metodo para obter o edificio
     *
     * @return o edificio da missao
     */
    public Edificio getEdificio() {
        return edificio;
    }

    /**
     * Metodo passar a informaçao dos healKits
     *
     * @param location          do healKits
     * @param pontosRecuperados do healKits
     * @param tipo              de healKit
     */
    public void setHealtKit(String location, int pontosRecuperados, String tipo) {
        itens.addToFront(new KitVida(location, pontosRecuperados, tipo));
    }

    /**
     * Metodo passar a informaçao da armor
     *
     * @param location    da armor
     * @param pontosExtra da armor
     * @param tipo        da armor
     */
    public void setArmor(String location, int pontosExtra, String tipo) {
        itens.addToFront(new Armor(location, pontosExtra, tipo));
    }

    /**
     * Metodo para obter alvo
     *
     * @return o alvo
     */
    public Alvo getAlvo() {
        return alvo;
    }

    /**
     * Metodo para passar o codMissao
     *
     * @param codMissao para ter codMissao
     */
    public void setCodMissao(String codMissao) {
        this.codMissao = codMissao;
    }

    /**
     * Metodo obter codMissao
     *
     * @return codMissao
     */
    public String getCodMissao() {
        return this.codMissao;
    }

    /**
     * Metodo para obter versao
     *
     * @return versao da missao
     */
    public int getVersao() {
        return versao;
    }

    /**
     * Metodo para passar o codMissao
     *
     * @param versao da missao
     */
    public void setVersao(int versao) {
        this.versao = versao;
    }

    /**
     * Metodo para por os inimigos na missao
     *
     * @param name  do inimigo
     * @param poder do inimigo
     * @param room  do inimigo
     */
    public void setInimigos(String name, int poder, String room) {
        inimigos.addToRear(new Inimigo(name, poder, room));
    }

    /**
     * Metodo para passar alvo de 1 tipo e em 1 divisao
     *
     * @param divisao que esta o alvo
     * @param tipo    do alvo
     */
    public void setAlvo(String divisao, String tipo) {
        alvo = new Alvo(divisao, tipo);
    }

    /**
     * Metodo para obter os itens
     *
     * @return os itens
     */
    public ArrayUnorderedList<Item> getItens() {
        return itens;
    }

    /**
     * Metodo para obter os inimigos
     *
     * @return os inimigos
     */
    public ArrayUnorderedList<Inimigo> getInimigos() {
        return inimigos;
    }

    /**
     * Cria uma cópia independente da missão para que cada simulação comece
     * com inimigos e itens no estado original.
     *
     * @return cópia da missão
     */
    public Missao copiar() {
        Missao copia = new Missao();
        copia.setCodMissao(codMissao);
        copia.setVersao(versao);

        Graph<String> grafo = edificio.getDivisao();
        Object[] vertices = grafo.getVertices();
        boolean[][] ligacoes = grafo.getAdjMatrix();

        for (int i = 0; i < grafo.size(); i++) {
            copia.addDivisao((String) vertices[i]);
        }

        for (int i = 0; i < grafo.size(); i++) {
            for (int j = i + 1; j < grafo.size(); j++) {
                if (ligacoes[i][j]) {
                    copia.addligacao((String) vertices[i], (String) vertices[j]);
                }
            }
        }

        for (String entradaSaida : edificio.getEntradasSaidas()) {
            copia.setEntradasSaidas(entradaSaida);
        }

        for (Inimigo inimigo : inimigos) {
            copia.setInimigos(inimigo.getName(), inimigo.getPoder(), inimigo.getRoom());
        }

        if (alvo != null) {
            copia.setAlvo(alvo.getRoom(), alvo.getTipo());
        }

        for (Item item : itens) {
            if (item instanceof KitVida) {
                KitVida kit = (KitVida) item;
                copia.setHealtKit(kit.getDivisao(), kit.getPontosRecuperados(), kit.getTipo());
            } else if (item instanceof Armor) {
                Armor colete = (Armor) item;
                copia.setArmor(colete.getDivisao(), colete.getPontosExtra(), colete.getTipo());
            }
        }

        return copia;
    }

    /**
     * Metodo string apresentar a missao
     *
     * @return string com info da missao
     */
    @Override
    public String toString() {
        return "Missao{" +
                "codMissao='" + codMissao + '\'' +
                ", versao=" + versao + '\n' +
                "Edificio=" + '\n' + edificio.toString() +
                "inimigos=" + inimigos +
                '\n' + alvo +
                '\n' + "Itens" + itens.toString() +
                '}';
    }
}
