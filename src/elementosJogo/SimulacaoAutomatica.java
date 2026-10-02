package elementosJogo;

import Collections.Graphs.Graph;
import Collections.Lists.ArrayUnorderedList;
import Collections.Stacks.ArrayStack;

/**
 * Simulação automática da missão.
 *
 * <p>São consideradas todas as entradas, todos os caminhos simples até ao
 * alvo e todas as saídas possíveis. Entre os percursos completos é escolhido
 * o que deixa o agente com mais pontos de vida. Em caso de empate é escolhido
 * o percurso com menos divisões.</p>
 */
public class SimulacaoAutomatica {

    private final Missao missaoAtual;
    private ArrayUnorderedList<String> melhorCaminho;
    private int melhorVida;

    /**
     * Cria a simulação automática para a missão indicada.
     *
     * @param missao missão a analisar
     */
    public SimulacaoAutomatica(Missao missao) {
        this.missaoAtual = missao;
        this.melhorCaminho = null;
        this.melhorVida = Integer.MIN_VALUE;
    }

    /**
     * Procura e apresenta o percurso completo que conserva mais pontos de
     * vida, incluindo o trajeto de entrada até ao alvo e o regresso até uma
     * saída.
     */
    public void iniciar() {
        if (missaoAtual == null) {
            System.out.println("Nenhuma missão existe.");
            return;
        }

        procurarMelhorPercurso();

        System.out.println("\n******** Simulação Automática ********");
        if (melhorCaminho == null) {
            System.out.println("Não existe um percurso entre uma entrada, o alvo e uma saída.");
            System.out.println("****************************************");
            return;
        }

        System.out.println("Entrada: " + melhorCaminho.first());
        System.out.println("Alvo: " + missaoAtual.getAlvo().getRoom());
        System.out.println("Saída: " + melhorCaminho.last());
        System.out.println("Percurso: " + melhorCaminho);
        System.out.println("Número de divisões: " + melhorCaminho.size());
        System.out.println("Pontos de vida estimados no final: " + melhorVida);

        if (melhorVida > 0) {
            System.out.println("É possível concluir a missão sem comprometer o sucesso.");
        } else {
            System.out.println("Com a informação atual, não é possível concluir a missão com vida.");
        }
        System.out.println("****************************************");
    }

    /**
     * Analisa todas as combinações de entrada, caminho até ao alvo e saída.
     */
    private void procurarMelhorPercurso() {
        ArrayUnorderedList<String> entradasSaidas = missaoAtual.getEntradasSaidas();
        String alvo = missaoAtual.getAlvo().getRoom();

        for (String entrada : entradasSaidas) {
            ArrayUnorderedList<ArrayUnorderedList<String>> caminhosIda =
                    obterCaminhosSimples(entrada, alvo);

            for (ArrayUnorderedList<String> caminhoIda : caminhosIda) {
                for (String saida : entradasSaidas) {
                    ArrayUnorderedList<ArrayUnorderedList<String>> caminhosVolta =
                            obterCaminhosSimples(alvo, saida);

                    for (ArrayUnorderedList<String> caminhoVolta : caminhosVolta) {
                        ArrayUnorderedList<String> percurso = combinarCaminhos(caminhoIda, caminhoVolta);
                        int vidaFinal = avaliarCaminho(percurso);

                        if (melhorCaminho == null
                                || vidaFinal > melhorVida
                                || (vidaFinal == melhorVida && percurso.size() < melhorCaminho.size())) {
                            melhorVida = vidaFinal;
                            melhorCaminho = copiarCaminho(percurso);
                        }
                    }
                }
            }
        }
    }

    /**
     * Obtém todos os caminhos simples entre duas divisões.
     *
     * @param inicio divisão inicial
     * @param fim divisão final
     * @return caminhos simples encontrados
     */
    private ArrayUnorderedList<ArrayUnorderedList<String>> obterCaminhosSimples(String inicio, String fim) {
        ArrayUnorderedList<ArrayUnorderedList<String>> resultados = new ArrayUnorderedList<>();
        Graph<String> grafo = missaoAtual.getEdificio().getDivisao();
        int inicioIndex = grafo.getIndex(inicio);
        int fimIndex = grafo.getIndex(fim);

        if (inicioIndex < 0 || fimIndex < 0) {
            return resultados;
        }

        boolean[] visitados = new boolean[grafo.size()];
        ArrayUnorderedList<String> caminhoAtual = new ArrayUnorderedList<>();
        procurarCaminhos(inicioIndex, fimIndex, visitados, caminhoAtual, resultados);
        return resultados;
    }

