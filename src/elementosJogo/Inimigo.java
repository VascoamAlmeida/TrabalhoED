package elementosJogo;

/**
 * Class inimigo que tem seu nome e room
 */
public class Inimigo extends Personagens {

    private String room;

    /**
     * Metodo construtor inimigo com nome, poder e room
     *
     * @param name  do inimigo
     * @param poder do inimigo
     * @param room  encontra o inimigo
     */
    public Inimigo(String name, int poder, String room) {
        super(name, poder);
        this.room = room;
    }

    /**
     * Metodo obter o room do inimigo
     *
     * @return room do inimigo
     */
    public String getRoom() {
        return room;
    }

    /**
     * Metodo obter o nome do inimigo
     *
     * @return nome do inimigo
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Metodo para adicionar room
     *
     * @param room adicionado
     */
    public void setRoom(String room) {
        this.room = room;
    }


    /**
     * Metodo string apresentar o inimigo
     *
     * @return string com info do inimigo
     */
    @Override
    public String toString() {
        return "Inimigo{" +
                "room='" + room + '\'' +
                super.toString() +
                '}';
    }
}
