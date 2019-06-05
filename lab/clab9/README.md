## Graphs
This lab is designed to investigate the ways graphs can model and solve problems.

<b>Problem Statement</b>

You’re hosting a party. Unfortunately, many of your invitees are sworn enemies of each other. As the organizer, 
to ensure aggregate happiness, you’d like to separate the party-goers into two groups such that no two members of the 
same group are enemies. You’d like to know if this is possible for your party.

Your friend Euler gives you a graph representing enmity-relationships among the invitees: 
Each invitee is mapped to a vertex, and an edge (u,v) means invitee u and invitee v are enemies.

The SeparableEnemySolver class constructor either initializes a Graph from an input file, or uses a Graph instance passed in.

<b>Task</b>

Implement the function isSeparable() in the SeparableEnemySolver class, which returns <b>true</b> if the invitees represented by g 
are separable, and <b>false</b> otherwise.
