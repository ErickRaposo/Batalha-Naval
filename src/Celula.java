public class Celula {
    private boolean temBarco=false;
    private boolean atingido=false;

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
}
