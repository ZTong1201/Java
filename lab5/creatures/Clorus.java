package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {
    /**
     * color of a clorus
     */
    private int r, g, b;
    private int colorShift = 5;

    /**
     * creates clorus with energy equal to E.
     *
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * Should return a color with red = 34, green = 0, blue = 231
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /**
     * Cloruses should lose 0.03 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.03;
        energy = Math.max(energy, 0.0);
    }


    /**
     * Cloruses lose 0.01 energy when staying.
     */
    public void stay() {
        b += HugLifeUtils.randomInt(-colorShift, colorShift);
        b = Math.min(b, 255);
        b = Math.max(b, 0);
        energy -= 0.01;
        energy = Math.max(energy, 0.0);
    }

    /**
     * Cloruses and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby Clorus.
     */
    public Clorus replicate() {
        double energyToGive = energy * 0.5;
        energy = energy * 0.5;
        return new Clorus(energyToGive);
    }

    /**
     * If a Clorus attacks another creature, it should gain
     * that creature’s energy.
     */
    public void attack(Creature c) {
        double energyGain = c.energy();
        energy += energyGain;
    }

    /**
     * Cloruses take exactly the following actions based on NEIGHBORS:
     * 1. If there are no empty squares, the Clorus will STAY
     * (even if there are Plips nearby they could attack since
     * plip squares do not count as empty squares)
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK
     * one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     * it will REPLICATE to a random empty square
     * 4. Otherwise, the Clorus will MOVE to a random empty square
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyPlip = false;

        for (Map.Entry<Direction, Occupant> pair : neighbors.entrySet()){
            if (pair.getValue().name().equals("plip")) {
                anyPlip = true;
                plipNeighbors.addLast(pair.getKey());
            } else if (pair.getValue().name().equals("empty")) {
                emptyNeighbors.addLast(pair.getKey());
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip) {
            //Plip newP = this.replicate();
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plipNeighbors));
        } else if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }

}
