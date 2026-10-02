package elementosJogo;

/**
 * Class alvo que vai ter seu tipo e onde se encontra
 */
public class Alvo implements IAlvo {
    private String room;
    private String tipo;

    /**
     * Metodo construtor de alvo com tipo e onde se encontra
     *
     * @param room local do alvo
     * @param tipo do alvo
     */
    public Alvo(String room, String tipo) {
        this.room = room;
        this.tipo = tipo;
    }

    /**
     * Metodo para obter room do alvo
     *
     * @return room do alvo
     */
    @Override
    public String getRoom() {
        return room;
    }

    /**
     * Metodo permite inserir o room do alvo
     *
     * @param room que esta o alvo
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Metodo permite obter o tipo do alvo
     *
     * @return tipo do alvo
     */
    @Override
    public String getTipo() {
        return tipo;
    }

    /**
     * Metodo permite dar tipo ao alvo
     *
     * @param tipo do alvo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo string apresentar o alvo
     *
     * @return string com info do alvo
     */
    @Override
    public String toString() {
        return "Alvo{" +
                "room='" + room + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
