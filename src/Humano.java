import java.util.Scanner;

public class Humano extends Player{
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void atacar(Player inimigo) {
        boolean atacou = false;

        while (!atacou) {
            System.out.println("Em qual posição você deseja atacar " + inimigo.getNome() + "?");

            int x = scanner.nextInt();
            int y = scanner.nextInt();

            Posicao pos = new Posicao(x, y);

            int statusPosicao = inimigo.getTabuleiro().posicaoEValida(pos);

            if (statusPosicao == 0) {
                System.out.println("A posição informada não está dentro do tabuleiro");
                continue;
            }

            if (statusPosicao > 5) {
                System.out.println("A posição informada já foi bombardeada");
                continue;
            }

            inimigo.receberAtaque(pos);
            atacou = true;
        }
    }

    @Override
    public void colocarBarcos() {
        int total = getTabuleiro().getTotalBarcos();

        for (int i=0; i<total; i++) {
            boolean colocou = false;

            while (!colocou) {
                System.out.println(getNome() + ", em qual posição você deseja colocar um barco?");

                int x = scanner.nextInt();
                int y = scanner.nextInt();

                Posicao pos = new Posicao(x, y);

                int statusPosicao = getTabuleiro().posicaoEValida(pos);

                if (statusPosicao == 0) {
                    System.out.println("A posição informada não está dentro do tabuleiro");
                    continue;
                }

                if (statusPosicao == 5 || statusPosicao == 7) {
                    System.out.println("Já tem um barco nessa posição");
                    continue;
                }

                getTabuleiro().colocarBarco(pos);
                colocou = true;
            }
        }
    }

    public Humano(String nome, int tamanhoTabuleiro, int totalBarcos) {
        super(nome, tamanhoTabuleiro, totalBarcos);
    }
}
