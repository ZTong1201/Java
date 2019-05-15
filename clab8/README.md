### Heaps and Hashes
In this challenging lab, we have two tasks.
1. Given a list of commercial airline flights, stored as an ArrayList. Each Flight object stores a flight start time, a flight ending time,
and a number of passengers. Try to find the largest number of people that have ever been in flight at once.
2. Implementing the [Rabin-Karp algorithm](https://en.wikipedia.org/wiki/Rabin%E2%80%93Karp_algorith), which a string-searching algorithm that “uses hashing to find any one of a set of pattern strings
in a text”.

First task can be easily solve using two priority queues (implemented by Min Heap). The first MinHeap records flight by the start time, whereas
the second MinHeap records flight by the ending time. We peek the flights in both MinHeap, and remove the smallest flight. If this flight
is coming from the first MinHeap, add the number of passengers and record the best we have seen. If this flight is coming from the second
MinHeap, subtract the number of passengers. We keep doing this until the first MinHeap becomes empty, and return the best value recorded.

For second task, we first implement a RollingString class using a Deque data structure. This class is designed for adding and removing characters
and computing hash values in constant time. If a given pattern cannot be found or the inputs are invalid (i.e. the pattern length to match
is longer than the string itself), we return -1. Otherwise, we return the corresponding index in the string.
