# JavaChessAI

### Authors
Saurav Kiri and Marios Petrov

## Overview
JavaChessAI is a project to expand upon a pre-existing Java chess library [chesslib](https://github.com/bhlangonijr/chesslib). We utilized the board functionalities in `chesslib` to implement an AI based on the minimax algorithm, as well as a GUI depicting the board state at each move to facilitate user engagement with playing against the computer.

## Running the Program
In order to run the program, one may simply utilize the `.jar` file on the terminal:
```
$ java -jar ChessAI.jar
```
Instructions on how to type in a move will be printed. Additionally, what difficulty computer to play against and side to play will be queried.

If for some reason, the `.jar` file is not working, this project can be opened in an IDE, and the `ChessGame.java` class can be run to initiate the game.

## Project Structure
This project contains 3 main packages in the `src` folder:
* `game` - this package contains the main driver class `ChessGame.java` through which the game is initiated.
* `minimax` - this package includes the following classes:
    + `SimpleMinimax.java` - minimax + alpha-beta pruning alone
    + `AdvancedMinimax.java` - implementation of minimax + alpha-beta pruning + quiescent searching
    + `MaterialEvaluator.java` - class that contains a method to return a numerical evaluation of a `Board` object based on the total material present.

  `minimax` also includes two interfaces, `BoardEvaluator.java` and `Strategy.java`. The former enforces some method to numerically evaluate a board for use in an AI algorithm, and the latter enforces the implementation of an AI method to find a move given a `Board`.
* `gui` - this package includes the `BoardGUI.java` class that is responsible for drawing a representation of the chessboard at each turn, and a `ChessImages.java` class that contains a `HashMap` constant for the board piece assets.

Unit testing and AI vs. AI games can be found in the `test` package, and the `resources` folder contains .pngs of each piece used to build the GUI.
    