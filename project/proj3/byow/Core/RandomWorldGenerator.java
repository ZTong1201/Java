package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.Position;

import java.util.*;

public class RandomWorldGenerator {
    TERenderer ter = new TERenderer();
    TETile[][] randomWorldFrame;
    /* Feel free to change the width and height. */
    private int WIDTH;
    private int HEIGHT;
    private Random RANDOM;

    public RandomWorldGenerator(int width, int height, long seed) {
        RANDOM = new Random(seed);
        WIDTH = width;
        HEIGHT = height;
    }

    public TETile[][] getRandomWorldFrame() {
        ter.initialize(WIDTH, HEIGHT);
        randomWorldFrame = new TETile[WIDTH][HEIGHT];
        initialize(randomWorldFrame, Tileset.NOTHING);
        for(int i = 0; i < Math.max(WIDTH, HEIGHT); i++) {
            Position roomStart = new Position(RandomUtils.uniform(RANDOM, WIDTH), RandomUtils.uniform(RANDOM, HEIGHT));
            placeRandomRoom(randomWorldFrame, roomStart, RANDOM);
        }
        for(int i = 0; i < 2 * Math.max(WIDTH, HEIGHT); i++) {
            Position hallwayStart = new Position(RandomUtils.uniform(RANDOM, WIDTH), RandomUtils.uniform(RANDOM, HEIGHT));
            placeARandomHallway(randomWorldFrame, hallwayStart, RANDOM);
        }
        removeWall(randomWorldFrame);
        addRandomExit(randomWorldFrame, RANDOM);
        ter.renderFrame(randomWorldFrame);
        return randomWorldFrame;
    }

