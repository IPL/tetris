import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Console extends Frame implements KeyListener {
    private Game game;
    private GamePanel gamePanel;

    Console() {
        super("Tetris Game");
        setSize(400, 600); // 设置窗口大小
        setLayout(new BorderLayout());

        game = new Game();
        gamePanel = new GamePanel(game);
        add(gamePanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        addKeyListener(this);

        setVisible(true);
        requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            Block rotatedBlock = new Block(game.getCurrentBlock());
            rotatedBlock.rotate();
            if (game.checkValidMove(rotatedBlock, 0, 0)) {
                game.getCurrentBlock().rotate();
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (game.checkValidMove(-1, 0)) {
                game.getCurrentBlock().moveLeft();
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (game.checkValidMove(0, 1)) {
                game.getCurrentBlock().moveDown();
            } else {
                game.updateBlockToBoard();
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            if (game.checkValidMove(1, 0)) {
                game.getCurrentBlock().moveRight();
            }
        }

        gamePanel.repaint(); // 重绘游戏板
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Console();
    }
}