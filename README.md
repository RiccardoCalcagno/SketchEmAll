# SketchEmAll

Project of
Adv. Programming of Interactive Systems

## Authors

Group 11:
Katerina Koleva, Riccardo Calcagno and Julien Marzal

### Application: 
Our application is a mini-game, that uses the tools of a creative application. In “Scketch ‘em all” there is one drawer and few people who can guess. The guessers can make attempts by submitting the name of the drawed thing. We decided to include cards with words and different modes of drawing.
Modes of drawing: The modes should challenge the user to be original with the tools given, when representing ordinary words. Example of those modes are drawing with the mouse, drawing only with rectangles or having the ability only to make points and connect them with lines.
### Timer: 
The session has a duration of 10 minutes, this time would be splitted into different chunks of time, each of them representing the available time to guess a single drawing. In this perspective, at any new figure will be added a new chunk of time to the timer. If the player guesses the word than he gains a reward. If he guesses right or the number of attempts ends or the time of the chunk is over than the players can continue with the next drawing by picking another word.
Picking words: The words as well as the modes would be chosen randomly - so the drawer could not know what the next turn would be. When picking a word the drawer should click on a card to turn it around and see the next word and mode. At this point the other players should not be looking at the screen.
### Goal: 
The goal is for the drawer to do as much words possible in this 10 minute window and the guessers to guess them correctly. When the word is correctly guessed in 3 attempts and before the time is up, the players get a reward in the form of a badge with the drawing.
Why it complies with the topic of the course
We tried to find a project in which we could experiment with a colorful and original set of interactions, especially the ones used by the user to draw.
One goal of the course is to be able to create your own custom widgets, we think the canvas would be a quite complex widget that will require us to define a well structured PAC unit. It will require a fair number of components to draw in the canvas (such as a pencil, segmented pen, drawing with rectangles). It will need a hierarchical model to manage and store graphical items, like lines, rectangles and stickers, and they could be interactive too (through selection and dragging).
## Feature implementation (patterns, algorithms, components)

### The session: 
A main manager unit that handles the status of the session of the game and triggers the presentation of procedure like: start of the game, the end of the game (and if we have more time left overview score).
### Turns management: 
It should rely on a set of controllers that permit the start of a new turn, (new word to guess, clear attempt counter, the start of a new timer’s chunk), it should listen to all the possible reasons for the end turn and fire the procedures to handle that kind of event.
### Drawing area: 
We should develop a manager for the Canvas that allows the interaction with the drawer: it needs to accept input for drawing, and select already created figures. And It should allow programmable interaction such as clearing the canvas, taking screenshots and updating the position of dynamic figures.
### Progression Area: 
The progression area has two parts. One of them is the time management responsible for stopping and restarting the time of the session. The time manager should also handle time left of the turn and count the time left of the session. The other part of the progression area are the badges management which includes the creation of badges procedure and adding it to the badges panel.
### Words management: 
This manager stores the current word and in the event of submission checks if the word is correct. Another procedure is picking a new random word from a file with words.


### Structure
We tried basing our architecture on PAC principles. We will use Java, Java Swing library and if needed  other toolkits.


