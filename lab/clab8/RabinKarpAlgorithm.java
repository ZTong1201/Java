public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        int n = input.length();
        int m = pattern.length();
        if(n < m) {
            return -1;
        }
        RollingString rollingString1 = new RollingString(input, m);
        RollingString rollingString2 = new RollingString(pattern, m);
        int hashNum1 = rollingString1.hashCode();
        int hashNum2 = rollingString2.hashCode();
        if(hashNum1 == hashNum2) {
            if(rollingString1.equals(rollingString2)){
                return 0;
            }
        }
        for(int i = 1; i < n - m + 1; i++) {
            rollingString1.addChar(input.charAt(i + m - 1));
            hashNum1 = rollingString1.hashCode();
            if(hashNum1 == hashNum2) {
                if(rollingString1.equals(rollingString2)) {
                    return i;
                }
            }
        }
        return -1;
    }

}
