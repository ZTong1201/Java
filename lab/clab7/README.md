### Binary Search Tree Performance
In this lab, I am trying to investigate the performance (average depth actually) of a binary search tree with random deletions and insertions.
The experiment is roughly as follows:

1. Initialize a tree by randomly inserting N items. Record the average depth observed as the ‘starting depth’.
2. Randomly delete an item using asymmetric Hibbard deletion.
3. Randomly insert a new item.
4. Record the average depth of the tree.
5. Repeat steps 2-4 a total of M times.
6. Plot the data.

Using asymmetric Hibbard deletion, we actually delete nodes with two children are replaced ONLY with their successor. The result
indicates that these steps actually increase the average depth of the tree. We then compare the results with symmetrical deletion, i.e.
randomly switching between picking the predecessor and the successor. The average depth of the tree actually decreases as M goes large
if we take symmetrical deletion.
