import java.util.Random;



public class Tabuleiro {
    private int tamanho;
    private Celula[][] grid;
    private int totalBarcos;
    private int barcosRestantes;

    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";

    public void colocarBarco(Posicao pos) {
        grid[pos.getX()][pos.getY()].marcarBarco();
    }

    public void colocarBarcosAleatorios() {
        Random random = new Random();

        for (int i=0; i<totalBarcos; i++) {
            int x = random.nextInt(tamanho);
            int y = random.nextInt(tamanho);

            if(grid[x][y].estaVazio()) {
                grid[x][y].marcarBarco();
                continue;
            }

            i--;
        }
    }

    public boolean receberAtaque(Posicao pos) {
        grid[pos.getX()][pos.getY()].atingir();
        return !grid[pos.getX()][pos.getY()].estaVazio();
    }

    public boolean osBarcosEstaoAfundados() {
        for (int i=0; i<tamanho ; i++) {
            for (int j=0 ; j<tamanho ; j++) {
                if(!grid[i][j].estaVazio() && !grid[i][j].estaAtingido()) {
                    return false;
                }
            }
        }

        return true;
    }

    public int posicaoEValida(Posicao pos) {
        // 0 - posição não está dentro do tabuleiro
        // 1 - dentro do tabuleiro
        // 3 - dentro do tabuleiro e com barco
        // 5 - dentro do tabuleiro e atingido
        // 7 - dentro do tabuleiro, com barco e atingido

        int soma=0;

        if (pos.getX() >= 0 && pos.getX() < tamanho && pos.getY() >= 0 && pos.getY() < tamanho) {
            soma+=1;
        } else {
            return 0;
        }

        if (!grid[pos.getX()][pos.getY()].estaVazio()) soma+=2;
        if (grid[pos.getX()][pos.getY()].estaAtingido()) soma+=4;

        return soma;
    }

    public Tabuleiro(int tamanho, int totalBarcos) {
        this.tamanho = tamanho;
        this.totalBarcos = totalBarcos;
        this.barcosRestantes = totalBarcos;
        this.grid = new Celula[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                grid[i][j] = new Celula();
            }
        }
    }

    public void printarTabuleiro(boolean revelarBarcos) {
        System.out.print("   ");
        for (int j = 1; j <= tamanho; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();

        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d ", (i+1));

            for (int j = 0; j < tamanho; j++) {
                Celula celula = grid[i][j];

                if (celula.estaAtingido()) {
                    if (celula.estaVazio()) {
                        System.out.print(CYAN + " O " + RESET);
                    } else {
                        System.out.print(RED + " X " + RESET);
                    }
                } else {
                    if (celula.estaVazio()) {
                        System.out.print(BLUE + " ~ " + RESET);
                    } else if(revelarBarcos) {
                        System.out.print(YELLOW + " B " + RESET);
                    } else {
                        System.out.print(BLUE + " ~ " + RESET);
                    }
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Legenda:");
        System.out.println(BLUE + "~" + RESET + " - Água");
        System.out.println(CYAN + "O" + RESET + " - Tiro na água");
        System.out.println(RED + "X" + RESET + " - Barco atingido");
        if (revelarBarcos) {
            System.out.println(YELLOW + "B" + RESET + " - Barco");
        }
    }
}