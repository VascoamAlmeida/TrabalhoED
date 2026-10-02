package elementosJogo;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Lists.ArrayUnorderedList;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe responsável pelo menu principal do simulador.
 */
public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private final ReadWriteJson readWriteJson = new ReadWriteJson();
    private Missao missaoAtual;

    /**
     * Apresenta o menu principal.
     *
     * @throws EmptyCollectionException caso uma operação sobre as coleções
     *                                  seja efetuada sem elementos
     */
    public void mainMenu() throws EmptyCollectionException {
        int opcao;

        do {
            System.out.println("\n\n********** Missão Impossível **********");
            System.out.println("1 - Importar mapa/missão");
            System.out.println("2 - Ler relatórios manuais da missão carregada");
            System.out.println("3 - Visualizar mapa");
            System.out.println("4 - Iniciar simulação manual");
            System.out.println("5 - Iniciar simulação automática");
            System.out.println("0 - Sair do jogo");
            System.out.println("***************************************");

            opcao = obterOpcao();

            switch (opcao) {
                case 1:
                    importarMapa();
                    break;
                case 2:
                    lerRelatorios();
                    break;
                case 3:
                    visualizarMapa();
                    break;
                case 4:
                    iniciarSimulacaoManual();
                    break;
                case 5:
                    iniciarSimulacaoAutomatica();
                    break;
                case 0:
                    System.out.println("Sair do jogo");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    /**
     * Obtém uma opção numérica introduzida pelo utilizador.
     *
     * @return opção introduzida ou -1 se o valor não for numérico
     */
    private int obterOpcao() {
        int opcao = -1;
        try {
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Número inválido.");
            scanner.next();
        }
        return opcao;
    }

    /**
     * Permite escolher um ficheiro JSON e carregar uma das versões da missão
     * nele contidas.
     */
    private void importarMapa() {
        String caminhoFicheiro = conteudoPasta("src/resorces/Missoes");
        if (caminhoFicheiro == null) {
            return;
        }

        try {
            ArrayUnorderedList<Missao> missoes = readWriteJson.readJSON(caminhoFicheiro);
            if (missoes.size() == 1) {
                missaoAtual = missoes.first();
            } else {
                System.out.println("Escolha a versão da missão que deseja simular:");
                for (int i = 0; i < missoes.size(); i++) {
                    System.out.println(i + " - Missão: "
                            + missoes.get(i).getCodMissao()
                            + ", versão: " + missoes.get(i).getVersao());
                }

                int opcao;
                do {
                    opcao = obterOpcao();
                    if (opcao < 0 || opcao >= missoes.size()) {
                        System.out.println("Opção inválida.");
                    }
                } while (opcao < 0 || opcao >= missoes.size());

                missaoAtual = missoes.get(opcao);
            }

            System.out.println("Missão importada com sucesso.");
            System.out.println("Missão: " + missaoAtual.getCodMissao()
                    + " | Versão: " + missaoAtual.getVersao());
        } catch (Exception e) {
            System.out.println("Erro ao importar a missão: " + e.getMessage());
        }
    }

    /**
     * Apresenta os ficheiros JSON de uma pasta e devolve o escolhido.
     *
     * @param caminho caminho da pasta
     * @return caminho do ficheiro escolhido ou null se não existirem ficheiros
     */
    private String conteudoPasta(String caminho) {
        File pasta = new File(caminho);
        if (!pasta.isDirectory()) {
            System.out.println("Pasta não encontrada: " + caminho);
            return null;
        }

        File[] ficheiros = pasta.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".json"));
        if (ficheiros == null || ficheiros.length == 0) {
            System.out.println("A pasta não contém ficheiros JSON.");
            return null;
        }

        System.out.println("Ficheiros disponíveis:");
        for (int i = 0; i < ficheiros.length; i++) {
            System.out.println(i + " - " + ficheiros[i].getName());
        }

        int opcao;
        do {
            opcao = obterOpcao();
            if (opcao < 0 || opcao >= ficheiros.length) {
                System.out.println("Opção inválida.");
            }
        } while (opcao < 0 || opcao >= ficheiros.length);

        return ficheiros[opcao].getPath();
    }

    /**
     * Apresenta os resultados das simulações manuais da missão carregada,
     * ordenados pela vida restante.
     */
    private void lerRelatorios() {
        if (missaoAtual == null) {
            System.out.println("Importe primeiro a missão que pretende consultar.");
            return;
        }

        System.out.println("------------------------------");
        readWriteJson.importarRelatorios(
                "src/resorces/Relatorios/relatorios.json",
                missaoAtual.getCodMissao()
        );
        System.out.println("------------------------------");
    }

    /**
     * Apresenta na consola a estrutura da missão carregada.
     */
    private void visualizarMapa() {
        if (missaoAtual == null) {
            System.out.println("Não existe uma missão carregada.");
            return;
        }

        System.out.println("Mapa da missão:");
        System.out.println(missaoAtual);
    }

    /**
     * Inicia uma simulação manual da missão carregada.
     */
    private void iniciarSimulacaoManual() {
        if (missaoAtual == null) {
            System.out.println("Não existe uma missão carregada.");
            return;
        }

        SimulacaoManual manual = new SimulacaoManual(missaoAtual, scanner);
        manual.iniciar();
    }

    /**
     * Calcula automaticamente o percurso que conserva mais pontos de vida,
     * considerando todas as entradas e saídas da missão.
     */
    private void iniciarSimulacaoAutomatica() {
        if (missaoAtual == null) {
            System.out.println("Não existe uma missão carregada.");
            return;
        }

        SimulacaoAutomatica automatica = new SimulacaoAutomatica(missaoAtual);
        automatica.iniciar();
    }
}
