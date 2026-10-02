package Collections.Graphs;

import Collections.Exceptions.ElementNotFoundException;

import Collections.Lists.*;
import Collections.Queues.*;
import Collections.Stacks.*;

import java.util.Iterator;


/**
 * Graph represents an adjacency matrix implementation of a graph.
 *
 * @param <T> generico
 */
public class Graph<T> implements GraphADT<T> {

    /**
     * variaveis capacidade
     */
    protected final int DEFAULT_CAPACITY = 100;
    /**
     * variavel numVertices
     */
    protected int numVertices; // number of vertices in the graph
    /**
     * variavel boolean adjMatrix
     */
    protected boolean[][] adjMatrix; // adjacency matrix
    /**
     * variavel vertices
     */
    protected T[] vertices; // values of vertices

    /**
     * Creates an empty graph.
     */
    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if
     * necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    @Override
    public void addVertex(T vertex) {

        if (numVertices == vertices.length) {
            expandCapacity();
        }

        //Adiciona vertex ao array
        vertices[numVertices] = vertex;

        //Inicia as ligaçoes dos adjacentes, mas ainda nao os liga(false)
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    /**
     * Vai remover 1 vertex do grafo
     *
     * @param vertex ser removido do grafo
     */
    @Override
    public void removeVertex(T vertex) throws ElementNotFoundException {
        if (vertex == null) {
            throw new ElementNotFoundException("vertice nulo");
        }

        if (getIndex(vertex) == -1) {
            throw new ElementNotFoundException("Vértice não encontrado no grafo.");
        }

        removeVertex(getIndex(vertex));
    }

    /**
     * Remove o vertice do grafo pelo index fornecido. Ajusta o array vertices e
     * a matriz adjacencias para garantir cosistencia grafo
     *
     * @param index vertice a ser removido
     */
    public void removeVertex(int index) {
        if (indexIsValid(index)) {
            numVertices--;

            // Troca o vértice removido com o último vértice
            vertices[index] = vertices[numVertices];

            // Ajusta a matriz de adjacências
            for (int i = 0; i < numVertices; i++) {
                // Remove as ligações da linha do vértice removido (substituído)
                adjMatrix[i][index] = adjMatrix[i][numVertices];
                adjMatrix[index][i] = adjMatrix[numVertices][i];
            }
        }
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true; //Ligar index1 com index2
            adjMatrix[index2][index1] = true; //Ligar index2 com index1
        }
    }

    /**
     * Procura o vertex no grafo e returna o index no array
     *
     * @param vertex que se procura
     * @return o index do vertex do array
     */
    public int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Verifica se é 1 index valido i
     *
     * @param index valor a ser validado
     * @return Se é valido se tiver entre 0 e num Vertices
     */
    private boolean indexIsValid(int index) {
        return ((index < numVertices) && index >= 0);
    }

    /**
     * Remove vertex entre 2 vertex do grafo
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Removoe 1 vertex entre 2 vertex do grafo
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    public void removeEdge(int index1, int index2) {
        if (!indexIsValid(index1)) {
            throw new IllegalArgumentException("O primeiro vertice ñ é valido");
        }

        if (!indexIsValid(index2)) {
            throw new IllegalArgumentException("O segundo vertice ñ é valido");
        }

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false; //Remove a ligaçao entre index1 e index2
            adjMatrix[index2][index1] = false; //Remove a ligaçao entre index2 e index1
        }
    }

    /**
     * Returns an iterator that performs a breadth first search traversal
     * starting at the given index.
     *
     * @param startVertex the index to begin the search from
     * @return an iterator that performs a breadth first traversal
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        return iteratorBFS(getIndex(startVertex));
    }

    /**
     * Retorna 1 iterador que realiza a travessia em largura a partir de 1 index
     *
     * @param startIndex onde travessia em largura começa
     * @return 1 iterador que percorre grafo
     */
    private Iterator<T> iteratorBFS(int startIndex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        //Inicia o array de vertices visitados
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        //Adiciona index inicial a fila e marca como visitado
        traversalQueue.enqueue(Integer.valueOf(startIndex));
        visited[startIndex] = true;

        //Enquanto tiver vertex na fila, continua travessia
        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue(); //remove vertex da fila
            resultList.addToRear(vertices[x.intValue()]); //adiciona vertex a fila resultados
            /**
             * Find all vertices adjacent to x that have not been visited and
             * queue them up
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalQueue.enqueue(Integer.valueOf(i)); //adiciona vertex a fila
                    visited[i] = true; //marca como visitado
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Retorna 1 iterado que realiza travessia em profundidade a partir vertex
     *
     * @param startVertex onde travessia começa
     * @return 1 iterador que percorre grafo
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        return iteratorDFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a depth first search traversal starting
     * at the given index.
     *
     * @param startIndex the index to begin the search traversal from
     * @return an iterator that performs a depth first traversal
     */
    public Iterator<T> iteratorDFS(int startIndex) {
        Integer x; //armazena index atual
        boolean found; //Flag indica vertex adjacente encontrado
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        //array para marcar vertex visitados
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        //Incia array de vertex visitados
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        //Addiciona o index inicial a pilha e marca como visitado
        traversalStack.push(Integer.valueOf(startIndex));
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        //Enquanto tiver vertex na pilha continua travessia
        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            /**
             * Find a vertex adjacent to x that has not been visited and push it
             * on the stack
             */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(Integer.valueOf(i)); //adiciona vertex a pilha
                    resultList.addToRear(vertices[i]); //adiciona vertex a lista resultados 
                    visited[i] = true; //Marca o vertex como visitado
                    found = true;  //Marca que 1 vertex foi encontrado
                }
            }

