package elementosJogo;

import Collections.Lists.ArrayUnorderedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsável pela leitura e escrita dos ficheiros JSON do simulador.
 */
public class ReadWriteJson {

    private static final String CAMINHO_RELATORIOS = "src/resorces/Relatorios/relatorios.json";

    /**
     * Lê um ficheiro JSON de missão. É aceite tanto o formato original do
     * enunciado (uma missão no objeto raiz) como o formato usado no projeto
     * para agrupar várias versões através da chave {@code missoes}.
     *
     * @param filePath caminho do ficheiro JSON
     * @return lista de missões encontradas no ficheiro
     */
    public ArrayUnorderedList<Missao> readJSON(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Caminho do ficheiro inválido.");
        }

        ArrayUnorderedList<Missao> missoes = new ArrayUnorderedList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            Object parsed = jsonParser.parse(reader);
            if (!(parsed instanceof JSONObject)) {
                throw new IllegalArgumentException("O ficheiro JSON deve conter um objeto na raiz.");
            }

            JSONObject root = (JSONObject) parsed;
            Object conjuntoMissoes = root.get("missoes");

            if (conjuntoMissoes instanceof JSONArray) {
                JSONArray missoesArray = (JSONArray) conjuntoMissoes;
                if (missoesArray.isEmpty()) {
                    throw new IllegalArgumentException("O ficheiro não contém missões.");
                }

                for (Object objeto : missoesArray) {
                    if (!(objeto instanceof JSONObject)) {
                        throw new IllegalArgumentException("Existe uma missão com formato inválido.");
                    }
                    JSONObject missaoObj = (JSONObject) objeto;
                    validarMissaoJson(missaoObj);
                    missoes.addToRear(criarMissao(missaoObj));
                }
            } else {
                validarMissaoJson(root);
                missoes.addToRear(criarMissao(root));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Ficheiro não encontrado: " + filePath, e);
        } catch (ParseException e) {
            throw new IllegalArgumentException("JSON inválido: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o ficheiro: " + e.getMessage(), e);
        }

