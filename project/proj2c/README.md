## Bear Maps
This project of [CS61B Data Structures](https://sp19.datastructur.es/) (originally created by [Alan Yao](https://www.linkedin.com/in/alanyao)) is designed to build a small piece of a
web-browser based Google Maps clone.

For this web mapping application, we will focus on four major parts of functions, namely, map rastering, finding shortest route between two
user-identified locations, autocomplete searching, and generating turn-by-turn navigation.

Specifically, the web application has functionalities as follows:

Funtions | Detailed Explanation
--------------- | ---------------
Map Rastering | Given coordinates of a rectangular region of the world and the size of the web browser window, provide images of the appropriate resolution that cover that region.
Routing | Given a start and destination latitude and longitude, provide street directions between those points.
Autocomplete | Given a string, find all locations that match that string.
Turn-by-turn Navigation | Augment the routing from Part II to include written driving directions

Here is a link you can try using heroku app: [bearmaps](http://bearmaps-sp19-ztong5.herokuapp.com/map.html).
The expected results will be displayed as below.
1. Map Rastering
![Image of Initialization](https://github.com/ZTong1201/Java/blob/master/project/proj2c/static/test%20demo/initialization.png)
2. Autocomplete Searching
![Image of Autocompletion](https://github.com/ZTong1201/Java/blob/master/project/proj2c/static/test%20demo/autocompletion.png)
3. Location Data
![Image of Location](https://github.com/ZTong1201/Java/blob/master/project/proj2c/static/test%20demo/location_search.png)
4. Routing & Turn-by-turn Navigation
![Image of Navigatiom](https://github.com/ZTong1201/Java/blob/master/project/proj2c/static/test%20demo/turn-by-turn_navigation.png)

#### Run this app
1. Clone this repo and [library-sp19 repo](https://github.com/Berkeley-CS61B/library-sp19/tree/7fe87114b62fdcf0a6af3ec3d9e96b0ef4f64523) which
contains necessary libraries, street map data, and all the images.
2. Import projects into Intellij IDEA and modify project structure to select proj2c as library and static as your source folder.
3. Run MapServer.java in bearmaps/proj2c, your computer will be acting as a web server.
4. Right click on map.html locates in static/page, then click on "Open in Browser" (I personally recommend Chrome, however, you can choose
whichever you like).
5. For more detailed setup information, please refer to [CS61B website](https://sp19.datastructur.es/materials/proj/proj2c/proj2c) directly.
