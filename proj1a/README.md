### Linked List Deque vs. Array-based Deque
In this project, I am focusing on implementation of deque using linked list and array. As you may notice in the respoitory,
there are two .java files for each implementation. For linked list deque, I tried two sentinel nodes at the front and the end, 
and a circular topology with one sentinel node. For array-based deque, the realizations are much similar except for taking remainder
to achieve get(int index) method in ArrayDeque2.java. However, the resizing process for this implementation is slower since I simply
utilize a for-loop rather than arraycopy() method.
