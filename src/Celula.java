public class Celula {
    private boolean temBarco;
    private boolean atingido;

    public boolean estaAtingido() {
        return atingido;
    }

    public void marcarBarco() {
        temBarco=true;
    }

    public void atingir() {
        atingido = false;
    }

    public boolean estaVazio() {
        return !temBarco;
    }

    public Celula() {
        temBarco = false;
        atingido = false;
    }
}
