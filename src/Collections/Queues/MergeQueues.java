package Collections.Queues;

/**
 * Class de merge de queues
 *
 */
public class MergeQueues {

    /**
     * Metodo que junta 2 queues
     *
     * @param queue1 para juntar
     * @param queue2 para juntar
     * @return queues juntas
     * @param <T> generico
     */
    public static <T extends Comparable<T>> CircularArrayQueue<T> mergeQueues(QueueADT<T> queue1, QueueADT<T> queue2) {
        CircularArrayQueue<T> mergedQueue = new CircularArrayQueue<>();

        // Mescla as duas filas em ordem
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            if (queue1.first().compareTo(queue2.first()) <= 0) {
                mergedQueue.enqueue(queue1.dequeue());
            } else {
                mergedQueue.enqueue(queue2.dequeue());
            }
        }

        // Adiciona os elementos restantes da fila 1, se houver
        while (!queue1.isEmpty()) {
            mergedQueue.enqueue(queue1.dequeue());
        }

        // Adiciona os elementos restantes da fila 2, se houver
        while (!queue2.isEmpty()) {
            mergedQueue.enqueue(queue2.dequeue());
        }

        return mergedQueue;
    }
}
