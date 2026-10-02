package Collections;
import Collections.Exceptions.ElementNotFoundException;
import Collections.Lists.*;
import Collections.Queues.*;
import Collections.Stacks.*;
import Collections.Trees.*;
import Collections.Graphs.*;

import java.util.Iterator;

/**
 * Main class collections
 */
public class Collections {

    /**
     * Main
     *
     * @param args recebe argumentos
     */
    public static void main(String[] args) {

        HeapADT<Integer> heap = new ArrayHeap<>();

        System.out.println("Heap ao adicionar elementos: " + heap.isEmpty());
        heap.addElement(2);
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(4);
        heap.addElement(7);
        heap.addElement(25);
        heap.addElement(1);


        System.out.println("Heap ao adicionar elementos: ");
        System.out.println(heap.toString());

        System.out.println("Valor minimo da Heap é: " + heap.findMin());
        System.out.println("Heap ao remover o minimo: ");
        heap.removeMin();
        System.out.println(heap.toString());

        System.out.println("Valor minimo da Heap é: " + heap.findMin());
        System.out.println("Heap ao remover o minimo: ");
        heap.removeMin();
        System.out.println(heap.toString());

        heap.addElement(1);
        System.out.println("Valor minimo da Heap é: " + heap.findMin());
        System.out.println("Heap ao adicionar elementos: " + heap.isEmpty());
//
//        LinkedBinarySearchTree<Integer> arvore = new LinkedBinarySearchTree<>();
//
//        arvore.addElement(4);
//        arvore.addElement(5);
//        arvore.addElement(3);
//        arvore.addElement(4);
//        arvore.addElement(7);
//        arvore.addElement(2);
//        arvore.addElement(25);
//        arvore.addElement(22);
//        arvore.addElement(1);
//
//        System.out.println("Arvore ao adicionar elementos: ");
//        System.out.println(arvore.toString());
//
//        System.out.println("Tirar menor elemento: " + arvore.removeMin());
//        System.out.println("arvore ao remover menor elemento: ");
//
//        System.out.println("Tirar maior elemento:: " + arvore.removeMax());
//        System.out.println("arvore ao remover maior elemento: ");
//
//        try {
//            System.out.println("Menor: " + arvore.removeElement(7));
//        } catch (ElementNotFoundException e) {
//            System.out.println("arvore ao remover elemento 7: ");
//        }
//
//        //System.out.println("Ver se contem elemento 7: " + arvore.contains(7));
//        System.out.println("Ver se contem elemento 5: " + arvore.contains(5));
//        System.out.println(arvore.toString());
//
//
//        arvore.removeElement(4);
//        System.out.println("removento elemento 4: ");
//        System.out.println(arvore.toString());
//
//        System.out.println("PreOrder ");
//        Iterator<Integer> preOrderIterator = arvore.iteratorPreOrder();
//        while (preOrderIterator.hasNext()) {
//            Integer element = preOrderIterator.next();
//            System.out.print(element + " ");  // Imprime os elementos da travessia em uma linha
//        }
//        System.out.println();
//
//        System.out.println("PreOrder ");
//        Iterator<Integer> inOrderIterator = arvore.iteratorInOrder();
//        while (inOrderIterator.hasNext()) {
//            Integer element = inOrderIterator.next();
//            System.out.print(element + " ");  // Imprime os elementos da travessia em uma linha
//        }
//
//        System.out.println();
//
//        System.out.println("PostOrder ");
//        Iterator<Integer> postOrderIterator = arvore.iteratorPostOrder();
//        while (postOrderIterator.hasNext()) {
//            Integer element = postOrderIterator.next();
//            System.out.print(element + " ");  // Imprime os elementos da travessia em uma linha
//        }
//        System.out.println();
//
//        System.out.println("Level: ");
//        Iterator<Integer> levelOrderIterator = arvore.iteratorLevelOrder();
//        while (levelOrderIterator.hasNext()) {
//            Integer element = levelOrderIterator.next();
//            System.out.print(element + " ");  // Imprime os elementos da travessia em uma linha
//        }
//        System.out.println();

//        System.out.println("//--------------------Double Linked Ordered List--------------------------------//");
//        OrderedListADT<Integer> doubleLinkedOrderedList = new DoubleLinkedOrderedList<>();
//
//        System.out.println("Lista vazia?: " + doubleLinkedOrderedList.isEmpty());
//
//        doubleLinkedOrderedList.add(7);
//        doubleLinkedOrderedList.add(5);
//        doubleLinkedOrderedList.add(15);
//        doubleLinkedOrderedList.add(1);
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : doubleLinkedOrderedList) {
//            System.out.print(elem + " ");
//        }
//
//        System.out.println();
//        System.out.println("Lista backwords: ");
//        ((DoubleLinkedOrderedList<Integer>) doubleLinkedOrderedList).backward();
//
//        System.out.println();
//        System.out.println("Lista vazia?: " + doubleLinkedOrderedList.isEmpty());
//        System.out.println("Tamanho da lista: " + doubleLinkedOrderedList.size());
//        System.out.println("Primeiro elemento: " + doubleLinkedOrderedList.first());
//        System.out.println("Último elemento: " + doubleLinkedOrderedList.last());
//        System.out.println();
//
//        doubleLinkedOrderedList.add(2);
//        doubleLinkedOrderedList.add(55);
//        doubleLinkedOrderedList.add(22);
//        doubleLinkedOrderedList.add(6);
//        doubleLinkedOrderedList.add(-5);
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : doubleLinkedOrderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println("Tamanho da lista: " + doubleLinkedOrderedList.size());
//        System.out.println("Primeiro elemento: " + doubleLinkedOrderedList.first());
//        System.out.println("Último elemento: " + doubleLinkedOrderedList.last());
//
//        System.out.println();
//
//        System.out.println("Removendo o primeiro elemento: " + doubleLinkedOrderedList.removeFirst());
//        System.out.println("Removendo o último elemento: " + doubleLinkedOrderedList.removeLast());
//        System.out.println("Removendo o elemento 2: " + doubleLinkedOrderedList.remove(2));
//        System.out.println("Contem elemento 2: " + doubleLinkedOrderedList.contains(12));
//        System.out.println("Contem elemento 15: " + doubleLinkedOrderedList.contains(15));
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : doubleLinkedOrderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//
//
//
//// Exibir elementos da lista após a substituição
//        System.out.println("Elementos na lista após substituição:");
//        for (Integer elem : doubleLinkedOrderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//
//
//        System.out.println("------------------------DESORDENADO---------------------------------------");
//
//        UnorderedListADT<Integer> doubleLinkedUnorderedList = new DoubleLinkedUnorderedList<>();
//
//        System.out.println("Lista vazia?: " + doubleLinkedUnorderedList.isEmpty());
//
//        doubleLinkedUnorderedList.addToFront(5);
//        doubleLinkedUnorderedList.addToFront(3);
//        doubleLinkedUnorderedList.addToRear(7);
//        doubleLinkedUnorderedList.addToFront(11);
//        doubleLinkedUnorderedList.addToRear(2);
//        doubleLinkedUnorderedList.addToFront(1);
//
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : doubleLinkedUnorderedList) {
//            System.out.print(elem + " ");
//        }
//
//
//
//        System.out.println();
//        doubleLinkedUnorderedList.addAfter(15, 1);
//        //Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : doubleLinkedUnorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println("Primeiro elemento: " + doubleLinkedUnorderedList.first());
//        System.out.println("Último elemento: " + doubleLinkedUnorderedList.last());
//
//        System.out.println("Lista vazia?: " + doubleLinkedUnorderedList.isEmpty());
//        System.out.println();
//        System.out.println();
//        System.out.println("Removendo o primeiro elemento: " + doubleLinkedUnorderedList.removeFirst());
//        System.out.println("Removendo o último elemento: " + doubleLinkedUnorderedList.removeLast());
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : doubleLinkedUnorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println("Tamanho da lista: " + doubleLinkedUnorderedList.size());
//        System.out.println("Contem elemento 10: " + doubleLinkedUnorderedList.contains(10));
//        System.out.println("Contem elemento 5: " + doubleLinkedUnorderedList.contains(5));
//        System.out.println("Removendo o elemento 2: " + doubleLinkedUnorderedList.remove(5));
//        System.out.println();
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : doubleLinkedUnorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();

//        LinkedList<Integer> lista = new LinkedList<>();
//
//        System.out.println("Lista vazia?: " + lista.isEmpty());
//        lista.add(1);
//        lista.add(5);
//        lista.add(2);
//        lista.add(3);
//        lista.add(10);
//        lista.add(8);
//        lista.add(22);
//        System.out.println("Lista vazia?: " + lista.isEmpty());
//        System.out.println("Imprimindo elementos da lista normal:");
//        System.out.println(lista);
//        System.out.println("Imprimindo elementos da lista recursivamente:");
//        lista.printRecursively();
//        System.out.println();
//        System.out.println("------------------------ORDENADO---------------------------------------");
//
//        OrderedListADT<Integer> orderedList = new ArrayOrderedList<>();
//
//        System.out.println("Lista vazia?: " + orderedList.isEmpty());
//
//        orderedList.add(10);
//        orderedList.add(5);
//        orderedList.add(15);
//        orderedList.add(1);
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : orderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println();
//
//        System.out.println("Primeiro elemento: " + orderedList.first());
//        System.out.println("Último elemento: " + orderedList.last());
//        System.out.println("Lista vazia?: " + orderedList.isEmpty());
//        System.out.println();
//
//        //Adicionar + elmentos
//        orderedList.add(2);
//        orderedList.add(55);
//        orderedList.add(13);
//        orderedList.add(4);
//        orderedList.add(-5);
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : orderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println("Primeiro elemento: " + orderedList.first());
//        System.out.println("Último elemento: " + orderedList.last());
//        System.out.println();
//        System.out.println("Removendo o primeiro elemento: " + orderedList.removeFirst());
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : orderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println("Removendo o último elemento: " + orderedList.removeLast());
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : orderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println("Tamanho da lista: " + orderedList.size());
//        System.out.println();
//        System.out.println("Removendo o elemento 2: " + orderedList.remove(2));
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : orderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println("Contem elemento 2: " + orderedList.contains(2));
//        System.out.println("Contem elemento 2: " + orderedList.contains(5));
//
//        System.out.println("------------------------DESORDENADO---------------------------------------");
//        UnorderedListADT<Integer> unorderedList = new ArrayUnorderedList<>();
//
//        System.out.println("Lista vazia?: " + unorderedList.isEmpty());
//
//        unorderedList.addToFront(10);
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista desordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        unorderedList.addToFront(5);
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista desordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        unorderedList.addToRear(15);
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista desordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//
//        unorderedList.addAfter(2, 15);
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista desordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista desordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println("Primeiro elemento: " + unorderedList.first());
//        System.out.println("Último elemento: " + unorderedList.last());
//        System.out.println("Lista vazia?: " + unorderedList.isEmpty());
//        System.out.println();
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista deordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//
//        System.out.println();
//
//        System.out.println("Primeiro elemento: " + unorderedList.first());
//        System.out.println("Último elemento: " + unorderedList.last());
//        System.out.println();
//
//        System.out.println("Removendo o primeiro elemento: " + unorderedList.removeFirst());
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista ordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println("Removendo o último elemento: " + unorderedList.removeLast());
//
//        // Exibir elementos da lista
//        System.out.println("Elementos na lista desordenada:");
//        for (Integer elem : unorderedList) {
//            System.out.print(elem + " ");
//        }
//        System.out.println();
//        System.out.println("Tamanho da lista: " + unorderedList.size());
//        System.out.println("Removendo o elemento 2: " + unorderedList.remove(10));
//        System.out.println();
//        System.out.println("Contem elemento 10: " + unorderedList.contains(15));
//        System.out.println("Contem elemento 5: " + unorderedList.contains(5));
//
//        System.out.println("-----------------ITERADOR COM O WHILE----------------------");
//        Iterator<Integer> it = orderedList.iterator();
//        System.out.println("Elementos usando iterador:");
//
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
//        System.out.println();
//
//        // Exibe o tamanho da lista
//        System.out.println("Tamanho da lista: " + orderedList.size());
//        System.out.println();
//        System.out.println("Removendo o elemento 5: " + orderedList.remove(5));
//
//        // Exibir elementos novamente usando o iterador
//        it = orderedList.iterator();
//        System.out.println("Elementos restantes usando iterador:");
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
//        System.out.println();
//        QueueADT<Integer> queue = new LinkedQueue<>();
//
//        queue.enqueue(10);
//        queue.enqueue(20);
//        queue.enqueue(30);
//
//        System.out.println("Mostra elemento fim da Fila: " + queue.first() + "\n");
//        System.out.println("Mostra a fila:\n" + queue.toString());
//        System.out.println("Tamanho é: " + queue.size());
//        System.out.println();
//        queue.dequeue();
//
//        System.out.println("Mostra elemento fim da Fila: " + queue.first() + "\n");
//        System.out.println("Mostra fila:\n" + queue.toString());
//        System.out.println("Tamanho é: " + queue.size());
//
//        System.out.println("Is empty?: " + queue.isEmpty());
//
//        queue.dequeue();
//        System.out.println("Mostra fila:\n" + queue.toString());
//        queue.dequeue();
//        System.out.println("Is empty?: " + queue.isEmpty());

//        StackADT<Integer> stack = new ArrayStack<>();
//
//        stack.push(10);
//        stack.push(20);
//        stack.push(30);
//
//        System.out.println("Mostra elemento Topo Stack: " + stack.peek() + "\n");
//        System.out.println("Mostra Pilha:\n" + stack.toString());
//        System.out.println("Tamanho é: " + stack.size());
//
//        stack.pop();
//
//        System.out.println("Mostra elemento Topo Stack: " + stack.peek() + "\n");
//        System.out.println("Mostra Pilha:\n" + stack.toString());
//        System.out.println("Tamanho é: " + stack.size());
//
//        System.out.println("Is empty?: " + stack.isEmpty());
//
//        stack.pop();
//        stack.pop();
//        System.out.println("Is empty?: " + stack.isEmpty());
//
//        StackADT<Integer> stack = new LinkedStack<>();
//
//        stack.push(10);
//        stack.push(20);
//        stack.push(30);
//
//        System.out.println("Mostra elemento Topo Stack: " + stack.peek() + "\n");
//        System.out.println("Mostra Pilha:\n" + stack.toString());
//        System.out.println("Tamanho é: " + stack.size());
//        ((LinkedStack<Integer>) stack).printRecursively(); // Casting para chamar o método específico
//        System.out.println();
//        stack.pop();
//
//        System.out.println("Mostra elemento Topo Stack: " + stack.peek() + "\n");
//        System.out.println("Mostra Pilha:\n" + stack.toString());
//        System.out.println("Tamanho é: " + stack.size());
//
//        System.out.println("Is empty?: " + stack.isEmpty());
//
//        stack.pop();
//        stack.pop();
//        System.out.println("Is empty?: " + stack.isEmpty());

//        GraphADT<String> grafo = new Graph<>();
//
//        grafo.addVertex("A");
//        grafo.addVertex("B");
//        grafo.addVertex("C");
//        grafo.addVertex("D");
//        grafo.addVertex("E");
//        grafo.addVertex("F");
//
//        grafo.addEdge("A", "B");
//        grafo.addEdge("A", "C");
//        grafo.addEdge("B", "D");
//        grafo.addEdge("C", "D");
//        grafo.addEdge("C", "E");
//        grafo.addEdge("A", "D");
//        grafo.addEdge("E", "F");
//
//        System.out.println("Grafo: " + grafo.toString());
//
//        System.out.println();
//         grafo.removeVertex("C");
//        System.out.println("Grafo: " + grafo.toString());
//        System.out.println("O grafo está conectado? " + grafo.isConnected());
//
//        // Realizando travessia em largura (BFS) a partir do vértice "A"
//        System.out.println("BFS (Travessia em largura) a partir de A:");
//        Iterator<String> bfsIterator = grafo.iteratorBFS("A");
//        while (bfsIterator.hasNext()) {
//            System.out.print(bfsIterator.next() + " ");
//        }
//        System.out.println();
//
//        // Realizando travessia em profundidade (DFS) a partir do vértice "A"
//        System.out.println("DFS (Travessia em profundidade) a partir de A:");
//        Iterator<String> dfsIterator = grafo.iteratorDFS("C");
//        while (dfsIterator.hasNext()) {
//            System.out.print(dfsIterator.next() + " ");
//        }
//        System.out.println();
//
//        System.out.println("Caminho mais curto A até E: ");
//        Iterator<String> shortestPathIterator = grafo.iteratorShortestPath("A", "E");
//        while (shortestPathIterator.hasNext()) {
//            System.out.print(shortestPathIterator.next());
//            if (shortestPathIterator.hasNext()) {
//                System.out.print(" -> ");
//            }
//        }
//        System.out.println();

//        Network<String> network = new Network<>();
//
//        // Adicionando vértices
//        network.addVertex("A");
//        network.addVertex("B");
//        network.addVertex("C");
//        network.addVertex("D");
//        network.addVertex("E");
//
//        // Adicionando arestas com pesos
//        network.addEdge("A", "B", 4.0);
//        network.addEdge("A", "C", 2.0);
//        network.addEdge("B", "C", 5.0);
//        network.addEdge("B", "D", 10.0);
//        network.addEdge("C", "D", 3.0);
//        network.addEdge("C", "E", 8.0);
//        network.addEdge("D", "E", 7.0);
//
//        // Exibindo o grafo com a matriz de adjacências e pesos das arestas
//        System.out.println("Matriz de Adjacências (Grafo Pesado):");
//        System.out.println(network);
//
//        // Testando o cálculo do caminho mais curto
//        System.out.println("\nPeso do caminho mais curto entre A e D:");
//        double shortestPath = network.shortestPathWeight("A", "D");
//        System.out.println("Peso do caminho mais curto: " + shortestPath);
//
//        // Testando o cálculo do caminho mais curto entre A e E
//        System.out.println("\nPeso do caminho mais curto entre A e E:");
//        shortestPath = network.shortestPathWeight("A", "E");
//        System.out.println("Peso do caminho mais curto: " + shortestPath);
//
//        // Testando a árvore geradora mínima (MST)
//        System.out.println("\nÁrvore Geradora Mínima (MST) do Grafo:");
//        Network<String> mst = network.mstNetwork();
//        System.out.println(mst);  // Imprime a MST (Mínimum Spanning Tree)
    }
}