            //Se nenhum vertex adjacentes for encontrado, remove vertice da pilha
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    /**
     * Retorna 1 iterador tem caminho + curto entre 2 vertices
     *
     * https://faculty.washington.edu/moishe/javademos/ch18%20Code/jss2/Graph.java
     *
     * @param startVertex index inicial
     * @param targetVertex index que se pretende
     * @return caminho mais curto
     */
    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    /**
     * Retorna 1 iterador que contem caminho + curto entre os 2 vertices
     *
     * https://faculty.washington.edu/moishe/javademos/ch18%20Code/jss2/Graph.java
     *
     * @param startIndex index inicial
     * @param targetIndex index final
     * @return 1 iterador que contem caminho + curtos entre os 2 vertices
     */
    private Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        //Verifica se indice são validos
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        //Fz iterado com os index dos vertex caminho + curto
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);;

        //Converte index para objt e adiciona lista
        while (it.hasNext()) {
            resultList.addToRear(vertices[(it.next())]);
        }

        return resultList.iterator();
    }

    /**
     * Calculo do caminho + curto entre 2 vertices
     *
     * https://faculty.washington.edu/moishe/javademos/ch18%20Code/jss2/Graph.java
     *
     * @param startIndex index começa
     * @param targetIndex index vai ter
     * @return caminho mais curto
     */
    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index = startIndex; //indicar index inicial
        int[] pathLength = new int[numVertices]; //Guarda caminho ate cada vertex
        int[] predecessor = new int[numVertices]; //Guarda index do predecessor
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        if (startIndex == targetIndex) {
            resultList.addToRear(startIndex);
            return resultList.iterator();
        }

        //Marca os vertex ñ visitados
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        //Configura vertex inical para a BFS
        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        //Realiza BFS
        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            index = (traversalQueue.dequeue());

            //Atualiza o pathLength para cada vértice não visitado adjacente ao vertex no índice atual
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) { //verifica adjcencia e ñ foi visitada
                    pathLength[i] = pathLength[index] + 1; //atualiza comprimento
                    predecessor[i] = index; //define predecesssor
                    traversalQueue.enqueue(i); // adiciona vertex adjacente a fila
                    visited[i] = true; //marca visitado
                }
            }
        }

        //nenhum caminho deve ter sido encontrado
        if (index != targetIndex) {
            return resultList.iterator();
        }

        //Contrui caminho em pilha
        LinkedStack<Integer> stack = new LinkedStack<>();
        index = targetIndex;
        stack.push(index);

        //Segue precessors ate vertex inicial
        do {
            index = predecessor[index];
            stack.push(index);

        } while (index != startIndex);

        //Tranfere index da pilha para lista resultados
        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop());
        }

        return resultList.iterator();
    }

    /**
     * Verifica se esta vazio
     *
     * @return true se estiver vazio
     */
    @Override
    public boolean isEmpty() {

        return (numVertices == 0);
    }

    /**
     * Verifica se grafo esta connected
     *
     * @return true se grafo estiver connected
     */
    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }

        //Vai realizar a procura a partir do vertex
        Iterator<T> it = iteratorBFS(0);
        //inicia contador dos vertex que foram visitados
        int count = 0;

        //Cada vez percorre 1 vertex incrementa o count
        while (it.hasNext()) {
            it.next();
            count++;
        }

        //Se count for igual numVertices entao grafo e connected
        return (count == numVertices);
    }

    /**
     * Vai ver numero de vertex do grafo
     *
     * @return numero vertex do grafo
     */
    @Override
    public int size() {

        return numVertices;
    }

    /**
     * Expands the capacity of the array if necessary.
     */
    protected void expandCapacity() {
        {
            T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
            boolean[][] largerAdjMatrix
                    = new boolean[vertices.length * 2][vertices.length * 2];

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
     * Metodo publico para obter matriz adjacencias
     *
     * @return matriz adjacencias
     */
    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * Metodo publico para obter os vértices
     *
     * @return vertices
     */
    public T[] getVertices() {
        return vertices;
    }

    /**
     * Vai realizar string do grafo, + matriz adjacentes e valores vertex
     *
     * @return 1 string que representa o grafo, + matriz adjacentes e valores
     * vertex
     */
    public String toString() {
        if (numVertices == 0) {
            return "Graph is empty";
        }

        String result = new String("");

        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i;
            if (i < 10) {
                result += " ";
            }
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j]) {
                    result += "1 ";
                } else {
                    result += "0 ";
                }
            }
            result += "\n";
        }

        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }
        result += "\n";
        return result;
    }
}
