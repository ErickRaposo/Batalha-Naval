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

    public void start() {
        System.out.println("=== BEM-VINDO À BATALHA NAVAL ===");
        configurarPartida();
        iniciarPosicionamento();
        iniciarBatalha();
    }
    
    private void configurarPartida() {
        int tamanhoTabuleiro = perguntarTamanhoTabuleiro();
        int quantidadeBarcos = perguntarQuantidadeBarcos(tamanhoTabuleiro);
        int modoDeJogo = perguntarModoDeJogo();

        if (modoDeJogo == 1) {
            System.out.print("Digite o seu nome: ");
            String nomeHumano = scanner.next();
            jogadores[0] = new Humano(nomeHumano, tamanhoTabuleiro, quantidadeBarcos);
            jogadores[1] = new Computador("Computador", tamanhoTabuleiro, quantidadeBarcos);
        } else {
            System.out.print("Digite o nome do Jogador 1: ");
            String nomeJogador1 = scanner.next();
            jogadores[0] = new Humano(nomeJogador1, tamanhoTabuleiro, quantidadeBarcos);

            System.out.print("Digite o nome do Jogador 2: ");
            String nomeJogador2 = scanner.next();
            jogadores[1] = new Humano(nomeJogador2, tamanhoTabuleiro, quantidadeBarcos);
        }
    }

    private void iniciarPosicionamento() {
        System.out.println("\n--- FASE DE POSICIONAMENTO ---");
        for (int i = 0; i < jogadores.length; i++) {
            Player jogadorAtual = jogadores[i];
            if (jogadorAtual instanceof Humano) {
                System.out.println("\n" + jogadorAtual.getNome() + ", é a sua vez de posicionar os barcos.");
            }
            
            if (jogadores[i] instanceof Humano && i==1) {
                System.out.println(jogadores[(i + 1) % 2].getNome() + ", por favor, não olhe a tela!");
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
            }
            
            jogadorAtual.colocarBarcos();
            
            if (jogadores[(i + 1) % 2] instanceof Humano && i == 0) {
                System.out.println("\nBarcos posicionados! Pressione ENTER para limpar a tela e passar para o próximo jogador.");
                scanner.nextLine();
                scanner.nextLine();
                for (int j = 0; j < 50; j++) System.out.println();
            }
        }
    }

    private void iniciarBatalha() {
        System.out.println("\n--- O JOGO COMEÇOU! ---");
        Player vencedor = null;
        while (vencedor == null) {
            Player atacante = jogadores[turno];
            Player inimigo = jogadores[(turno + 1) % 2];
            boolean isModoPvP = jogadores[0] instanceof Humano && jogadores[1] instanceof Humano;

            System.out.println("\n-----------------------------------------");
            System.out.println("Turno de: " + atacante.getNome());

            if (atacante instanceof Humano) {
                boolean revelarBarcos = !isModoPvP;

                System.out.println("Tabuleiro do inimigo (" + inimigo.getNome() + "):");
                inimigo.getTabuleiro().printarTabuleiro(false);
                inimigo.getTabuleiro().printarLegenda(revelarBarcos);
            }

            boolean acertou = atacante.atacar(inimigo);

            inimigo.getTabuleiro().printarTabuleiro(false);

            if (acertou) {
                System.out.println("Você acertou um barco de " + inimigo.getNome() + "! Agora resta " + inimigo.getTabuleiro().getBarcosRestantes() + " barcos.");
            } else {
                System.out.println("Não acertou nenhum barco! Ainda restam " + inimigo.getTabuleiro().getBarcosRestantes() + " barcos de " + inimigo.getNome() + ".");
            }

            try { Thread.sleep(3000); } catch (InterruptedException e) {}

            vencedor = checarVencedor();
            trocarTurno();
        }
        anunciarVencedor(vencedor);
    }
    
    private int perguntarTamanhoTabuleiro() {
        int tamanho = 0;
        while (tamanho <= 1) {
            try {
                System.out.print("Digite o tamanho do tabuleiro (ex: 10 para 10x10): ");
                tamanho = scanner.nextInt();
                if (tamanho <= 1) System.out.println("O tamanho deve ser maior que 1.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
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
                scanner.next();
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
                scanner.next();
            }
        }
        return modo;
    }
    
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