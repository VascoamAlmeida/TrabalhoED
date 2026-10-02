package elementosJogo;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.LinkedList;
import Collections.Queues.LinkedQueue;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.Math.random;

/**
 * Simulação manual da missão e respetivas fases de jogador e inimigos.
 */
public class SimulacaoManual {

    private final Missao missaoAtual;
    private final Jogador jogador;
    private String posicaoAtual;
    private final Scanner scanner;
    private final LinkedList<RelatorioSimulacaoManual> relatoriosSimulacoes = new LinkedList<>();
    private ArrayUnorderedList<String> caminhoPercorrido = new ArrayUnorderedList<>();

    /**
     * Cria uma simulação manual usando uma cópia independente da missão.
     *
     * @param missao missão a simular
     */
    public SimulacaoManual(Missao missao) {
        this(missao, new Scanner(System.in));
    }

    /**
     * Cria uma simulação manual usando o scanner fornecido pelo menu.
     *
     * @param missao missão a simular
     * @param scanner scanner usado para ler as opções do jogador
     */
    public SimulacaoManual(Missao missao, Scanner scanner) {
        this.missaoAtual = missao == null ? null : missao.copiar();
        this.jogador = new Jogador("Tó Cruz", 10, false, 100, 0);
        this.scanner = scanner;
    }

    /**
     * Inicia a simulação manual.
     */
    public void iniciar() {
        if (missaoAtual == null) {
            System.out.println("Nenhuma missão existe.");
            return;
        }

        selecionarEntradaInicial();
        caminhoPercorrido = new ArrayUnorderedList<>();
        caminhoPercorrido.addToRear(posicaoAtual);

        // A escolha da entrada corresponde à primeira entrada numa divisão.
        if (inimigoNaSala(posicaoAtual)) {
            resolverCombateJogadorPrimeiro();
            if (jogador.getVida() <= 0) {
                terminarDerrota("Tó Cruz perdeu todos os pontos de vida.");
                return;
            }
        }
        if (!inimigoNaSala(posicaoAtual) && existeAlvo(posicaoAtual)) {
            apanharAlvo();
        }

        while (true) {
            exibirStatusAtual();
            int acao = faseJogador();
            boolean eliminouTodosAoEntrar = false;

            if (acao == 1) {
                if (inimigoNaSala(posicaoAtual)) {
                    eliminouTodosAoEntrar = resolverCombateJogadorPrimeiro();
                }

                if (jogador.getVida() <= 0) {
                    terminarDerrota("Tó Cruz perdeu todos os pontos de vida.");
                    return;
                }

                if (!inimigoNaSala(posicaoAtual) && existeAlvo(posicaoAtual)) {
                    apanharAlvo();
                }

                if (divisaoSaida()) {
                    if (jogador.isAlvo()) {
                        terminarVitoria();
                    } else {
                        terminarDerrota("Tó Cruz saiu do edifício sem o alvo.");
                    }
                    return;
                }
            } else if (acao == 2) {
                // Usar um kit consome a fase do jogador. Em combate, os
                // inimigos presentes mantêm a sua fase de ataque.
                if (inimigoNaSala(posicaoAtual)) {
                    atacarJogador();
                }
            } else if (acao == 3 && inimigoNaSala(posicaoAtual)) {
                resolverCombateJogadorPrimeiro();
            }

            if (jogador.getVida() <= 0) {
                terminarDerrota("Tó Cruz perdeu todos os pontos de vida.");
                return;
            }

            // Se todos os inimigos encontrados ao entrar forem eliminados,
            // o turno termina sem movimento adicional dos restantes inimigos.
            if (!(acao == 1 && eliminouTodosAoEntrar)) {
                boolean inimigosEntraram = faseInimigo();
                if (inimigosEntraram) {
                    resolverCombateInimigosPrimeiro();
                }
            }

            if (jogador.getVida() <= 0) {
                terminarDerrota("Tó Cruz perdeu todos os pontos de vida.");
                return;
            }

            if (!jogador.isAlvo() && !inimigoNaSala(posicaoAtual) && existeAlvo(posicaoAtual)) {
                apanharAlvo();
            }

            apresentarSugestoes();
            System.out.println("Fim do turno.");
            System.out.println("------------------------------");
        }
    }

