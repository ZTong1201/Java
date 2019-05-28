package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        while(i < n) {
            res.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
            i += 1;
        }
        return res.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);

        if(!gameOver) {
            Font roundFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(roundFont);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            if(playerTurn) StdDraw.text(width/2, height - 1, "Type!");
            else StdDraw.text(width/2, height - 1, "Watch!");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }

        Font keyfont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(keyfont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for(int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(750);
            drawFrame("");
            StdDraw.pause(750);
        }
    }

    public String solicitNCharsInput(int n) {
        StringBuilder res = new StringBuilder();
        drawFrame(res.toString());

        while(res.length() < n) {
            if(!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            res.append(key);
            drawFrame(res.toString());
        }
        StdDraw.pause(700);
        return res.toString();
    }

    public void startGame() {
        gameOver = false;
        round = 1;
        playerTurn = false;
        while(!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);
            StdDraw.pause(1500);

            String goal = generateRandomString(round);
            flashSequence(goal);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);
            if(!userInput.equals(goal)) {
                gameOver = true;
                drawFrame("Game Over! You made it to round: " + round);
            } else {
                drawFrame("Correct! Next round");
                StdDraw.pause(700);
                round += 1;
            }
        }
    }

}
