import org.junit.Test;
import static org.junit.Assert.*;

public class BubbleGridTest {

    @Test
    public void testBasic() {

        int[][] grid = {{1, 0, 0, 0},
                        {1, 1, 1, 0}};
        int[][] darts = {{1, 0}};
        int[] expected = {2};

        validate(grid, darts, expected);

        int[][] grid2 = {{1, 1, 0},
                {1, 0, 0}, {1, 1, 0}, {1, 1, 1}};
        int[][] darts2 = {{2, 2}, {2, 0}};
        int[] expected2 = {0, 4};

        validate(grid2, darts2, expected2);

        int[][] grid3 = {{1, 0, 1}, {1, 1, 1}};
        int[][] darts3 = {{0, 0}, {0, 2}, {1, 1}};
        int[] expected3 = {0, 3, 0};

        validate(grid3, darts3, expected3);

        int[][] grid4 = {{1}, {1}, {1}, {1}, {1}};
        int[][] darts4 = {{3, 0}, {4, 0}, {1, 0}, {2, 0}, {0, 0}};
        int[] expected4 = {1, 0, 1, 0, 0};

        validate(grid4, darts4, expected4);

        int[][] grid5 = {{0, 1, 1, 1, 1}, {1, 1, 1, 1, 0}, {1, 1, 1, 1, 0},
                {0, 0, 1, 1, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        int[][] darts5 = {{6, 0}, {1, 0}, {4, 3}, {1, 2}, {7, 1}, {6, 3}, {5, 2}, {5, 1},
                {2, 4}, {4, 4}, {7, 3}};
        int[] expected5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        validate(grid5, darts5, expected5);
    }

    private void validate(int[][] grid, int[][] darts, int[] expected) {
        BubbleGrid sol = new BubbleGrid(grid);
        assertArrayEquals(expected, sol.popBubbles(darts));
    }
}
