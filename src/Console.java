import java.awt.GridLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Console extends Frame implements KeyListener {
    private Game game1; // 玩家1的游戏状态
    private Game game2; // 玩家2的游戏状态
    private GamePanel gamePanel1; // 玩家1的游戏面板
    private GamePanel gamePanel2; // 玩家2的游戏面板

    Console() {
        super("Tetris Dual Player");
        setSize(800, 600); // 设置窗口大小
        setLayout(new GridLayout(1, 2)); // 左右排列两个游戏面板

        // 初始化游戏状态和面板
        game1 = new Game();
        game2 = new Game();
        gamePanel1 = new GamePanel(game1);
        gamePanel2 = new GamePanel(game2);
        game1.setGamePanel(gamePanel1); // 设置 GamePanel
        game2.setGamePanel(gamePanel2); // 设置 GamePanel

        // 将两个游戏面板添加到窗口中
        add(gamePanel1);
        add(gamePanel2);

        // 窗口关闭事件
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // 绑定键盘事件
        addKeyListener(this);
        setVisible(true);
        requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // 玩家1控制（ASDW）
        if (keyCode == KeyEvent.VK_W) { // 旋转
            Block rotatedBlock = new Block(game1.getCurrentBlock());
            rotatedBlock.rotate();
            if (game1.checkValidMove(rotatedBlock, 0, 0)) {
                game1.getCurrentBlock().rotate();
            }
        } else if (keyCode == KeyEvent.VK_A) { // 左移
            if (game1.checkValidMove(-1, 0)) {
                game1.getCurrentBlock().moveLeft();
            }
        } else if (keyCode == KeyEvent.VK_S) { // 下移
            if (game1.checkValidMove(0, 1)) {
                game1.getCurrentBlock().moveDown();
            } else {
                game1.updateBlockToBoard();
            }
        } else if (keyCode == KeyEvent.VK_D) { // 右移
            if (game1.checkValidMove(1, 0)) {
                game1.getCurrentBlock().moveRight();
            }
        }

        // 玩家2控制（JKLI）
        if (keyCode == KeyEvent.VK_I) { // 旋转
            Block rotatedBlock = new Block(game2.getCurrentBlock());
            rotatedBlock.rotate();
            if (game2.checkValidMove(rotatedBlock, 0, 0)) {
                game2.getCurrentBlock().rotate();
            }
        } else if (keyCode == KeyEvent.VK_J) { // 左移
            if (game2.checkValidMove(-1, 0)) {
                game2.getCurrentBlock().moveLeft();
            }
        } else if (keyCode == KeyEvent.VK_K) { // 下移
            if (game2.checkValidMove(0, 1)) {
                game2.getCurrentBlock().moveDown();
            } else {
                game2.updateBlockToBoard();
            }
        } else if (keyCode == KeyEvent.VK_L) { // 右移
            if (game2.checkValidMove(1, 0)) {
                game2.getCurrentBlock().moveRight();
            }
        }

        // 刷新两个游戏面板
        gamePanel1.repaint();
        gamePanel2.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Console();
    }
}