    /**
     * Pesquisa em profundidade os caminhos simples entre dois vértices.
     *
     * @param atual índice atual
     * @param destino índice de destino
     * @param visitados vértices já usados no caminho atual
     * @param caminhoAtual caminho em construção
     * @param resultados caminhos encontrados
     */
    private void procurarCaminhos(int atual,
                                  int destino,
                                  boolean[] visitados,
                                  ArrayUnorderedList<String> caminhoAtual,
                                  ArrayUnorderedList<ArrayUnorderedList<String>> resultados) {
        Graph<String> grafo = missaoAtual.getEdificio().getDivisao();
        Object[] vertices = grafo.getVertices();
        boolean[][] adjacencias = grafo.getAdjMatrix();

        visitados[atual] = true;
        caminhoAtual.addToRear((String) vertices[atual]);

        if (atual == destino) {
            resultados.addToRear(copiarCaminho(caminhoAtual));
        } else {
            for (int i = 0; i < grafo.size(); i++) {
                if (adjacencias[atual][i] && !visitados[i]) {
                    procurarCaminhos(i, destino, visitados, caminhoAtual, resultados);
                }
            }
        }

        caminhoAtual.removeLast();
        visitados[atual] = false;
    }

    /**
     * Junta o percurso de ida e o de regresso sem repetir o alvo.
     *
     * @param ida caminho até ao alvo
     * @param volta caminho do alvo até à saída
     * @return percurso completo
     */
    private ArrayUnorderedList<String> combinarCaminhos(ArrayUnorderedList<String> ida,
                                                         ArrayUnorderedList<String> volta) {
        ArrayUnorderedList<String> percurso = copiarCaminho(ida);
        for (int i = 1; i < volta.size(); i++) {
            percurso.addToRear(volta.get(i));
        }
        return percurso;
    }

    /**
     * Estima os pontos de vida restantes num percurso com base na informação
     * conhecida da versão da missão. O poder dos inimigos encontrados é
     * descontado uma vez, os coletes são aplicados imediatamente e os kits
     * recolhidos são usados apenas quando necessários ou no final.
     *
     * @param percurso percurso completo
     * @return pontos de vida restantes; valor não positivo significa falha
     */
    private int avaliarCaminho(ArrayUnorderedList<String> percurso) {
        int vida = 100;
        ArrayUnorderedList<Inimigo> inimigos = missaoAtual.getInimigos();
        ArrayUnorderedList<Item> itens = missaoAtual.getItens();
        boolean[] inimigosNeutralizados = new boolean[inimigos.size()];
        boolean[] itensRecolhidos = new boolean[itens.size()];
        ArrayStack<Integer> kits = new ArrayStack<>();

        for (String divisao : percurso) {
            int danoSala = 0;
            for (int i = 0; i < inimigos.size(); i++) {
                Inimigo inimigo = inimigos.get(i);
                if (!inimigosNeutralizados[i] && inimigo.getRoom().equals(divisao)) {
                    danoSala += inimigo.getPoder();
                    inimigosNeutralizados[i] = true;
                }
            }

            while (vida - danoSala <= 0 && !kits.isEmpty() && vida < 100) {
                vida = Math.min(100, vida + kits.pop());
            }

            vida -= danoSala;
            if (vida <= 0) {
                return vida;
            }

            for (int i = 0; i < itens.size(); i++) {
                Item item = itens.get(i);
                if (itensRecolhidos[i] || !item.getDivisao().equals(divisao)) {
                    continue;
                }

                itensRecolhidos[i] = true;
                if (item instanceof Armor) {
                    vida += ((Armor) item).getPontosExtra();
                } else if (item instanceof KitVida) {
                    kits.push(((KitVida) item).getPontosRecuperados());
                }
            }
        }

        while (vida < 100 && !kits.isEmpty()) {
            vida = Math.min(100, vida + kits.pop());
        }
        return vida;
    }

    /**
     * Cria uma cópia independente de um caminho.
     *
     * @param original caminho original
     * @return cópia do caminho
     */
    private ArrayUnorderedList<String> copiarCaminho(ArrayUnorderedList<String> original) {
        ArrayUnorderedList<String> copia = new ArrayUnorderedList<>();
        for (String divisao : original) {
            copia.addToRear(divisao);
        }
        return copia;
    }
}
