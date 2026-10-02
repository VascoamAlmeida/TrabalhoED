package Collections.Graphs;

import Collections.Lists.*;
import Collections.Queues.*;
import Collections.Stacks.*;
import Collections.Trees.*;
import Collections.Graphs.*;

import java.util.Iterator;

/**
 * Class para graph de network
 *
 * @param <T> generico
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {

    /**
     * variavel de matriz
     */
    protected double[][] adjMatrix;

    /**
     * Creates an empty graph.
     *
     */
    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight da edge
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    /**
     *
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     * @param weight peo
     */
    public void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight; //Ligar index1 com index2
            adjMatrix[index2][index1] = weight; //Ligar index2 com index1
        }
    }

    /**
     * Metodo fazer caminho mais curto com peso
     *
     * @param vertex1 vertice 1
     * @param vertex2 vertice 2
     * @return caminho mais curto com peso
     */
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        return shortestPathWeight(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Metodo auxiliar fazer caminho mais curto com peso
     *
     * @param startIndex index inical
     * @param targetIndex do objetivo
     * @return caminho mais curto com peso
     */
    private double shortestPathWeight(int startIndex, int targetIndex) {

        double result = 0;
        //Verifica se indice são validos
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return Double.POSITIVE_INFINITY;
        }

        int index1, index2;
        //Fz iterado com os index dos vertex caminho + curto
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);

        if (it.hasNext()) {
         index1 = ((Integer)it.next()).intValue();
        } else {
            return Double.POSITIVE_INFINITY;
        }
        //Converte index para objt e adiciona lista
        while (it.hasNext()) {
            index2 = (it.next().intValue());
            result += adjMatrix[index1][index2];
            index1 = index2;
        }

        return result;
    }

    /**
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
     */
    public Network mstNetwork() {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        ArrayHeap<Double> minHeap = new ArrayHeap<Double>();
        Network<T> resultGraph = new Network<T>();

        resultGraph.adjMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        resultGraph.vertices = (T[]) (new Object[numVertices]);
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;

        /**
         * Add all edges, which are adjacent to the starting vertex, to the heap
         */
        for (int i = 0; i < numVertices; i++) {
            minHeap.addElement(Double.valueOf(adjMatrix[0][i]));
        }

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
            /**
             * Get the edge with the smallest weight that has exactly one vertex
             * already in the resultGraph
             */
            do {
                weight = (minHeap.removeMin()).doubleValue();
                edge = getEdgeWithWeightOf(weight, visited);
            } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));
            x = edge[0];
            y = edge[1];
            if (!visited[x]) {
                index = x;
            } else {
                index = y;
            }
            /**
             * Add the new edge and vertex to the resultGraph
             */
            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];
            /**
             * Add all edges, that are adjacent to the newly added vertex, to
             * the heap
             */
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && (this.adjMatrix[i][index]
                        < Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(Double.valueOf(adjMatrix[index][i]));
                }
            }
        }
        return resultGraph;
    }

    /**
     * Procura aresta no grafo com 1 peso e conneta 1 vertice ja visitado a 1
     * nao visitado
     *
     * @param weight da aresta procurada
     * @param visited array boolean indica os vertices visitados
     * @return array 2 inteiros
     */
    private int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        int[] edge = new int[2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) //Verifica peso da aresta e coneta os vertices
            {
                if ((adjMatrix[i][j] == weight) && (visited[i] ^ visited[j])) {
                    edge[0] = i;
                    edge[1] = j;
                    return edge;
                }
            }
        }

        /**
         * Will only get to here if a valid edge is not found
         */
        edge[0] = -1;
        edge[1] = -1;
        return edge;
    }

    /**
     * Verifica se é 1 index valido i
     *
     * @param index valor a ser validado
     * @return Se é valido se tiver entre 0 e num Vertices
     */
    private boolean indexIsValid(int index) {
        return (index >= 0 && index < numVertices);
    }

    /**
     * Procura o vertex no grafo e returna o index no array
     *
     * @param vertex que se procura
     * @return o index do vertex do array
     */
//    private int getIndex(T vertex) {
//        for (int i = 0; i < numVertices; i++) {
//            if (vertices[i].equals(vertex)) {
//                return i;
//            }
//        }
//        return -1;
//    }

    /**
     * Expands the capacity of the array if necessary.
     */
    protected void expandCapacity() {
        {
            T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
            double[][] largerAdjMatrix
                    = new double[vertices.length * 2][vertices.length * 2];

            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    largerAdjMatrix[i][j] = adjMatrix[i][j];
                }
                largerVertices[i] = vertices[i];
            }

            vertices = largerVertices;
            adjMatrix = largerAdjMatrix;
        }
    }

    /**
     * Metodo realizar string
     *
     * @return string matriz
     */
      public String toString()
   {
      if (numVertices == 0)
         return "Graph is empty";

      String result = new String("");

      /** Print the adjacency Matrix */
      result += "Adjacency Matrix\n";
      result += "----------------\n";
      result += "index\t";

      for (int i = 0; i < numVertices; i++) 
      {
         result += "" + i;
         if (i < 10)
            result += " ";
      }
      result += "\n\n";

      for (int i = 0; i < numVertices; i++)
      {
         result += "" + i + "\t";
      
         for (int j = 0; j < numVertices; j++)
         {
            if (adjMatrix[i][j] < Double.POSITIVE_INFINITY)
            result += adjMatrix[i][j] + " "; // Alteração aqui, mostrando o valor real            else
               result += "0 ";
         }
         result += "\n";
      }

      /** Print the vertex values */
      result += "\n\nVertex Values";
      result += "\n-------------\n";
      result += "index\tvalue\n\n";

      for (int i = 0; i < numVertices; i++)
      {
         result += "" + i + "\t";
         result += vertices[i].toString() + "\n";
      }

      /** Print the weights of the edges */
      result += "\n\nWeights of Edges";
      result += "\n----------------\n";
      result += "index\tweight\n\n";

      for (int i = 0; i < numVertices; i++)
      {
         for (int j = numVertices-1; j > i; j--)
         {
            if (adjMatrix[i][j] < Double.POSITIVE_INFINITY)
            {
               result += i + " to " + j + "\t";
               result += adjMatrix[i][j] + "\n";
            }
         }
      }

      result += "\n";
      return result;
   }

    /**
     * Metodo adicionar vertex
     */
    public void addVertex ()
   {
      if (numVertices == vertices.length)
         expandCapacity();

      vertices[numVertices] = null;
      for (int i = 0; i <= numVertices; i++)
      {
         adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
         adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
      }      
      numVertices++;
   }

    /**
     * Metodo adicionar vertex por elemento
     *
     * @param vertex the vertex to be added to this graph
     */
   public void addVertex (T vertex)
   {
      if (numVertices == vertices.length)
         expandCapacity();

      vertices[numVertices] = vertex;
      for (int i = 0; i <= numVertices; i++)
      {
         adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
         adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
      }      
      numVertices++;
   }
}
