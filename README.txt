MathDoku - Thomas Hoad (30914515)

Guide:
1) Make sure you have a system with a working version of Java and JavaFX in order to run the program.
2) Run command line from src file location.
3) Run Main class (this will differ on the version of Java - see below.)
4) An application should appear that displays a GUI. To load a grid, you must load a game.

To run from command line (Java 8 and including JavaFX):
    javac Main.java
    java Main

To run from command line (Java 9+ or excluding JavaFX):
    javac --module-path="directory of lib folder for javafx" --add-modules=ALL-MODULE-PATH Main.java
    java --module-path="directory of lib folder for javafx" --add-modules=ALL-MODULE-PATH Main

Sections completed:
1) Skeleton GUI
2) The Grid
3) Cell Completion
4) Win Mistake Detection
5) Clearing, Undo, Redo
6) Loading Files

Extensions completed:
7) Font Sizes
8) Winning Animation

Extensions attempted:
9) MathDoku Solver