public class Block {
    short[][] shape;
    int x, y;

    public Block(Block block) {
        this.shape = block.shape;
        this.x = block.x;
        this.y = block.y;
    }

    public Block(short[][] shape, int width) {
        this.shape = shape;
        // make the new block appear at the middle screen
        this.x = width / 2 - 2;
        this.y = 0;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        short[][] rotated = new short[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
    }
}
