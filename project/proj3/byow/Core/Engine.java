package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.Position;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private long SEED;
    private static final int titleFont = 70;
    private static final int keywordFont = 30;
    private static final int descriptionFont = 20;
    private TETile[][] randomWorld;
    private Position currPos;
    private Random RANDOM;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        randomWorld = new TETile[WIDTH][HEIGHT];
        drawMainMenu();
        RandomWorldGenerator randomWorldGenerator = new RandomWorldGenerator(WIDTH, HEIGHT, SEED);
        randomWorld = randomWorldGenerator.getRandomWorldFrame();
        currPos = randomWorldGenerator.addRandomAvatar(randomWorld, RANDOM);
        ter.renderFrame(randomWorld);

        startPlayGame();

    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        if(input.toUpperCase().contains("N") && input.toUpperCase().contains("S")) {
            int seedStart = input.toUpperCase().indexOf("N") + 1;
            int seedStop = input.toUpperCase().indexOf("S");
            if(input.substring(seedStart, seedStop).length() > 0) {
                SEED = Long.valueOf(input.substring(seedStart, seedStop));
            } else {
                throw new IllegalArgumentException("You must input a number between N and S");
            }
        } else {
            throw new IllegalArgumentException("Please input a string starting with N and ending with S.");
        }
        RandomWorldGenerator randomWorldGenerator = new RandomWorldGenerator(WIDTH, HEIGHT, SEED);
        return randomWorldGenerator.randomWorldFrame;
    }

    private void drawMainMenu() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, titleFont));
        StdDraw.text(WIDTH/2, HEIGHT * 3/4, "CS61B : THE GAME");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, keywordFont));
        StdDraw.text(WIDTH/2, HEIGHT/2, "New Game(N)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 3, "Load Game(L)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 6, "Quit(Q)");
        StdDraw.show();

        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                switch (key) {
                    case 'N': case 'n': {
                        getSeed();
                        return;
                    }
                    case 'L': case 'l': {
                        loadGameStatus();
                        startPlayGame();
                        break;
                    }
                    case 'Q': case 'q': {
                        System.exit(0);
                    }
                    default: break;
                }
            }
        }
    }

    private void getSeed() {
        StringBuilder res = new StringBuilder();
        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if(key == 'S' || key == 's') {
                    SEED = Long.valueOf(res.toString());
                    RANDOM = new Random(SEED);
                    return;
                }
                res.append(key);
                StdDraw.clear(Color.BLACK);
                StdDraw.setFont(new Font("Monaco", Font.BOLD, titleFont));
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(WIDTH/2, HEIGHT * 3/4, "CS61B : THE GAME");
                StdDraw.setFont(new Font("Monaco", Font.BOLD, keywordFont));
                StdDraw.text(WIDTH/2, HEIGHT/2, "Entering Seed:");
                StdDraw.text(WIDTH/2, HEIGHT/2 - 3, res.toString());
                StdDraw.show();
            }
        }
    }

    private void startPlayGame() {
        ter.renderFrame(randomWorld);
        while(true) {
            showDescription();
            if(StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                switch (key) {
                    case 'W': case 'w': {
                        if(randomWorld[currPos.x][currPos.y + 1].equals(Tileset.WALL)) {
                            break;
                        }
                        if(randomWorld[currPos.x][currPos.y + 1].equals(Tileset.UNLOCKED_DOOR)) {
                            findExit();
                            System.exit(0);
                        }
                        else {
                            randomWorld[currPos.x][currPos.y] = Tileset.FLOOR;
                            randomWorld[currPos.x][currPos.y + 1] = Tileset.AVATAR;
                            currPos.y += 1;
                            ter.renderFrame(randomWorld);
                        }
                        break;
                    }
                    case 'S': case 's': {
                        if(randomWorld[currPos.x][currPos.y - 1].equals(Tileset.WALL)) {
                            break;
                        }
                        if(randomWorld[currPos.x][currPos.y - 1].equals(Tileset.UNLOCKED_DOOR)) {
                            findExit();
                            System.exit(0);
                        }
                        else {
                            randomWorld[currPos.x][currPos.y] = Tileset.FLOOR;
                            randomWorld[currPos.x][currPos.y - 1] = Tileset.AVATAR;
                            currPos.y -= 1;
                            ter.renderFrame(randomWorld);
                        }
                        break;
                    }
                    case 'A': case 'a': {
                        if(randomWorld[currPos.x - 1][currPos.y].equals(Tileset.WALL)) {
                            break;
                        }
                        if(randomWorld[currPos.x - 1][currPos.y].equals(Tileset.UNLOCKED_DOOR)) {
                            findExit();
                            System.exit(0);
                        }
                        else {
                            randomWorld[currPos.x][currPos.y] = Tileset.FLOOR;
                            randomWorld[currPos.x - 1][currPos.y] = Tileset.AVATAR;
                            currPos.x -= 1;
                            ter.renderFrame(randomWorld);
                        }
                        break;
                    }
                    case 'D': case 'd': {
                        if(randomWorld[currPos.x + 1][currPos.y].equals(Tileset.WALL)) {
                            break;
                        }
                        if(randomWorld[currPos.x + 1][currPos.y].equals(Tileset.UNLOCKED_DOOR)) {
                            findExit();
                            System.exit(0);
                        }
                        else {
                            randomWorld[currPos.x][currPos.y] = Tileset.FLOOR;
                            randomWorld[currPos.x + 1][currPos.y] = Tileset.AVATAR;
                            currPos.x += 1;
                            ter.renderFrame(randomWorld);
                        }
                        break;
                    }
                    case ':': {
                        while(true) {
                            if(StdDraw.hasNextKeyTyped()) {
                                char c = StdDraw.nextKeyTyped();
                                if(c == 'Q' || c == 'q') {
                                    saveGameStatus();
                                    System.exit(0);
                                } else {
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    default: break;
                }
            }
        }
    }

    private void showDescription() {
        ter.renderFrame(randomWorld);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if(!randomWorld[x][y].equals(Tileset.NOTHING)) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Monaco", Font.BOLD, descriptionFont));
            StdDraw.text(5, HEIGHT - 1, randomWorld[x][y].description());
            StdDraw.show();
        }
    }

    private void findExit() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, keywordFont));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Congratulations! You found the exit!");
        //StdDraw.text(WIDTH / 2, HEIGHT / 2 - 10, "You found the exit!");
        StdDraw.show();
        StdDraw.pause(500);
    }

    private void saveGameStatus() {
        File f = new File("./savedGame.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this.currPos);
            os.writeObject(this.SEED);
            os.close();
            fs.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void loadGameStatus() {
        File f = new File("./savedGame.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                currPos = (Position) os.readObject();
                SEED = (Long) os.readObject();
                RandomWorldGenerator randomWorldGenerator = new RandomWorldGenerator(WIDTH, HEIGHT, SEED);
                randomWorld = randomWorldGenerator.getRandomWorldFrame();
                randomWorld[currPos.x][currPos.y] = Tileset.AVATAR;
                os.close();
                fs.close();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

    }
}
