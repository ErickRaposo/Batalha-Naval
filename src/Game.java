import java.util.Scanner;

public class Game {
    private Player[] jogadores;
    private int turno; 
    private int quantidadeBarcos;
    private static final Scanner scanner = new Scanner(System.in);
    private int tamanhoTabuleiro;

    // Construtor para inicializar o jogo
    public Game(int tamanhoTabuleiro, int quantidadeBarcos) {
        this.quantidadeBarcos = quantidadeBarcos;
        this.jogadores = new Player[2];
        this.turno = 0;
        this.tamanhoTabuleiro = tamanhoTabuleiro;
    }

    public void start() {
        System.out.println("=== BEM-VINDO À BATALHA NAVAL ===");

        // 1. Setup do Jogo: Criar jogadores
        System.out.print("Digite o seu nome: ");
        String nomeHumano = scanner.nextLine();
        jogadores[0] = new Humano(nomeHumano, tamanhoTabuleiro, quantidadeBarcos);
        jogadores[1] = new Computador("Computador", tamanhoTabuleiro, quantidadeBarcos);

        // 2. Posicionamento dos Barcos
        System.out.println("\n--- FASE DE POSICIONAMENTO ---");
        jogadores[0].colocarBarcos();
        jogadores[1].colocarBarcos();

        System.out.println("\n--- O JOGO COMEÇOU! ---");

        // 3. Loop Principal do Jogo
        Player vencedor = null;
        while (vencedor == null) {
            Player atacante = jogadores[turno];
            Player inimigo = jogadores[(turno + 1) % 2]; // O outro jogador

            System.out.println("\n-----------------------------------------");
            System.out.println("Turno de: " + atacante.getNome());

            // Mostra o tabuleiro do inimigo (sem revelar barcos)
            System.out.println("Tabuleiro do inimigo (" + inimigo.getNome() + "):");
            inimigo.getTabuleiro().printarTabuleiro(false);

            // Mostra o seu próprio tabuleiro (revelando seus barcos)
            System.out.println("Seu tabuleiro (" + atacante.getNome() + "):");
            atacante.getTabuleiro().printarTabuleiro(false);

            // Ação de atacar
            atacante.atacar(inimigo);

            // Verifica se há um vencedor
            vencedor = checarVencedor();

            // Troca o turno
            trocarTurno();
        }

        // 4. Fim de Jogo
        System.out.println("\n=========================================");
        System.out.println("FIM DE JOGO! O VENCEDOR É: " + vencedor.getNome());
        System.out.println("=========================================");
    }

    /**
     * Alterna o turno entre os jogadores (de 0 para 1, e de 1 para 0).
     */
    public void trocarTurno() {
        this.turno = (this.turno + 1) % 2; // O operador % garante que o valor alterne entre 0 e 1
    }

    /**
     * Verifica se algum jogador perdeu.
     * @return O jogador vencedor, ou null se o jogo ainda não acabou.
     */
    public Player checarVencedor() {
        if (jogadores[0].perdeu()) {
            return jogadores[1]; // Se jogador 0 perdeu, jogador 1 é o vencedor
        }
        if (jogadores[1].perdeu()) {
            return jogadores[0]; // Se jogador 1 perdeu, jogador 0 é o vencedor
        }
        return null; // Ninguém perdeu ainda
    }
}