        return missoes;
    }

    /**
     * Valida os elementos essenciais de uma missão antes de a importar.
     *
     * @param missaoObj objeto JSON da missão
     */
    private void validarMissaoJson(JSONObject missaoObj) {
        if (!(missaoObj.get("cod-missao") instanceof String)
                || ((String) missaoObj.get("cod-missao")).isBlank()) {
            throw new IllegalArgumentException("A missão não possui um cod-missao válido.");
        }
        if (!(missaoObj.get("versao") instanceof Number)) {
            throw new IllegalArgumentException("A missão não possui uma versão válida.");
        }

        JSONArray divisoes = obterArrayObrigatorio(missaoObj, "edificio");
        if (divisoes.isEmpty()) {
            throw new IllegalArgumentException("O edifício deve possuir pelo menos uma divisão.");
        }
        for (int i = 0; i < divisoes.size(); i++) {
            Object divisao = divisoes.get(i);
            if (!(divisao instanceof String) || ((String) divisao).isBlank()) {
                throw new IllegalArgumentException("Existe uma divisão inválida no edifício.");
            }
            for (int j = i + 1; j < divisoes.size(); j++) {
                if (divisao.equals(divisoes.get(j))) {
                    throw new IllegalArgumentException("Divisão repetida no edifício: " + divisao);
                }
            }
        }

        JSONArray ligacoes = obterArrayObrigatorio(missaoObj, "ligacoes");
        for (Object objetoLigacao : ligacoes) {
            if (!(objetoLigacao instanceof JSONArray)) {
                throw new IllegalArgumentException("Existe uma ligação com formato inválido.");
            }
            JSONArray ligacao = (JSONArray) objetoLigacao;
            if (ligacao.size() != 2
                    || !(ligacao.get(0) instanceof String)
                    || !(ligacao.get(1) instanceof String)) {
                throw new IllegalArgumentException("Cada ligação deve conter exatamente duas divisões.");
            }
            validarDivisaoExistente(divisoes, (String) ligacao.get(0), "ligação");
            validarDivisaoExistente(divisoes, (String) ligacao.get(1), "ligação");
        }

        JSONArray inimigos = obterArrayObrigatorio(missaoObj, "inimigos");
        for (Object objetoInimigo : inimigos) {
            if (!(objetoInimigo instanceof JSONObject)) {
                throw new IllegalArgumentException("Existe um inimigo com formato inválido.");
            }
            JSONObject inimigo = (JSONObject) objetoInimigo;
            if (!(inimigo.get("nome") instanceof String)
                    || !(inimigo.get("poder") instanceof Number)
                    || !(inimigo.get("divisao") instanceof String)) {
                throw new IllegalArgumentException("Existe um inimigo com dados incompletos.");
            }
            if (((Number) inimigo.get("poder")).intValue() < 0) {
                throw new IllegalArgumentException("O poder de um inimigo não pode ser negativo.");
            }
            validarDivisaoExistente(divisoes, (String) inimigo.get("divisao"), "inimigo");
        }

        JSONArray entradasSaidas = obterArrayObrigatorio(missaoObj, "entradas-saidas");
        if (entradasSaidas.isEmpty()) {
            throw new IllegalArgumentException("A missão deve possuir pelo menos uma entrada/saída.");
        }
        for (Object entradaSaida : entradasSaidas) {
            if (!(entradaSaida instanceof String)) {
                throw new IllegalArgumentException("Existe uma entrada/saída inválida.");
            }
            validarDivisaoExistente(divisoes, (String) entradaSaida, "entrada/saída");
        }

        Object alvoObj = missaoObj.get("alvo");
        if (!(alvoObj instanceof JSONObject)) {
            throw new IllegalArgumentException("A missão não possui um alvo válido.");
        }
        JSONObject alvo = (JSONObject) alvoObj;
        if (!(alvo.get("divisao") instanceof String) || !(alvo.get("tipo") instanceof String)) {
            throw new IllegalArgumentException("Os dados do alvo são inválidos.");
        }
        validarDivisaoExistente(divisoes, (String) alvo.get("divisao"), "alvo");

        JSONArray itens = obterArrayObrigatorio(missaoObj, "itens");
        for (Object objetoItem : itens) {
            if (!(objetoItem instanceof JSONObject)) {
                throw new IllegalArgumentException("Existe um item com formato inválido.");
            }
            JSONObject item = (JSONObject) objetoItem;
            if (!(item.get("divisao") instanceof String) || !(item.get("tipo") instanceof String)) {
                throw new IllegalArgumentException("Existe um item com dados incompletos.");
            }
            validarDivisaoExistente(divisoes, (String) item.get("divisao"), "item");

            boolean kit = item.get("pontos-recuperados") instanceof Number;
            boolean colete = item.get("pontos-extra") instanceof Number;
            if (kit == colete) {
                throw new IllegalArgumentException(
                        "Cada item deve possuir pontos-recuperados ou pontos-extra, mas não ambos.");
            }
        }
    }

    /**
     * Obtém um array obrigatório do JSON.
     *
     * @param objeto objeto que contém a chave
     * @param chave chave pretendida
     * @return array associado à chave
     */
    private JSONArray obterArrayObrigatorio(JSONObject objeto, String chave) {
        Object valor = objeto.get(chave);
        if (!(valor instanceof JSONArray)) {
            throw new IllegalArgumentException("A chave '" + chave + "' é obrigatória e deve ser um array.");
        }
        return (JSONArray) valor;
    }

    /**
     * Confirma que uma divisão referenciada existe no edifício.
     *
     * @param divisoes lista de divisões do edifício
     * @param divisao divisão a validar
     * @param contexto elemento que referencia a divisão
     */
    private void validarDivisaoExistente(JSONArray divisoes, String divisao, String contexto) {
        for (Object existente : divisoes) {
            if (divisao.equals(existente)) {
                return;
            }
        }
        throw new IllegalArgumentException(
                "A divisão '" + divisao + "' referida em " + contexto + " não existe no edifício.");
    }

    /**
     * Constrói uma missão a partir de um objeto JSON já validado.
     *
     * @param missaoObj objeto JSON validado
     * @return missão criada
     */
    private Missao criarMissao(JSONObject missaoObj) {
        Missao missao = new Missao();
        missao.setCodMissao((String) missaoObj.get("cod-missao"));
        missao.setVersao(((Number) missaoObj.get("versao")).intValue());

        JSONArray divisoesArray = (JSONArray) missaoObj.get("edificio");
        for (Object divisao : divisoesArray) {
            missao.addDivisao((String) divisao);
        }

        JSONArray ligacoesArray = (JSONArray) missaoObj.get("ligacoes");
        for (Object objetoLigacao : ligacoesArray) {
            JSONArray ligacao = (JSONArray) objetoLigacao;
            missao.addligacao((String) ligacao.get(0), (String) ligacao.get(1));
        }

        JSONArray inimigosArray = (JSONArray) missaoObj.get("inimigos");
        for (Object objetoInimigo : inimigosArray) {
            JSONObject inimigo = (JSONObject) objetoInimigo;
            missao.setInimigos(
                    (String) inimigo.get("nome"),
                    ((Number) inimigo.get("poder")).intValue(),
                    (String) inimigo.get("divisao")
            );
        }

        JSONArray entradaSaidaArray = (JSONArray) missaoObj.get("entradas-saidas");
        for (Object entradaSaida : entradaSaidaArray) {
            missao.setEntradasSaidas((String) entradaSaida);
        }

        JSONObject alvo = (JSONObject) missaoObj.get("alvo");
        missao.setAlvo((String) alvo.get("divisao"), (String) alvo.get("tipo"));

        JSONArray itemArray = (JSONArray) missaoObj.get("itens");
        for (Object objetoItem : itemArray) {
            JSONObject item = (JSONObject) objetoItem;
            String divisao = (String) item.get("divisao");
            String tipo = (String) item.get("tipo");
            if (item.get("pontos-recuperados") instanceof Number) {
                missao.setHealtKit(
                        divisao,
                        ((Number) item.get("pontos-recuperados")).intValue(),
                        tipo
                );
            } else {
                missao.setArmor(
                        divisao,
                        ((Number) item.get("pontos-extra")).intValue(),
                        tipo
                );
            }
        }

        return missao;
    }

    /**
     * Exporta o resultado de uma simulação manual para o ficheiro de
     * relatórios do projeto.
     *
     * @param relatorio relatório da simulação
     */
    public static void exportarRelatorio(RelatorioSimulacaoManual relatorio) {
        exportarRelatorioParaFicheiro(relatorio, CAMINHO_RELATORIOS);
    }

    /**
     * Importa e apresenta os relatórios de uma missão, ordenados por pontos de
     * vida restantes de forma decrescente.
     *
     * @param caminhoArquivo caminho do ficheiro de relatórios
     * @param codMissao código da missão a apresentar
     */
    public void importarRelatorios(String caminhoArquivo, String codMissao) {
        File ficheiro = new File(caminhoArquivo);
        if (!ficheiro.exists() || ficheiro.length() == 0) {
            System.out.println("Não existem relatórios manuais guardados.");
            return;
        }

        try (FileReader reader = new FileReader(caminhoArquivo)) {
            JSONParser parser = new JSONParser();
            JSONObject dados = (JSONObject) parser.parse(reader);
            ArrayUnorderedList<String> apresentados = new ArrayUnorderedList<>();
            boolean encontrou = false;

            System.out.println("Relatórios da missão '" + codMissao + "' (ordenados por vida restante):");

            while (true) {
                String melhorChave = null;
                JSONObject melhorRelatorio = null;
                int melhorVida = Integer.MIN_VALUE;

                for (Object keyObject : dados.keySet()) {
                    String chave = (String) keyObject;
                    if (jaApresentado(apresentados, chave)) {
                        continue;
                    }

                    JSONObject relatorio = (JSONObject) dados.get(chave);
                    if (!codMissao.equals(String.valueOf(relatorio.get("codMissao")))) {
                        continue;
                    }

                    int vida = ((Number) relatorio.get("vidaFinal")).intValue();
                    if (melhorRelatorio == null || vida > melhorVida) {
                        melhorChave = chave;
                        melhorRelatorio = relatorio;
                        melhorVida = vida;
                    }
                }

                if (melhorRelatorio == null) {
                    break;
                }

                encontrou = true;
                apresentados.addToRear(melhorChave);
                apresentarRelatorio(melhorChave, melhorRelatorio);
            }

            if (!encontrou) {
                System.out.println("Ainda não existem simulações manuais guardadas para esta missão.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao importar relatórios: " + e.getMessage());
        }
    }

    /**
     * Verifica se uma chave de relatório já foi apresentada.
     *
     * @param apresentados chaves já apresentadas
     * @param chave chave a verificar
     * @return true se a chave já tiver sido apresentada
     */
    private boolean jaApresentado(ArrayUnorderedList<String> apresentados, String chave) {
        for (String apresentada : apresentados) {
            if (apresentada.equals(chave)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Apresenta os dados de um relatório na consola.
     *
     * @param chave identificador do relatório
     * @param relatorio dados do relatório
     */
    private void apresentarRelatorio(String chave, JSONObject relatorio) {
        System.out.println("Relatório: " + chave);
        System.out.println("  Código da Missão: " + relatorio.get("codMissao"));
        System.out.println("  Versão: " + relatorio.get("versao"));
        System.out.println("  Vida Final: " + relatorio.get("vidaFinal"));
        System.out.println("  Pontos extra: " + relatorio.get("armorFinal"));
        System.out.println("  Caminho: " + relatorio.get("caminho"));
        System.out.println();
    }

    /**
     * Mantém compatibilidade com o método usado na versão original do
     * projeto para exportar um relatório para um caminho específico.
     *
     * @param relatorio relatório a exportar
     * @param caminho caminho de destino
     */
    public static void exportarRelatorioAutomaticos(RelatorioSimulacaoManual relatorio, String caminho) {
        exportarRelatorioParaFicheiro(relatorio, caminho);
    }

    /**
     * Adiciona um relatório a um ficheiro JSON, criando a pasta e o ficheiro
     * quando necessário.
     *
     * @param relatorio relatório a guardar
     * @param caminho caminho do ficheiro
     */
    private static void exportarRelatorioParaFicheiro(RelatorioSimulacaoManual relatorio, String caminho) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("codMissao", relatorio.getCodMissao());
        jsonObject.put("versao", relatorio.getVersao());
        jsonObject.put("vidaFinal", relatorio.getVidaFinal());
        jsonObject.put("armorFinal", relatorio.getArmorFinal());
        jsonObject.put("caminho", relatorio.getCaminho().toString());

        JSONObject dados = new JSONObject();
        File ficheiro = new File(caminho);
        File pasta = ficheiro.getParentFile();
        if (pasta != null && !pasta.exists() && !pasta.mkdirs()) {
            System.err.println("Não foi possível criar a pasta de relatórios.");
            return;
        }

        if (ficheiro.exists() && ficheiro.length() > 0) {
            try (FileReader reader = new FileReader(ficheiro)) {
                JSONParser parser = new JSONParser();
                Object parsed = parser.parse(reader);
                if (parsed instanceof JSONObject) {
                    dados = (JSONObject) parsed;
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar relatórios existentes: " + e.getMessage());
                return;
            }
        }

        int proximoID = 1;
        while (dados.containsKey("simulacao_" + proximoID)) {
            proximoID++;
        }
        dados.put("simulacao_" + proximoID, jsonObject);

        try (FileWriter file = new FileWriter(ficheiro)) {
            file.write(dados.toJSONString());
            System.out.println("Relatório exportado para: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao exportar JSON: " + e.getMessage());
        }
    }
}
