Read Me - Destruct-O-Match: CIS Rendition
Pratyusha Gupta

** Note: Destruct-O-Match belongs to Neopets. I'm just borrowing it because I have no creativity. **

This game involves two modes, classic and zen mode. In classic mode, you progress through three different levels, earning points throughout the way. You can click on contiguous bricks of the same color (more than 2 bricks) can be removed. You can restart a single level as many times as you want without any loss in points earned in the last level. In order to progress to the next level, you must "beat" the level you are currently on, which basically means that you have to exhaust all possible moves.

In zen mode, the game goes on forever. Every once in a while, new bricks will form at the top of the rows. If there are no remaining moves, the colors will randomize. You will progress through levels automatically. Happy zen.

There are many cool features in this game:
	- Zen Mode, which I just explained
	- Black Brick, which gives you an additional row at the top of your bricks
	- Rainbow brick, which randomizes all non-special bricks
	- Bomb brick, which "bombs" a certain radius of bricks
	- 3 levels
	- A preview feature - if you click a brick, you will see what you're about to remove

The controls are simple:
	- Press and hold a brick to preview and release to remove
	- Click a brick to remove
	- It's exactly what you think!
	
The data structure that I used is a 2D array. The reason I chose this data structure is because I had a fixed length/width -- the game always generates to a 12x12 array of bricks. It was very important for me to be able to easily access the indexes in any order that I want and 2D arrays provided me with the abilty to easily access this. In addition, I didn't need special sorting, so other data structures would have been unnecessary.

The game status display is on the bottom of the screen. It shows the points for the level, the points for the "game" (aka previous levels), the current levels and the number of bricks remaining. This status changes once a player has defeated a level/the game.

The game logic is explained within the comments on the file.

First real Java project :)
