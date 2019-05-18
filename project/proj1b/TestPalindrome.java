import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testPalindrome() {
        OffByOne offByOne = new OffByOne();
        OffByN offBy5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("aba", offBy5));
        assertFalse(palindrome.isPalindrome("aaaaaab"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("level"));
    }

    @Test
    public void testPalindromeRecursive() {
        assertTrue(palindrome.isPalindromeRecursive("racecar"));
        assertTrue(palindrome.isPalindromeRecursive("a"));
        assertTrue(palindrome.isPalindromeRecursive(""));
        assertFalse(palindrome.isPalindromeRecursive("aaaaaab"));
        assertFalse(palindrome.isPalindromeRecursive("cat"));
        assertTrue(palindrome.isPalindromeRecursive("level"));
    }
}