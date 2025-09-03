import java.util.Random;

public class Computador extends Player {
    @Override
    public void atacar(Player inimigo) {
        Random random = new Random();

        int x = random.nextInt(inimigo.getTabuleiro().getTamanho());
        int y = random.nextInt(inimigo.getTabuleiro().getTamanho());

        Posicao pos = new Posicao(x, y);

        System.out.println("O computador está atacando!");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        inimigo.receberAtaque(pos);
    }

    @Override
    public void colocarBarcos() {
        System.out.println("O computador está posicionando os barcos!");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        getTabuleiro().colocarBarcosAleatorios();
    }

    public Computador(String nome, int tamanhoTabuleiro, int totalBarcos) {
        super(nome, tamanhoTabuleiro, totalBarcos);
    }
}