    private void initialize(TETile[][] world, TETile t) {
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                world[i][j] = t;
            }
        }
    }

    private void placeRandomRoom(TETile[][] world, Position start, Random RANDOM) {
        int roomWidth = RandomUtils.uniform(RANDOM, 5, 10);
        int roomHeight = RandomUtils.uniform(RANDOM, 5, 10);
        if(canPlaceARoom(world, start, roomWidth, roomHeight)) {

            //Draw a room surrounded by wall
            for(int i = start.x; i < start.x + roomWidth; i++) {
                world[i][start.y] = Tileset.WALL;
                world[i][start.y + roomHeight - 1] = Tileset.WALL;
            }
            for(int j = start.y; j < start.y + roomHeight; j++) {
                world[start.x][j] = Tileset.WALL;
                world[start.x + roomWidth - 1][j] = Tileset.WALL;
            }

            //Draw floors inside the room
            for(int i = start.x + 1; i < start.x + roomWidth - 1; i++) {
                for(int j = start.y + 1; j < start.y + roomHeight - 1; j++) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }

    }


    private boolean canPlaceARoom(TETile[][] world, Position p, int roomWidth, int roomHeight) {
        if(p.x + roomWidth >= WIDTH || p.y + roomHeight >= HEIGHT) {
            return false;
        }
        for(int i = p.x; i < p.x + roomWidth; i++) {
            for(int j = p.y; j < p.y + roomHeight; j++) {
                if(!world[i][j].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeARandomHallway(TETile[][] world, Position start, Random RANDOM) {
        while(!(world[start.x][start.y].equals(Tileset.WALL)) || start.x < 3 || start.y < 3 ||
                start.x > WIDTH - 3 || start.y > HEIGHT - 3) {
            start = new Position(RandomUtils.uniform(RANDOM, WIDTH), RandomUtils.uniform(RANDOM, HEIGHT));

            if(start.x < 3 || start.y < 3 || start.x > WIDTH - 3 || start.y > HEIGHT - 3) {
                continue;
            }

            if(!canPlaceAHallway(world, start)) {
                continue;
            }
        }

        HallwayDirection direction = hallwayDirection(world, start);
        switch (direction) {
            case UP: {
                while(start.y < HEIGHT - 2 && !isConnected(world, start, HallwayDirection.UP)) {
                    world[start.x - 1][start.y] = Tileset.WALL;
                    world[start.x + 1][start.y] = Tileset.WALL;
                    world[start.x][start.y] = Tileset.FLOOR;
                    start.y += 1;
                }
                world[start.x - 1][start.y] = Tileset.WALL;
                world[start.x + 1][start.y] = Tileset.WALL;
                world[start.x][start.y] = Tileset.WALL;
                break;
            }

            case DOWN: {
                while(start.y > 1 && !isConnected(world, start, HallwayDirection.DOWN)) {
                    world[start.x - 1][start.y] = Tileset.WALL;
                    world[start.x + 1][start.y] = Tileset.WALL;
                    world[start.x][start.y] = Tileset.FLOOR;
                    start.y -= 1;
                }
                world[start.x - 1][start.y] = Tileset.WALL;
                world[start.x + 1][start.y] = Tileset.WALL;
                world[start.x][start.y] = Tileset.WALL;
                break;
            }

            case LEFT: {
                while(start.x > 1 && !isConnected(world, start, HallwayDirection.LEFT)) {
                    world[start.x][start.y - 1] = Tileset.WALL;
                    world[start.x][start.y + 1] = Tileset.WALL;
                    world[start.x][start.y] = Tileset.FLOOR;
                    start.x -= 1;
                }
                world[start.x][start.y + 1] = Tileset.WALL;
                world[start.x][start.y - 1] = Tileset.WALL;
                world[start.x][start.y] = Tileset.WALL;
                break;
            }

            case RIGHT: {
                while(start.x < WIDTH - 2 && !isConnected(world, start, HallwayDirection.RIGHT)) {
                    world[start.x][start.y - 1] = Tileset.WALL;
                    world[start.x][start.y + 1] = Tileset.WALL;
                    world[start.x][start.y] = Tileset.FLOOR;
                    start.x += 1;
                }
                world[start.x][start.y + 1] = Tileset.WALL;
                world[start.x][start.y - 1] = Tileset.WALL;
                world[start.x][start.y] = Tileset.WALL;
                break;
            }
        }

    }

    /**
     * Check whether can draw a random hallway at a given position.
     */
    private boolean canPlaceAHallway(TETile[][] world, Position p) {
        if(world[p.x + 1][p.y].equals(Tileset.FLOOR) || world[p.x - 1][p.y].equals(Tileset.FLOOR) ||
                world[p.x][p.y + 1].equals(Tileset.FLOOR) || world[p.x][p.y - 1].equals(Tileset.FLOOR)) {
            return true;
        }
        return false;
    }

    /**
     * Check in which direction a hallway can be drawn.
     */
    private HallwayDirection hallwayDirection(TETile[][] world, Position p) {
        HallwayDirection direction = HallwayDirection.NULL;
        if(world[p.x][p.y - 1].equals(Tileset.FLOOR)) {
            direction = HallwayDirection.UP;
        }
        if(world[p.x][p.y + 1].equals(Tileset.FLOOR)) {
            direction = HallwayDirection.DOWN;
        }
        if(world[p.x + 1][p.y].equals(Tileset.FLOOR)) {
            direction = HallwayDirection.LEFT;
        }
        if(world[p.x - 1][p.y].equals(Tileset.FLOOR)) {
            direction =  HallwayDirection.RIGHT;
        }
        return direction;
    }

    private boolean isConnected(TETile[][] world, Position p, HallwayDirection direction) {
        boolean connected = false;
        switch (direction) {
            case UP: {
                if(world[p.x - 1][p.y].equals(Tileset.FLOOR) || world[p.x + 1][p.y].equals(Tileset.FLOOR) ||
                        world[p.x][p.y + 1].equals(Tileset.FLOOR)) {
                    connected = true;
                }
                break;
            }
            case DOWN: {
                if(world[p.x - 1][p.y].equals(Tileset.FLOOR) || world[p.x + 1][p.y].equals(Tileset.FLOOR) ||
                        world[p.x][p.y - 1].equals(Tileset.FLOOR)) {
                    connected = true;
                }
                break;
            }
            case LEFT: {
                if(world[p.x][p.y - 1].equals(Tileset.FLOOR) || world[p.x][p.y + 1].equals(Tileset.FLOOR) ||
                        world[p.x - 1][p.y].equals(Tileset.FLOOR)) {
                    connected = true;
                }
                break;
            }
            case RIGHT: {
                if(world[p.x][p.y - 1].equals(Tileset.FLOOR) || world[p.x][p.y + 1].equals(Tileset.FLOOR) ||
                        world[p.x + 1][p.y].equals(Tileset.FLOOR)) {
                    connected = true;
                }
                break;
            }
        }
        return connected;
    }

    private void removeWall(TETile[][] world) {

        for(int i = 2; i < WIDTH - 2; i++) {
            for(int j = 2; j < HEIGHT - 2; j++) {
                Position currPos = new Position(i, j);
                if(needRemoval(world, currPos)) {
                    world[currPos.x][currPos.y] = Tileset.FLOOR;
                }
            }
        }
        for(int i = 2; i < WIDTH - 2; i++) {
            for(int j = 2; j < HEIGHT - 2; j++) {
                Position currPos = new Position(i, j);
                if(wallWithOpenHallways(world, currPos)) {
                    world[currPos.x][currPos.y] = Tileset.FLOOR;
                }
            }
        }
    }

    private boolean needRemoval(TETile[][] world, Position p) {
        if((world[p.x + 1][p.y].equals(Tileset.FLOOR) && world[p.x - 1][p.y].equals(Tileset.FLOOR) &&
                world[p.x][p.y - 1].equals(Tileset.WALL) && world[p.x][p.y + 1].equals(Tileset.WALL)) ||
                (world[p.x + 1][p.y].equals(Tileset.WALL) && world[p.x - 1][p.y].equals(Tileset.WALL) &&
                        world[p.x][p.y - 1].equals(Tileset.FLOOR) && world[p.x][p.y + 1].equals(Tileset.FLOOR))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean wallWithOpenHallways(TETile[][] world, Position p) {
        if(world[p.x][p.y].equals(Tileset.FLOOR)) return false;
        int numOfFloors = 0;
        if(world[p.x + 1][p.y].equals(Tileset.FLOOR)) {
            numOfFloors += 1;
        }
        if(world[p.x - 1][p.y].equals(Tileset.FLOOR)) {
            numOfFloors += 1;
        }
        if(world[p.x][p.y + 1].equals(Tileset.FLOOR)) {
            numOfFloors += 1;
        }
        if(world[p.x][p.y - 1].equals(Tileset.FLOOR)) {
            numOfFloors += 1;
        }
        if(numOfFloors >= 3) return true;
        return false;

    }

    private void addRandomExit(TETile[][] world, Random RANDOM) {
        Position p = new Position(RandomUtils.uniform(RANDOM, 3, WIDTH - 3), RandomUtils.uniform(RANDOM, 3, HEIGHT - 3));
        if(world[p.x][p.y].equals(Tileset.NOTHING)) {
            p = new Position(RandomUtils.uniform(RANDOM, 3, WIDTH - 3), RandomUtils.uniform(RANDOM, 3, HEIGHT - 3));
        }
        world[p.x][p.y] = Tileset.UNLOCKED_DOOR;
    }

    public Position addRandomAvatar(TETile[][] world, Random RANDOM) {
        Position p = new Position(RandomUtils.uniform(RANDOM, 3, WIDTH - 3), RandomUtils.uniform(RANDOM, 3, HEIGHT - 3));
        if(!world[p.x][p.y].equals(Tileset.FLOOR)) {
            p = new Position(RandomUtils.uniform(RANDOM, 3, WIDTH - 3), RandomUtils.uniform(RANDOM, 3, HEIGHT - 3));
        }
        world[p.x][p.y] = Tileset.AVATAR;
        return p;
    }


}
