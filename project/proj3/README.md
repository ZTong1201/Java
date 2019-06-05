# Build Your Own World
This final project is to design and implement a 2D tile-based world exploration engine. By “tile-based”, we mean the worlds you 
generate will consist of a 2D grid of tiles. By “world exploration engine” we mean that your software will build a world, which the user 
will be able to explore by walking around and interacting with objects in that world. Your world will have an overhead perspective. 
As an example of a much more sophisticated system than you will build, the NES game “Zelda II” is (sometimes) a tile based world 
exploration engine that happens to be a video game:
<p align="center">
<img src="http://www.mobygames.com/images/shots/l/31358-zelda-ii-the-adventure-of-link-nes-screenshot-an-overhead-view.jpg">
</p>

## Phase 1: World Generation
The first goal of the project will be to write a world generator. The requirements for the world are listed below:
1. The world must be a 2D grid, drawn using our tile engine.
2. The world must be pseudorandomly generated.
3. The generated world must include distinct rooms and hallways, though it may also include outdoor spaces.
4. At least some rooms should be rectangular, though you may support other shapes as well.
5. Your world generator must be capable of generating hallways that include turns (or equivalently, straight hallways that intersect).
6. The world should contain a random number of rooms and hallways.
7. The locations of the rooms and hallways should be random.
8. The width and height of rooms should be random.
9. The length of hallways should be random.
10. Rooms and hallways must have walls that are visually distinct from floors. Walls and floors should be visually distinct from 
unused spaces.
11. Rooms and hallways should be connected, i.e. there should not be gaps in the floor between adjacent rooms or hallways.
12. The world should be substantially different each time, i.e. you should not have the same basic layout with easily predictable 
features.

The random world generated would be something looks like below:
For your reference, exit: ![img1](https://github.com/ZTong1201/Java/blob/master/project/proj3/exit.png "exit")
floor: ![img2](https://github.com/ZTong1201/Java/blob/master/project/proj3/floor.png) avatar(you): ![img3](https://github.com/ZTong1201/Java/blob/master/project/proj3/human.png)
wall: ![img4](https://github.com/ZTong1201/Java/blob/master/project/proj3/walls.png)
<p align="center">
<img src="https://github.com/ZTong1201/Java/blob/master/project/proj3/random_world.png">
</p>

### Starting Your Program
Ultimately, your project must support both methods of receiving input, namely Core.Engine.interactWithKeyboard() method, and the other 
using the Core.Engine.interactWithInputSting(String s) method. Specifically, you should be able to handle an input of the format “N#######S” 
where each # is a digit and there can be an arbitrary number of #s. This corresponds to requesting a new world, providing a seed, 
and then pressing S to indicate that the seed has been completely entered.

When your Core.Engine.interactWithKeyboard() method is run, your program must display a Main Menu that provides at LEAST the options to 
start a new world, load a previously saved world, and quit. The Main Menu should be fully navigable via the keyboard, using N for 
“new world”, L for “load world”, and Q for quit. The user interface looks like:
<p align="center">
<img src="https://sp19.datastructur.es/materials/proj/proj3/img/mainmenu_example.png">
</p>

After pressing N for “new world”, the user should be prompted to enter a “random seed”, which is an integer of their choosing. 
This integer will be used to generate the world randomly. After the user has pressed the final number in their seed, they should 
press S to tell the system that they’ve entered the entire seed that they want. Your world generator should be able to handle any 
positive seed up to 9,223,372,036,854,775,807. There is no defined behavior for seeds larger than this.

## Phase 2: Interactivity
In the second phase of the project, you’ll add the ability for the user to actually interact with the world, and will also add user 
interface (UI) elements to your world to make it feel more immersive and informative.

The requirements for interactivity are as follows:
1. The user must be able to control some sort of “avatar” that can moved around using the W, A, S, and D keys. By “avatar”, we just mean 
some sort of on screen representation controlled by the user.
2. The avatar must be able to interact with the world in some way.
3. Your system must be deterministic in that the same sequence of keypresses from the same seed must result in exactly the same behavior 
every time.
4. The only files you may create must have the suffix “.txt” (for example “savefile.txt”).

### UI (User Interface) Appearance
After the user has entered a seed and pressed S, the world should be displayed with a user interface. 
The user interface of your project must include:
1. A 2D grid of tiles showing the current state of the world.
2. A “Heads Up Display” that provides additional information that maybe useful to the user. At the bare minimum, this should include 
Text that describes the tile currently under the mouse pointer.

As shown below, the text that describes the tile displays on the top-left corner ("floor", for whatever reason, the screenshot does not
capture the mouse pointer)
<p align="center">
<img src="https://github.com/ZTong1201/Java/blob/master/project/proj3/UI.png">
</p>

### UI Behavior
After the world has been generated, the user must be in control of some sort of avatar that is displayed in the world. The user must be 
able to move up, left, down, and right using the W, A, S, and D keys, respectively. The system must behave pseudorandomly. That is, 
given a certain seed, the same set of key presses must yield the exact same results!

In addition to movement keys, if the user enters “:Q”, the program should quit and save. This command must immediately quit and save, 
and should require no further keypresses to complete, e.g. do not ask them if they are sure before quitting. 
We will call this single action of quitting and saving at the same time “quit/saving”.

### Saving and Loading
Your system must have the ability to save the state of the world while exploring, as well as to subsequently load the world into the 
exact state it was in when last saved.

When the user restarts byow.Core.Main and presses L, the world should be in exactly the same state as it was before the project was 
terminated. This state includes the state of the random number generator! n the case that a user attempts to load but there is no 
previous save, your system should simply quit and the UI interface should close with no errors produced.

In the base requirements, the command “:Q” should save and completely terminate the program.
