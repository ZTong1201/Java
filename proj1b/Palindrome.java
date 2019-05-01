public class Palindrome {

    /**
     * Convert a string of word into a deque
     * @param word
     * @return A wordDeque
     */
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque1<Character> wordDeque = new LinkedListDeque1<>();
        for (int i = 0; i < word.length(); i++ ) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /**
     * Test whether a given word is a palindrome or not. Without Recursion.
     * @param word
     * @return true if the word is palindrome, false otherwise
     */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        while (wordDeque.size()>1) {
            Character front = wordDeque.removeFirst();
            Character end = wordDeque.removeLast();
            if (front != end) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        while (wordDeque.size() > 1 ){
            if (!cc.equalChars(wordDeque.removeFirst(),wordDeque.removeLast())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test whether a given word is a palindrome or not. With Recursion.
     * @param word
     * @return true if the word is palindrome, false otherwise
     */

    public boolean isPalindromeRecursive(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        if (wordDeque.size() <= 1) {
            return true;
        }
        if ( wordDeque.removeFirst() != wordDeque.removeLast() ) {
            return false;
        }
        return isPalindromeRecursive(word.substring(1, word.length() - 1));
    }
}
