## Extrinsic Priority Queue and KD Tree
### Proj2A - Extrinsic MinPQ
For this part of the project, I will build an implementation of the ExtrinsicMinPQ interface. The ExtrinsicMinPQ interface quite similar
to the MinPQ interface. The operations are described below:
1. public boolean contains(T item): Returns true if the PQ contains the given item.
2. public void add(T item, double priority): Adds an item of type T with the given priority. If the item already exists, 
throw an IllegalArgumentException. You may assume that item is never null.
3. public T getSmallest(): Returns the item with smallest priority. If no items exist, throw a NoSuchElementException.
4. public T removeSmallest(): Removes and returns the item with smallest priority. If no items exist, throw a NoSuchElementException.
5. public int size(): Returns the number of items.
6. public void changePriority(T item, double priority): Sets the priority of the given item to the given value. If the item does not exist,
throw a NoSuchElementException.

There are four key differences between this abstract data structure and the MinPQ ADT.
1. The priority is extrinsic to the object. That is, rather than relying on some sort of comparison function to decide which item is 
less than another, we simply assign a priority value using the add or changePriority functions.
2. There is an additional changePriority function that allows us to set the extrinsic priority of an item after it has been added.
3. There is a contains method that returns true if the given item exists in the PQ.
4. There may only be one copy of a given item in the priority queue at any time. If you try to add an item that is equal to another 
according to .equals, the PQ should throw an exception. If there are 2 items with the same priority, I break ties arbitrarily.

### Proj2B - K-d Tree
For this part of project, I will implement a K-d tree structure to find the nearest neighbor in a given set of points when the user provides
an arbitrary point in the same dimension. This tree structure should take O(logN) time (where N is the number of points) to find the nearest
neighbor.

These two data structures will be utilized in proj2c bearmaps to find the shortest route between any two points clicked on the map by users.
