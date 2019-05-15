### Hash Map
In this lab, I implement map using a hash table as my fundamental data structure. Hashing is a very powerful methodology as long as the
we control the load factor (number of items divided by number of buckets) properly. The searching method is AMORTIZED O(1) runtime when we
keep resizing the hash table and assume items are evenly spread. Moreover, the performance of a hash map will not be influenced by insertion
order. The InsertInOrderSpeedTest.java indicates that we obtain extremely better results than the BST Map. If we are supposed to insert items
with a whatever order (e.g. chronologically), Hash Map might be a desired option.