    /**
     * Permite selecionar uma das entradas/saídas como ponto inicial.
     */
    private void selecionarEntradaInicial() {
        int escolha = -1;
        ArrayUnorderedList<String> entradas = missaoAtual.getEntradasSaidas();

        do {
            System.out.println("------------------------------");
            System.out.println("Indique onde pretende começar a missão:");
            for (int i = 0; i < entradas.size(); i++) {
                System.out.println(i + " - " + entradas.get(i));
            }

            try {
                escolha = scanner.nextInt();
                if (escolha < 0 || escolha >= entradas.size()) {
                    System.out.println("Valor inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido.");
                scanner.next();
            }
        } while (escolha < 0 || escolha >= entradas.size());

        posicaoAtual = entradas.get(escolha);
        System.out.println("Tó Cruz entrou na divisão: " + posicaoAtual);
    }

    /**
     * Apresenta o estado atual do jogador e recolhe os itens existentes na
     * divisão atual.
     */
    private void exibirStatusAtual() {
        System.out.println("\n**************** Estado Atual ****************");
        System.out.println("Divisão atual: " + posicaoAtual);
        System.out.println("Inimigos na sala:");
        listarInimigosNaSala(posicaoAtual);
        System.out.println("Itens na sala:");
        listarItensNaSala(posicaoAtual);
        System.out.println("Vida: " + jogador.getVida());
        System.out.println("Pontos extra acima de 100: " + jogador.getArmor());
        System.out.println("************************************************\n");
    }

    /**
     * Executa a fase do jogador e devolve a ação escolhida.
     *
     * @return 1 para mover, 2 para usar item e 3 para permanecer na divisão
     */
    private int faseJogador() {
        int escolha = -1;
        do {
            System.out.println("Escolha a ação:");
            System.out.println("1 - Mover para outra divisão");
            System.out.println("2 - Usar kit de vida");
            System.out.println("3 - Ficar na divisão");

            try {
                escolha = scanner.nextInt();
                if (escolha < 1 || escolha > 3) {
                    System.out.println("Valor inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido.");
                scanner.next();
            }
        } while (escolha < 1 || escolha > 3);

        if (escolha == 1) {
            moverParaOutraDivisao();
            caminhoPercorrido.addToRear(posicaoAtual);
        } else if (escolha == 2) {
            usarKitDeVida();
        }

        return escolha;
    }

    /**
     * Apresenta os inimigos existentes numa divisão.
     *
     * @param sala divisão a consultar
     */
    private void listarInimigosNaSala(String sala) {
        boolean existeInimigo = false;
        for (Inimigo inimigo : missaoAtual.getInimigos()) {
            if (inimigo.getRoom().equals(sala)) {
                System.out.println(inimigo);
                existeInimigo = true;
            }
        }
        if (!existeInimigo) {
            System.out.println("Sala sem inimigos.");
        }
    }

    /**
     * Recolhe os itens presentes numa divisão. Os coletes são consumidos de
     * imediato e os kits são guardados na mochila (stack).
     *
     * @param sala divisão a consultar
     */
    private void listarItensNaSala(String sala) {
        Iterator<Item> itemIterator = missaoAtual.getItens().iterator();
        ArrayUnorderedList<Item> itensRemover = new ArrayUnorderedList<>();
        boolean existeItem = false;

        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (!item.getDivisao().equals(sala)) {
                continue;
            }

            existeItem = true;
            System.out.println(item);
            if (item.isArmor()) {
                item.applyEffect(jogador);
            } else {
                jogador.addItem(item);
            }
            itensRemover.addToRear(item);
        }

        for (Item item : itensRemover) {
            missaoAtual.getItens().remove(item);
        }

        if (!existeItem) {
            System.out.println("Sala sem itens.");
        }
    }

    /**
     * Permite escolher uma divisão adjacente à posição atual.
     */
    private void moverParaOutraDivisao() {
        int escolha = -1;
        ArrayUnorderedList<String> vizinhos = missaoAtual.getEdificio().getVizinhos(posicaoAtual);

        do {
            System.out.println("Divisões vizinhas a " + posicaoAtual + ":");
            for (int i = 0; i < vizinhos.size(); i++) {
                System.out.println(i + " - " + vizinhos.get(i));
            }

            try {
                escolha = scanner.nextInt();
                if (escolha < 0 || escolha >= vizinhos.size()) {
                    System.out.println("Valor inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido.");
                scanner.next();
            }
        } while (escolha < 0 || escolha >= vizinhos.size());

        posicaoAtual = vizinhos.get(escolha);
        System.out.println("Tó Cruz moveu-se para: " + posicaoAtual);
    }

    /**
     * Utiliza o kit que se encontra no topo da mochila.
     */
    private void usarKitDeVida() {
        Item kit = jogador.useItem();
        if (kit != null) {
            kit.applyEffect(jogador);
            System.out.println("Kit utilizado. Vida atual: " + jogador.getVida());
        }
    }

    /**
     * Verifica se existem inimigos na divisão indicada.
     *
     * @param sala divisão a verificar
     * @return true se existir pelo menos um inimigo
     */
    private boolean inimigoNaSala(String sala) {
        for (Inimigo inimigo : missaoAtual.getInimigos()) {
            if (inimigo.getRoom().equals(sala)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resolve um confronto em que Tó Cruz possui prioridade de ataque.
     *
     * @return true se todos os inimigos da sala forem eliminados
     */
    private boolean resolverCombateJogadorPrimeiro() {
        System.out.println("Confronto: Tó Cruz tem prioridade.");
        atacarInimigos();
        if (!inimigoNaSala(posicaoAtual)) {
            return true;
        }

        atacarJogador();
        return false;
    }

    /**
     * Resolve um confronto iniciado por inimigos que entraram na divisão do
     * jogador durante a respetiva fase.
     *
     * @return true se todos os inimigos da sala forem eliminados
     */
    private boolean resolverCombateInimigosPrimeiro() {
        System.out.println("Confronto: os inimigos têm prioridade.");
        atacarJogador();
        if (jogador.getVida() <= 0) {
            return false;
        }

        atacarInimigos();
        return !inimigoNaSala(posicaoAtual);
    }

    /**
     * Aplica o ataque do jogador simultaneamente a todos os inimigos da sala.
     */
    private void atacarInimigos() {
        ArrayUnorderedList<Inimigo> inimigosMortos = new ArrayUnorderedList<>();

        for (Inimigo inimigo : missaoAtual.getInimigos()) {
            if (inimigo.getRoom().equals(posicaoAtual)) {
                inimigo.recebeDano(jogador.getAtaque());
                System.out.println("Tó Cruz causou " + jogador.getAtaque()
                        + " de dano a " + inimigo.getName() + ".");
                if (inimigo.getPoder() <= 0) {
                    inimigosMortos.addToRear(inimigo);
                }
            }
        }

        for (Inimigo inimigo : inimigosMortos) {
            missaoAtual.getInimigos().remove(inimigo);
            System.out.println("Inimigo " + inimigo.getName() + " eliminado.");
        }
    }

    /**
     * Aplica ao jogador o poder total dos inimigos presentes na sala.
     */
    private void atacarJogador() {
        int dano = danoInimigos();
        jogador.recebeDano(dano);
        System.out.println("Tó Cruz recebeu " + dano + " de dano. Vida: " + jogador.getVida());
    }

    /**
     * Calcula o poder total dos inimigos presentes na divisão do jogador.
     *
     * @return dano total
     */
    private int danoInimigos() {
        int danoTotal = 0;
        for (Inimigo inimigo : missaoAtual.getInimigos()) {
            if (inimigo.getRoom().equals(posicaoAtual)) {
                danoTotal += inimigo.getPoder();
            }
        }
        return danoTotal;
    }

    /**
     * Executa a fase de movimento dos inimigos que não estão em confronto com
     * o jogador.
     *
     * @return true se pelo menos um inimigo entrar na divisão do jogador
     */
    private boolean faseInimigo() {
        System.out.println("\n**************** Fase dos Inimigos ****************");
        boolean inimigosEntramNaSala = false;

        for (Inimigo inimigo : missaoAtual.getInimigos()) {
            if (!inimigo.getRoom().equals(posicaoAtual)) {
                moverInimigo(inimigo);
                if (inimigo.getRoom().equals(posicaoAtual)) {
                    inimigosEntramNaSala = true;
                }
            }
        }

        System.out.println("****************************************************\n");
        return inimigosEntramNaSala;
    }

    /**
     * Move aleatoriamente um inimigo para uma divisão que se encontre a uma
     * distância máxima de duas divisões.
     *
     * @param inimigo inimigo a movimentar
     */
    private void moverInimigo(Inimigo inimigo) {
        ArrayUnorderedList<String> movimentosPossiveis = movimentosPossiveis(inimigo.getRoom(), 2);
        if (movimentosPossiveis.size() == 0) {
            return;
        }

        int escolha = (int) (random() * movimentosPossiveis.size());
        String novaSala = movimentosPossiveis.get(escolha);
        System.out.println(inimigo.getName() + " moveu-se de "
                + inimigo.getRoom() + " para " + novaSala + ".");
        inimigo.setRoom(novaSala);
    }

    /**
     * Obtém, por BFS, todas as divisões alcançáveis até uma distância máxima.
     *
     * @param sala divisão inicial
     * @param distanciaMaxima distância máxima permitida
     * @return divisões para as quais o inimigo se pode deslocar
     */
    private ArrayUnorderedList<String> movimentosPossiveis(String sala, int distanciaMaxima) {
        ArrayUnorderedList<String> movimentos = new ArrayUnorderedList<>();
        LinkedQueue<Movimento> queue = new LinkedQueue<>();
        ArrayUnorderedList<String> visitados = new ArrayUnorderedList<>();

        queue.enqueue(new Movimento(sala, 0));
        visitados.addToRear(sala);

        while (!queue.isEmpty()) {
            Movimento movimentoAtual = queue.dequeue();
            String salaAtual = movimentoAtual.getSala();
            int distanciaAtual = movimentoAtual.getDistancia();

            if (distanciaAtual > 0 && distanciaAtual <= distanciaMaxima) {
                movimentos.addToRear(salaAtual);
            }
            if (distanciaAtual >= distanciaMaxima) {
                continue;
            }

            ArrayUnorderedList<String> vizinhos = missaoAtual.getEdificio().getVizinhos(salaAtual);
            for (String vizinho : vizinhos) {
                if (!contem(visitados, vizinho)) {
                    visitados.addToRear(vizinho);
                    queue.enqueue(new Movimento(vizinho, distanciaAtual + 1));
                }
            }
        }
        return movimentos;
    }

    /**
     * Verifica se uma lista contém um valor sem recorrer às coleções da
     * plataforma Java.
     *
     * @param lista lista a percorrer
     * @param valor valor procurado
     * @return true quando o valor existe
     */
    private boolean contem(ArrayUnorderedList<String> lista, String valor) {
        for (String existente : lista) {
            if (existente.equals(valor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o alvo se encontra numa determinada divisão.
     *
     * @param sala divisão a verificar
     * @return true se o alvo estiver na divisão
     */
    private boolean existeAlvo(String sala) {
        return missaoAtual.getAlvo() != null && missaoAtual.getAlvo().getRoom().equals(sala);
    }

    /**
     * Marca o alvo como recuperado pelo jogador.
     */
    private void apanharAlvo() {
        if (!jogador.isAlvo() && existeAlvo(posicaoAtual) && !inimigoNaSala(posicaoAtual)) {
            jogador.setAlvo(true);
            System.out.println("Tó Cruz atingiu e recuperou o alvo.");
        }
    }

    /**
     * Verifica se a posição atual é uma das entradas/saídas do edifício.
     *
     * @return true se a divisão puder ser usada como saída
     */
    private boolean divisaoSaida() {
        for (String saida : missaoAtual.getEntradasSaidas()) {
            if (saida.equals(posicaoAtual)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Apresenta, no final de cada turno, o melhor caminho para o alvo e o
     * caminho para o kit de recuperação mais próximo.
     */
    private void apresentarSugestoes() {
        System.out.println("Sugestões de espionagem:");
        if (!jogador.isAlvo()) {
            ArrayUnorderedList<String> caminhoAlvo = caminhoMaisCurto(
                    posicaoAtual,
                    missaoAtual.getAlvo().getRoom()
            );
            System.out.println("  Melhor caminho para o alvo: " + caminhoAlvo);
        } else {
            System.out.println("  Alvo já recuperado.");
        }

        ArrayUnorderedList<String> caminhoKit = caminhoKitMaisProximo();
        if (caminhoKit == null) {
            System.out.println("  Não existem kits de recuperação por recolher.");
        } else {
            System.out.println("  Caminho para o kit mais próximo: " + caminhoKit);
        }
    }

    /**
     * Obtém o caminho mais curto entre duas divisões.
     *
     * @param inicio divisão inicial
     * @param fim divisão final
     * @return caminho mais curto
     */
    private ArrayUnorderedList<String> caminhoMaisCurto(String inicio, String fim) {
        ArrayUnorderedList<String> caminho = new ArrayUnorderedList<>();
        Iterator<String> iterator = missaoAtual.getEdificio().shortpath(inicio, fim);
        while (iterator.hasNext()) {
            caminho.addToRear(iterator.next());
        }
        return caminho;
    }

    /**
     * Procura o kit de vida não recolhido que se encontra mais perto da
     * posição atual.
     *
     * @return caminho para o kit mais próximo ou null se não existirem kits
     */
    private ArrayUnorderedList<String> caminhoKitMaisProximo() {
        ArrayUnorderedList<String> melhor = null;
        for (Item item : missaoAtual.getItens()) {
            if (!(item instanceof KitVida)) {
                continue;
            }

            ArrayUnorderedList<String> caminho = caminhoMaisCurto(posicaoAtual, item.getDivisao());
            if (caminho.size() > 0 && (melhor == null || caminho.size() < melhor.size())) {
                melhor = caminho;
            }
        }
        return melhor;
    }

    /**
     * Termina a simulação com sucesso e regista o percurso manual.
     */
    private void terminarVitoria() {
        System.out.println("Missão concluída com sucesso.");
        realizarRelatorio();
    }

    /**
     * Termina a simulação sem sucesso e regista o percurso manual.
     *
     * @param motivo motivo da derrota
     */
    private void terminarDerrota(String motivo) {
        System.out.println("Missão falhou: " + motivo);
        realizarRelatorio();
    }

    /**
     * Exporta para JSON o percurso e o estado final da simulação manual.
     */
    private void realizarRelatorio() {
        ArrayUnorderedList<String> caminho = new ArrayUnorderedList<>();
        for (String divisao : caminhoPercorrido) {
            caminho.addToRear(divisao);
        }

        RelatorioSimulacaoManual relatorio = new RelatorioSimulacaoManual(
                missaoAtual.getCodMissao(),
                missaoAtual.getVersao(),
                caminho,
                jogador.getVida(),
                jogador.getArmor()
        );
        ReadWriteJson.exportarRelatorio(relatorio);
        relatoriosSimulacoes.add(relatorio);
    }
}
