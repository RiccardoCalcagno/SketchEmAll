# SketchEmAll

Project of
Adv. Programming of Interactive Systems

## Authors

Group 11:
Katerina Koleva, Riccardo Calcagno and Julien Marzal

### Application: 
Our application is a mini-game, that uses the tools of a creative application. In “Scketch ‘em all” there is one drawer and few people who can guess. The guessers can make attempts by submitting the name of the drawed thing. We decided to include cards with words and different modes of drawing.

### Modes of drawing: 
The modes should challenge the user to be original with the tools given, when representing ordinary words. 
 
### **Pencil**: the basic stroke drawing, mapped 1:1 with the cursor 
### **Crazy pen**: a tool that transforms each new point located by the cursor by randow offset vector.  This vector gets bigger when the speed of the movement increases. 
### **Inverted pen**: a tool that takes the cursor's movement and applies central symetry transform 
### **Ink blot**: You can create and strech smooth filled closed splines in two colors - black and gray.

### Timer: 
The session has a duration of 10 minutes, this time is splitted into different chunks of time, each of them representing the available time to guess a single drawing. In this perspective, at any new figure will be added a new chunk of time to the timer. If the player guesses the word than he gains a badge. If he guesses right or the number of attempts ends or the time of the chunk is over than the players can continue with the next drawing by picking another word.

### Picking words: 
The words as well as the modes would be chosen randomly - so the drawer could not know what the next turn would be. When picking a word the drawer should click on a card to turn it around and see the next word and mode. At this point the other players should not be looking at the screen.

### Goal: 
The goal is for the drawer to do as much words possible in this 10 minute window and the guessers to guess them correctly. When the word is correctly guessed in 3 attempts and before the time is up, the players get a reward in the form of a badge with the drawing.

### The session: 
A session manager handles the status of the session of the game and triggers the presentation of procedure like: start of the game, the end of the game.

### Turns management: 
The turns manager relies on a set of controllers that permit the start of a new turn, new word to guess, clear attempt counter, the start of a new timer’s chunk, it listens to all the possible reasons for the end turn and fire the procedures to handle that kind of event.

### Drawing area: 
The canvas manager allows the interaction with the drawer: it needs to accept input for drawing and select already created figures. It should allow programmable interaction such as clearing the canvas, taking screenshots and updating the position of dynamic figures.

### Progression Area: 
The progression area has two parts. One of them is the time management responsible for stopping and restarting the time of the session. The time manager should also handle time left of the turn and count the time left of the session. 

The other part of the progression area are the badges management which includes the creation of badges procedure and adding it to the badges panel with the border with the mode's color and the guessed word.

### Words management: 
The word input manager stores the current word and in the event of submission checks if the word is correct. Another procedure is picking a new random word from a file with words.

### Structure
We tried basing our architecture on PAC principles. We used Java and Java Swing library. We added audio feedback at the end of every turn.

### Conclusion
We think the main focus of the game, which are the different drawing interactions with the canvas, is achieved. It is interesting and challenging to draw with some of the modes, but that is the point, to be creative. For us it was fun to play that game with our friends.
