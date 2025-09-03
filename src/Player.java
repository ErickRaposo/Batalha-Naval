abstract class Player {
    private String nome;
    private Tabuleiro tabuleiro;

    public String getNome() {
        return nome;
    }

    public abstract void atacar(Player inimigo);

    public boolean perdeu() {
        return tabuleiro.osBarcosEstaoAfundados();
    }

    public boolean receberAtaque(Posicao pos) {
        return tabuleiro.receberAtaque(pos);
    }

    public abstract void colocarBarcos(int total);

    public Player(String nome, int tamanhoTabuleiro, int totalBarcos) {
        this.nome = nome;
        this.tabuleiro = new Tabuleiro(tamanhoTabuleiro, totalBarcos);
    }
}
