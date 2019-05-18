/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */

    public static int optimalIPL(int N) {
        int[] cache = new int[N];
        for (int i = 0; i < cache.length; i++) {
            if (i == 0) {
                cache[i] = 0;
            } else {
                cache[i] = cache[i - 1] + (int) (Math.log(i + 1)/Math.log(2));
            }
        }
        return cache[N-1];
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        return (double) optimalIPL(N)/N;
    }

    public static void randomInsertItems(BST x, int max) {
        RandomGenerator randomGenerator = new RandomGenerator();
        int rand = randomGenerator.getRandomInt(max);
        while (x.contains(rand)) {
            rand = randomGenerator.getRandomInt(max);
        }
        x.add(rand);
    }

    public static void randomDeleteTakingSuccessor(BST x) {
        x.deleteTakingSuccessor(x.getRandomKey());
    }

    public static void randomDeleteTakingRandom(BST x) {
        x.deleteTakingRandom(x.getRandomKey());
    }
}
