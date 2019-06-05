## AStar Solver
In this assignment, we’ll be building an shortest path solver that can solve arbitrary state space traversal problems. 
Specifically, given a graph of possible states, our puzzle solver will find the optimal route from the start state to a goal state.

This solver will be able to solve a wide variety of problems, including finding driving directions, 
solving a [15 puzzle](https://en.wikipedia.org/wiki/15_puzzle), and finding [word ladders](https://en.wikipedia.org/wiki/Word_ladder).

The algorithm utilized in this homework is actually a enhanced version of Dijkstra's shortest path algorithm called A* algorithm.
This algorithm requires that we start with a priority queue that contains every possible vertex. This is often impossible in practice 
due to the memory limits of real computers. For example, suppose our graph corresponds to every state in a 15 puzzle. 
There are trillions of possible configurations for a 15 possible, so we cannot explicitly build such a graph.

To save memory, we will implement a different version of A* with one fundamental difference: Instead of starting with all vertices 
in the PQ, we’ll start with only the start vertex in the PQ.

This necessitates that we also change our relaxation operation. In the version from lecture, 
a successful relaxation operation updated the priority of the target vertex, but never added anything new. 
Now that the PQ starts off mostly empty, a successful relaxation must add the target vertex if it is not already in the PQ.

We will also make a third but less important change: 
If the algorithm takes longer than some timeout value to find the goal vertex, 
it will stop running and report that a solution was unable to be found. This is done because some A* problems are so hard 
that they can take e.g. billions of years and terabytes amounts of memory (due to a large PQ) to solve.

To summarize, our three differences from the lecture version are:
1. The algorithm starts with only the start vertex in the PQ.
2. When relaxing an edge, if the relaxation is successful and the target vertex is not in the PQ, add it.
3. If the algorithm takes longer than some timeout value, it stops running.
