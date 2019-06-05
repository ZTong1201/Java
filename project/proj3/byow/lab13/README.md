## Memory Game
Still, this is a helper lab to familiarize you with useful tools necessary for the project and teach you some programming 
paradigms you might end up using.

<b>Problem Statement</b>

In preparation for making your game, we will use StdDraw and java.util.Random to construct a simple memory game. T
his game is much like the electronic toy Simon, but on a computer and with a keyboard instead of with 4 colored buttons.
The goal of the game will be to type in a randomly generated target string of characters after it is briefly displayed on 
the screen one letter at a time. The target string starts off as a single letter, but for each successful string entered, 
the game gets harder by making the target string longer.

Eventually we want MemoryGame.java to have a main method which will launch a playable memory game, but instead of jumping 
straight into the implementation of the game, it is good to try and break down what tasks you will need to perform in order to run a game.
For this memory game it would looks something like:
1. Create the game window
2. Randomly generate a target string
3. Display target string on screen one character at a time
4. Wait for player input until they type in as many characters are there are in the target
5. Repeat from step 2 if player input matches the target string except with a longer random target string

After implementation, the memory game UI would be something looks like:
<p align="center">
<img src="https://sp19.datastructur.es/materials/lab/lab13/image.png">
</p>
