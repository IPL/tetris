import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static final int BOARD_WIDTH = 10; // 缩小宽度以适应窗口
    private static final int BOARD_HEIGHT = 20;

    private static final int DROP_DELAY = 1000; // 初始延迟 1 秒
    private static final int DROP_INTERVAL = 1000; // 下落间隔 1 秒

    private final short[][] board;
    private Block currentBlock;

    private final Random random;
    private final Timer timer;

    Game() {
        board = new short[BOARD_HEIGHT][BOARD_WIDTH];
        random = new Random();
        timer = new Timer();
        generateNewBlock();
        startAutoDrop();
    }

    /**
     * 启动自动下落定时器
     */
    private void startAutoDrop() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (checkValidMove(0, 1)) {
                    currentBlock.moveDown();
                } else {
                    updateBlockToBoard();
                }
            }
        }, DROP_DELAY, DROP_INTERVAL);
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public short[][] getBoard() {
        return board;
    }

    private void generateNewBlock() {
        short[][][] shapes = {
                {{1, 1, 1, 1}}, // I
                {{1, 1}, {1, 1}}, // O
                {{0, 1, 0}, {1, 1, 1}}, // T
                {{0, 1, 1}, {1, 1, 0}}, // S
                {{1, 1, 0}, {0, 1, 1}}, // Z
                {{1, 0, 0}, {1, 1, 1}}, // J
                {{0, 0, 1}, {1, 1, 1}}  // L
        };
        int index = random.nextInt(shapes.length);
        currentBlock = new Block(shapes[index], BOARD_WIDTH);
    }

    public boolean checkValidMove(Block block, int deltaX, int deltaY) {
        int rows = block.shape.length;
        int cols = block.shape[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (block.shape[i][j] == 1) {
                    int x = block.x + j + deltaX;
                    int y = block.y + i + deltaY;
                    if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT || board[y][x] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkValidMove(int deltaX, int deltaY) {
        return checkValidMove(currentBlock, deltaX, deltaY);
    }

    public void updateBlockToBoard() {
        int rows = currentBlock.shape.length;
        int cols = currentBlock.shape[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (currentBlock.shape[i][j] == 1) {
                    board[currentBlock.y + i][currentBlock.x + j] = 1;
                }
            }
        }
        checkAndClearRow();
        generateNewBlock();
        if (!checkValidMove(0, 0)) {
            System.out.println("Game Over!");
            System.exit(0);
        }
    }

    private void checkAndClearRow() {
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean isAllBlock = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == 0) {
                    isAllBlock = false;
                    break;
                }
            }
            if (isAllBlock) {
                for (int k = i; k > 0; k--) {
                    copyRow2Row(k - 1, k);
                }
                i++;
            }
        }
    }

    private void copyRow2Row(int i, int k) {
        for (int j = 0; j < BOARD_WIDTH; j++) {
            board[k][j] = board[i][j];
        }
    }
}