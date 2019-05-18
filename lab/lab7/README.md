### Implement Map using Binary Search Tree (BST Map)
In this lab, I implement a map (storing <key, value> pairs>) using a naive binary search tree as my fundamental data structure. If the tree
is actually "bushy", i.e. inserting items with random order, we will have O(logN) runtime (N is the number of nodes in the tree) for adding,
containing and getting methods. However, if we insert items with an ascending order (or a descending order), our BST will degenerate to a
linked list, hence our adding or searching for a key will be O(N) runtime. In InsertRandomSpeedTest.java and InsertInOrderSpeedTest.java,
we actually adding <key, value> pairs in random order and [lexicographically-increasing order](https://en.wikipedia.org/wiki/Lexicographical_order).
The testing results indicate that insertion with order can be very slow.
