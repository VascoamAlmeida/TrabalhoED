package elementosJogo;

import Collections.Graphs.*;
import Collections.Lists.*;

/**
 * Class auxiliar para obter os vizinhos da minha divisão atual
 *
 * @param <T> variavel generica
 */
public class ObterVizinhos<T> {

    /**
     * Metodo para obter os vizinhos
     *
     * @param graph  dado
     * @param vertex vertex dado
     * @return vizinhos vertex dado
     */
    public ArrayUnorderedList<T> getVizinhos(Graph<T> graph, T vertex) {
        //Cria lista vizinhos encontrados
        ArrayUnorderedList<T> vizinhos = new ArrayUnorderedList<>();

        int index = graph.getIndex(vertex);

        if (index == -1) {
            System.out.println("vertice ñ encontrado");
            return vizinhos;
        }

        //Obter matriz adjacencias, que estao connected
        boolean[][] adjMatrix = graph.getAdjMatrix();

        //Obter lista todos vertices
        T[] vertices = graph.getVertices();

        //Percorrer todos vertices grafo para ver vi<inhos
        for (int i = 0; i < graph.size(); i++) {
            if (adjMatrix[index][i]) {
                //Adiciona vertice a lista
                vizinhos.addToRear(vertices[i]);
            }
        }
        return vizinhos;
    }
}
