import java.awt.*;
import java.awt.event.*;

class Console extends Frame implements KeyListener {

    static int WINDOW_WIDTH = 500;
    static int WINDOW_HEIGHT = 500;

    TextArea area = null;

    Game game;

    Console() {
        super("Console Test");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new BorderLayout());

        area = new TextArea("Try typing\n", WINDOW_WIDTH, WINDOW_HEIGHT, TextArea.SCROLLBARS_NONE);
        area.setFont(new Font("Monospaced", Font.PLAIN, 24));
        area.setEditable(false);
        area.setFocusable(false);

        add(area, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.addKeyListener(this);

        setVisible(true);
        requestFocus();

        game = new Game();
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (e.isControlDown() && keyCode == KeyEvent.VK_C) {
            System.exit(0);
        } else if (keyCode == KeyEvent.VK_UP) {
            area.append("UP\n");
            Block rotatedBlock = new Block(game.getCurrentBlock());
            rotatedBlock.rotate();
            if (game.checkValidMove(rotatedBlock, 0, 0)) {
                game.getCurrentBlock().rotate();
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            area.append("LEFT\n");
            if (game.checkValidMove(-1, 0)) {
                game.getCurrentBlock().moveLeft();
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            area.append("DOWN\n");
            if (game.checkValidMove(0, 1)) {
                game.getCurrentBlock().moveDown();
            } else {
                game.updateBlockToBoard();
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            area.append("RIGHT\n");
            if (game.checkValidMove(1, 0)) {
                game.getCurrentBlock().moveRight();
            }
        } else {
            area.append(KeyEvent.getKeyText(keyCode));
            area.append("\n");
        }

        game.renderBoard();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        Console console = new Console();
        console.game.renderBoard();
    }
}
