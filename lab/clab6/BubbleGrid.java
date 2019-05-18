public class BubbleGrid {
    private int[][] grid;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int rowNum = grid.length;
        int colNum = grid[0].length;
        int[][] gridCopy = new int[rowNum][colNum];
        int[] dr = new int[]{0, 1, 0, -1};
        int[] dc = new int[]{1, 0, -1, 0};

        for (int i = 0; i < rowNum; i++) {
            gridCopy[i] = grid[i].clone();
        }

        for (int[] dart : darts) {
            gridCopy[dart[0]][dart[1]] = 0;
        }

        UnionFind u = new UnionFind(rowNum*colNum + 1);
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                int index = i * colNum + j;
                if (gridCopy[i][j] == 1) {
                    if (i == 0) {
                        u.union(index, rowNum*colNum);
                    }
                    if ((i >= 1) && (gridCopy[i-1][j] == 1)) {
                        u.union(index, (i - 1)*colNum + j);
                    }
                    if ((j >= 1) && (gridCopy[i][j-1] == 1)) {
                        u.union(index, i * colNum + j - 1);
                    }
                }
            }
        }

        int[] res = new int[darts.length];
        int t = darts.length - 1;
        while (t >= 0) {
            int preSize = u.sizeOf(rowNum*colNum);
            int x = darts[t][0];
            int y = darts[t][1];
            if (grid[x][y] == 0) {
                t -= 1;
            } else {
                int index = x * colNum + y;
                for (int k = 0; k < 4; k++) {
                    int newX = x + dr[k];
                    int newY = y + dc[k];
                    if ((newX >= 0) && (newX < rowNum) && (newY >= 0) && (newY < colNum) && (gridCopy[newX][newY] == 1)) {
                        u.union(index, newX * colNum + newY);
                    }
                }
                if (x == 0) {
                    u.union(index, rowNum*colNum);
                }
                gridCopy[x][y] = 1;
                res[t] = Math.max(0, u.sizeOf(rowNum*colNum) - preSize - 1);
                t -= 1;
            }
        }
        return res;
    }
}
