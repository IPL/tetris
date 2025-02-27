import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;

public class GamePanel extends Panel {
    private static final int BLOCK_SIZE = 30; // 每个方块的大小
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(game.getBoard()[0].length * BLOCK_SIZE, game.getBoard().length * BLOCK_SIZE));
        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        short[][] board = game.getBoard();
        Block currentBlock = game.getCurrentBlock();

        // 绘制游戏板
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // 绘制当前方块
        if (currentBlock != null) {
            g.setColor(Color.RED);
            short[][] shape = currentBlock.shape;
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] == 1) {
                        int x = (currentBlock.x + j) * BLOCK_SIZE;
                        int y = (currentBlock.y + i) * BLOCK_SIZE;
                        g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }
    }
}