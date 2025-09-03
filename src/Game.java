import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private Player[] jogadores;
    private int turno;
    private final Scanner scanner;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.jogadores = new Player[2];
        this.turno = 0;
    }

    /**
     * Método principal que inicia e controla o fluxo do jogo.
     */
    public void start() {
        System.out.println("=== BEM-VINDO À BATALHA NAVAL ===");
        
        // 1. Configurar a partida com base nas escolhas do usuário
        configurarPartida();

        // 2. Iniciar a fase de posicionamento dos barcos
        iniciarPosicionamento();

        // 3. Iniciar a batalha (loop de turnos)
        iniciarBatalha();
    }
    
    /**
     * Coleta as informações iniciais: tamanho do tabuleiro, número de barcos e modo de jogo.
     */
    private void configurarPartida() {
        int tamanhoTabuleiro = perguntarTamanhoTabuleiro();
        int quantidadeBarcos = perguntarQuantidadeBarcos(tamanhoTabuleiro);
        int modoDeJogo = perguntarModoDeJogo();

        if (modoDeJogo == 1) { // Jogador vs Computador
            System.out.print("Digite o seu nome: ");
            String nomeHumano = scanner.next();
            jogadores[0] = new Humano(nomeHumano, tamanhoTabuleiro, quantidadeBarcos);
            jogadores[1] = new Computador("Computador", tamanhoTabuleiro, quantidadeBarcos);
        } else { // Jogador vs Jogador
            System.out.print("Digite o nome do Jogador 1: ");
            String nomeJogador1 = scanner.next();
            jogadores[0] = new Humano(nomeJogador1, tamanhoTabuleiro, quantidadeBarcos);

            System.out.print("Digite o nome do Jogador 2: ");
            String nomeJogador2 = scanner.next();
            jogadores[1] = new Humano(nomeJogador2, tamanhoTabuleiro, quantidadeBarcos);
        }
    }

    /**
     * Gerencia a fase em que os jogadores colocam seus barcos no tabuleiro.
     */
    private void iniciarPosicionamento() {
        System.out.println("\n--- FASE DE POSICIONAMENTO ---");
        for (int i = 0; i < jogadores.length; i++) {
            Player jogadorAtual = jogadores[i];
            System.out.println("\n" + jogadorAtual.getNome() + ", é a sua vez de posicionar os barcos.");
            
            // Se houver outro jogador humano esperando, peça para ele não olhar.
            if (jogadores[(i + 1) % 2] instanceof Humano) {
                System.out.println(jogadores[(i + 1) % 2].getNome() + ", por favor, não olhe a tela!");
                // Pausa para dar tempo do outro jogador se virar
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
            }
            
            jogadorAtual.colocarBarcos();
            
            // Limpa a tela (simulado com linhas em branco) para o próximo jogador não ver o tabuleiro.
            if (jogadores[(i + 1) % 2] instanceof Humano && i == 0) { // Apenas após o J1
                System.out.println("\nBarcos posicionados! Pressione ENTER para limpar a tela e passar para o próximo jogador.");
                scanner.nextLine(); // Consome o \n anterior
                scanner.nextLine(); // Espera o Enter
                for (int j = 0; j < 50; j++) System.out.println();
            }
        }
    }

    /**
     * Inicia o loop principal de ataque até que um vencedor seja encontrado.
     */
    private void iniciarBatalha() {
        System.out.println("\n--- O JOGO COMEÇOU! ---");
        Player vencedor = null;
        while (vencedor == null) {
            Player atacante = jogadores[turno];
            Player inimigo = jogadores[(turno + 1) % 2];

            System.out.println("\n-----------------------------------------");
            System.out.println("Turno de: " + atacante.getNome());
            
            // Mostra o tabuleiro do inimigo (sem revelar barcos)
            System.out.println("Tabuleiro do inimigo (" + inimigo.getNome() + "):");
            inimigo.getTabuleiro().printarTabuleiro(false);

            // Mostra o seu próprio tabuleiro (revelando seus barcos)
            System.out.println("Seu tabuleiro (" + atacante.getNome() + "):");
            atacante.getTabuleiro().printarTabuleiro(true);

            atacante.atacar(inimigo);
            vencedor = checarVencedor();
            trocarTurno();
        }
        anunciarVencedor(vencedor);
    }
    
    // --- MÉTODOS AUXILIARES DE CONFIGURAÇÃO ---

    private int perguntarTamanhoTabuleiro() {
        int tamanho = 0;
        while (tamanho <= 1) {
            try {
                System.out.print("Digite o tamanho do tabuleiro (ex: 10 para 10x10): ");
                tamanho = scanner.nextInt();
                if (tamanho <= 1) System.out.println("O tamanho deve ser maior que 1.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // Limpa o buffer
            }
        }
        return tamanho;
    }

    private int perguntarQuantidadeBarcos(int tamanhoTabuleiro) {
        int maxBarcos = tamanhoTabuleiro * tamanhoTabuleiro;
        int qtd = 0;
        while (qtd <= 0 || qtd > maxBarcos) {
            try {
                System.out.print("Digite a quantidade de barcos: ");
                qtd = scanner.nextInt();
                if (qtd <= 0 || qtd > maxBarcos) {
                    System.out.println("Número de barcos inválido. Deve ser entre 1 e " + maxBarcos + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // Limpa o buffer
            }
        }
        return qtd;
    }

    private int perguntarModoDeJogo() {
        int modo = 0;
        while (modo != 1 && modo != 2) {
            try {
                System.out.println("Escolha o modo de jogo:");
                System.out.println("1 - Jogador vs Computador");
                System.out.println("2 - Jogador vs Jogador");
                System.out.print("Sua escolha: ");
                modo = scanner.nextInt();
                if (modo != 1 && modo != 2) {
                    System.out.println("Opção inválida. Escolha 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite 1 ou 2.");
                scanner.next(); // Limpa o buffer
            }
        }
        return modo;
    }
    
    // --- MÉTODOS DE CONTROLE DE JOGO ---
    
    private void trocarTurno() {
        this.turno = (this.turno + 1) % 2;
    }

    private Player checarVencedor() {
        if (jogadores[0].perdeu()) return jogadores[1];
        if (jogadores[1].perdeu()) return jogadores[0];
        return null;
    }
    
    private void anunciarVencedor(Player vencedor) {
        System.out.println("\n=========================================");
        System.out.println("FIM DE JOGO! O VENCEDOR É: " + vencedor.getNome());
        System.out.println("=========================================");
    }
}