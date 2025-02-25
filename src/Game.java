import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static final int BOARD_WIDTH = 30;
    private static final int BOARD_HEIGHT = 20;

    private static final int DROP_DELAY = 1000; // 1 second
    private static final int DROP_INTERVAL = 2000; // 2 seconds

    private final short[][] board;
    private Block currentBlock;

    private final Random random;

    Game(){
        board = new short[BOARD_HEIGHT][BOARD_WIDTH];
        random = new Random();
        generateNewBlock();
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    private void generateNewBlock() {
        short[][][] shapes = {
                {{1, 1, 1, 1}},
                {{1, 1}, {1, 1}},
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

    public void renderBoard() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                boolean isBlock = checkWhetherOccupied(i, j);
                if (isBlock) {
                    System.out.print("*");
                } else if (board[i][j] == 1) {
                    System.out.print("*");
                } else {
                    if (i == 0
                            || j == 0
                            || i == BOARD_HEIGHT - 1
                            || j == BOARD_WIDTH -1) {
                        System.out.print("=");
                    } else {
                        System.out.print("-");
                    }
                }
            }
            System.out.println();
        }
    }

    private boolean checkWhetherOccupied(int i, int j) {
        int rows = currentBlock.shape.length;
        int cols = currentBlock.shape[0].length;

        for (int k = 0; k < rows; k++) {
            for (int l = 0; l < cols; l++) {
                if (currentBlock.shape[k][l] == 1
                        && currentBlock.x + l == j
                        && currentBlock.y + k == i) {
                    return true;
                }
            }
        }
        return false;
    }
}
