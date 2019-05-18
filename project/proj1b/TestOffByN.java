import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    OffByN offBy5 = new OffByN(5);

    @Test
    public void TestOffByN() {
        assertTrue(offBy5.equalChars('a','f'));
        assertTrue(offBy5.equalChars('f','a'));
        assertTrue(offBy5.equalChars('g','l'));
        assertFalse(offBy5.equalChars('a','a'));
        assertFalse(offBy5.equalChars('%','&'));
    }
}