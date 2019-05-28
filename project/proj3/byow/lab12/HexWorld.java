package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int SEED = 2;
    private static final Random RANDOM = new Random(SEED);


    /**
     *  Add a row with desired tile
     */
    private static void addTiles(TETile[][] world, Position startPoint, int width, TETile t) {
        for(int i = 0; i < width; i++) {
            world[startPoint.x + i][startPoint.y] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     *
     * Compute the new start point for a given row
     */
    private static Position newPosition(Position p, int row, int s){
        Position newStart;
        if(row < s) {
            newStart = new Position(p.x - row, p.y + row);
        } else {
            newStart = new Position(p.x - 2*s + row + 1, p.y + row);
        }
        return newStart;
    }

    /**
     * Compute the width, i.e. the number of tiles in a given row
     */
    private static int width(int row, int s) {
        if(row < s) {
            return s + 2*row;
        } else {
            return s + 2*(2*s - row - 1);
        }
    }

    /**
     *
     * Draw a Hexagon of desired tires in world
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if(s < 2) throw new IllegalArgumentException("Hexagon size cannot be less than 2.");
        for(int i = 0; i < 2*s; i++) {
            Position startPoint = newPosition(p, i, s);
            int width = width(i, s);
            addTiles(world, startPoint, width, t);
        }
    }

    /**
     * Initialize the world with all empty tiles
     */
    public static void initialize(TETile[][] world, TETile t) {
        for(int i = 0; i < world.length; i++) {
            for(int j = 0; j < world[0].length; j++) {
                world[i][j] = t;
            }
        }
    }

    /**
     * Select a random tile type in Tileset
     */
    private static TETile randomTETile() {
        int rand = RANDOM.nextInt(10);
        switch (rand) {
            case 0: return Tileset.FLOOR;
            case 1: return Tileset.AVATAR;
            case 2: return Tileset.FLOWER;
            case 3: return Tileset.WALL;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.LOCKED_DOOR;
            case 6: return  Tileset.MOUNTAIN;
            case 7: return Tileset.SAND;
            case 8: return Tileset.TREE;
            case 9: return Tileset.UNLOCKED_DOOR;
            default: return Tileset.WATER;

        }
    }

    /**
     * Draw a series of size of hexagons in the world vertically
     * @param world The world needed drawing
     * @param p The starting point of drawing the first hexagon
     * @param size The desired size of hexagons to be drawn
     * @param num The number of hexagons layout vertically
     */
    public static void drawRandomVerticalHexes(TETile[][] world, Position p, int size, int num) {
        Position start = p;
        for(int i = 0; i < num; i++) {
            addHexagon(world, start, size, randomTETile());
            start = new Position(start.x, start.y + 2 * size);
        }
    }

    private static Position bottomRightStartingPoint(Position p, int size) {
        return new Position(p.x + 2 * size - 1, p.y - size);
    }

    private static Position topRightStartingPoint(Position p, int size) {
        return new Position(p.x + 2 * size - 1, p.y + size);
    }

    public static void drawHexagonTesselation(TETile[][] world, Position p, int size) {
        int num = 3;
        Position start = p;
        for(int i = 0; i < 5; i++) {
            switch (i) {
                case 0: case 1: {
                    drawRandomVerticalHexes(world, start, size, num);
                    start = bottomRightStartingPoint(start, size);
                    num += 1;
                    break;
                }
                case 2: case 3: case 4: {
                    drawRandomVerticalHexes(world, start, size, num);
                    start = topRightStartingPoint(start, size);
                    num -= 1;
                    break;
                }
            }
        }
    }

    @Test
    public void testWidth() {
        assertEquals(3, width(0, 3));
        assertEquals(5, width(1, 3));
        assertEquals(7, width(2, 3));
        assertEquals(7, width(3, 3));
        assertEquals(5, width(4, 3));
        assertEquals(3, width(5, 3));
        assertEquals(2, width(0, 2));
        assertEquals(4, width(1, 2));
        assertEquals(4, width(2, 2));
        assertEquals(2, width(3, 2));
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        Position lowerLeft = new Position(15, 15);
        initialize(hexWorld, Tileset.NOTHING);
        drawHexagonTesselation(hexWorld, lowerLeft, 3);
        ter.renderFrame(hexWorld);
    }

}
