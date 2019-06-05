## Bears and Beds
<b>Introduction</b>

The hot new Cal startup AirBearsnBeds has hired you to create an algorithm to help them place their customers in the best possible 
homes to improve their experience. They are currently in their alpha stage so their only customers (for now) are bears. 
Now, a little known fact about bears is that they are very, very picky about their bed sizes: 
they do not like their beds too big or too little - they like them just right. 
Bears are also sensitive creatures who don’t like being compared to other bears, but they are perfectly fine with trying out beds.

<b>Problem Statement</b>
Given a list of Bears with unique but unknown sizes and a list of Beds with corresponding but also unknown sizes 
(not necessarily in the same order), return a list of Bears and a list of Beds such that that the ith Bear in your returned list of 
Bears is the same size as the ith Bed in your returned list of Beds. <b><i>Bears can only be compared to Beds</i></b> and we can 
get feedback on if the Bed is too large, too small, or just right. In addition, <b><i>Beds can only be compared to Bears</i></b> and 
we can get feedback if the Bear is too large for it, too small for it, or just right for it.

<b>Constraints</b>
Your algorithm should run in O(NlogN) time.
