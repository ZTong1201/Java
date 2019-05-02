package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(3);
        Plip p = new Plip(2);
        c.attack(p);
        assertEquals(5, c.energy(), 0.01);
        Plip p2 = new Plip(0.5);
        c.attack(p2);
        assertEquals(5.5, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        Clorus newC = c.replicate();
        assertEquals(1, c.energy(), 0.01);
        assertEquals(1, newC.energy(), 0.01);
        Clorus newC2 = newC.replicate();
        assertEquals(1, c.energy(), 0.01);
        assertEquals(0.5, newC.energy(), 0.01);
        assertEquals(0.5, newC2.energy(), 0.01);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // Surrounded by Plips in four directions. No empty adjacent spaces; stay.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> surroundedPlip = new HashMap<Direction, Occupant>();
        surroundedPlip.put(Direction.TOP, new Plip(0.5));
        surroundedPlip.put(Direction.BOTTOM, new Plip(2));
        surroundedPlip.put(Direction.LEFT, new Plip(1.3));
        surroundedPlip.put(Direction.RIGHT, new Plip(0.8));

        actual = c.chooseAction(surroundedPlip);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Plip nearby; should attack Plip.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Plip(0.8));
        topPlip.put(Direction.BOTTOM, new Empty());
        topPlip.put(Direction.LEFT, new Empty());
        topPlip.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(topPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1 and no plip nearby; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> leftEmpty = new HashMap<Direction, Occupant>();
        leftEmpty.put(Direction.TOP, new Impassible());
        leftEmpty.put(Direction.BOTTOM, new Impassible());
        leftEmpty.put(Direction.LEFT, new Empty());
        leftEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(leftEmpty);
        Action unexpected = new Action(Action.ActionType.REPLICATE, Direction.LEFT);

        assertNotEquals(unexpected, actual);


        // Energy < 1 and no plip nearby; move to an empty space.
        c = new Clorus(.99);

        actual = c.chooseAction(leftEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.LEFT);

        assertEquals(expected, actual);

    }
}
