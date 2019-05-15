import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
        String input2 = "hello";
        String pattern2 = "ll";
        assertEquals(2, RabinKarpAlgorithm.rabinKarp(input2, pattern2));
        assertNotEquals(-1, RabinKarpAlgorithm.rabinKarp(input2, pattern2));
    }
}
