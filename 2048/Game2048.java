/*
 .----------------.  .----------------.  .----------------.  .----------------. 
| .--------------. || .--------------. || .--------------. || .--------------. |
| |    _____     | || |     ____     | || |   _    _     | || |     ____     | |
| |   / ___ `.   | || |   .'    '.   | || |  | |  | |    | || |   .' __ '.   | |
| |  |_/___) |   | || |  |  .--.  |  | || |  | |__| |_   | || |   | (__) |   | |
| |   .'____.'   | || |  | |    | |  | || |  |____   _|  | || |   .`____'.   | |
| |  / /____     | || |  |  `--'  |  | || |      _| |_   | || |  | (____) |  | |
| |  |_______|   | || |   '.____.'   | || |     |_____|  | || |  `.______.'  | |
| |              | || |              | || |              | || |              | |
| '--------------' || '--------------' || '--------------' || '--------------' |
 '----------------'  '----------------'  '----------------'  '----------------' 
 NAME: BiCheng Sha
 CLASS: ICS3M1 - 03
 DATE: June 10, 2014
 DESCRIPTION: A recreation of the popular game 2048 by Gabriele Cirulli. Original game can be found on Github at this permalink: gabrielecirulli.github.io/2048/
*/

import java.lang.Math;
import java.io.*;
import java.util.*;

public class Game2048{
   //=== *** Provided class variables (Don't delete this section) *** ===//
   final public static int LEFT_INPUT  = 0;
   final public static int DOWN_INPUT  = 1;
   final public static int RIGHT_INPUT = 2;
   final public static int UP_INPUT  = 3;
   
   final int GRIDSIZE = 4;
   final public static int VALUE_GRID_SETTING  = 0;
   final public static int INDEX_GRID_SETTING = 1;
   
   private String GAME_CONFIG_FILE = "game_config.txt";
   
   private Game2048GUI gui;
   
   /* position [0][0] represents the Top-Left corner and
    * position [max][max] represents the Bottom-Right corner */
   private int grid [][];
   
   //=== *** Your class variables can be added starting here *** ===//
   private final int EMPTY_SLOT = -1;
   
   private int winningLevel;  
   private long currentScore;
   private int currentLevel;
   boolean moved = false;
   int spacesTaken = 0;
   int startx, starty, startVal;
   boolean canMove;

   /**
    * Constructs Game2048 object.
    *
    * @param gameGUI The GUI object that will be used by this class.
    */   
   
   public Game2048(Game2048GUI gameGUI){
      gui = gameGUI;
      
      // TO DO:  
      // - create and initialize the grid array
      // - initialize the variables 
      //  - winningLevel (value read from text file)
      //  - currentLevel
      //  - currentScore
      // - insert the first number tile
      
      grid = new int[GRIDSIZE][GRIDSIZE];
      for (int i = 0; i < GRIDSIZE; i++){
         for (int j = 0; j < GRIDSIZE; j++){
            grid[i][j] = EMPTY_SLOT;
         }
      }
      
      try {
         BufferedReader in = new BufferedReader(new FileReader(GAME_CONFIG_FILE));
         in.readLine();
         winningLevel = Integer.parseInt(in.readLine()); //takes in winning level from game_config.txt
         
         currentLevel = 0; //initialize levl
         currentScore = 0; //initialize score
      } 
      catch (IOException iox) {
         System.out.println("Error");
      }
      
      Random gen = new Random();
      int startVal = gen.nextInt(5);
      while ((startVal != 2) && (startVal != 4)){
         startVal = (int)(gen.nextInt(5));
      }
      int startx = gen.nextInt(4);
      int starty = gen.nextInt(4);
      gui.setNewSlotBySlotValue(startx, starty, startVal);
      grid[startx][starty] = startVal;
   }
   
   /**
    * Place a new number tile on a random slot on the grid.
    * This method is called every time a key is released.
    */  
   public void newSlot(){
      // TO DO: insert a new number tile on the grid
      // Make sure to check if a new tile should be inserted first
      // (a slide or a tile combination has occurred previously)
      if (moved == true){ //If tiles were moved...
         Random gen = new Random();
         startVal = gen.nextInt(5);//Generate a number from 0 to <5
         while ((startVal != 2) && (startVal != 4)){ //Makes sure the generated number is either a 2 or 4
            startVal = (int)(gen.nextInt(5)); //If generated number is not 2 or 4, regenerate
         }
         do {
            startx = gen.nextInt(4); //create random spawn coordinate
            starty = gen.nextInt(4); //create random spawn coordinate
            if (grid[startx][starty] == EMPTY_SLOT){ //checks to make sure the slot is open to place a new tile
               gui.setNewSlotBySlotValue(startx, starty, startVal); //set new tile
            }
         } while (grid[startx][starty] != EMPTY_SLOT); //if selected location is taken, then regenerate location
         grid[startx][starty] = startVal; //updates grid to value
      }
   }
   
   /**
    * Plays the game by the direction specified by the user.     
    * This method is called every time a button is pressed
    */  
   public void play(int direction){
      int k; //comes into use when finding adjacent tiles
      moved = false; //initializing moved to be false to prevent spawn at next keyboard input
      //IF LEFT ARROW KEY WAS PRESSED
      if (direction == LEFT_INPUT){
         //BEGIN COMBINING TILES
         for (int i = 0; i < GRIDSIZE; i++){
            for (int j = 0; j < GRIDSIZE - 1; j++){
               if (grid[i][j] != EMPTY_SLOT ) { //checks for tiles
                  k = j;
                  do { 
                     k = k + 1;
                  }while (grid[i][k]== EMPTY_SLOT && k < GRIDSIZE -1); //keep increasing 'k' until there is a tile found
                  if (grid[i][k] != EMPTY_SLOT){ //if THIS slot is not empty...
                     if (grid[i][j] == grid[i][k]){ //AND if THIS slot value is equal to the adjacent slot...
                        grid[i][j] = grid[i][j] + grid[i][k]; //THEN combine to form the next greater tile value
                        grid[i][k] = EMPTY_SLOT; //Clear old tile slot
                        moved = true; //Movement has happened
                        currentScore = currentScore + grid[i][j]; //Update score
                        gui.setScore(currentScore); //Updates display of score
                        if (grid[i][j] + grid[i][k] > currentLevel){ //IF THIS tile and ADJACENT tile add up to greater than the greatest tile, then the new tile is the largest tile
                           currentLevel = grid[i][j] + grid[i][k] + 1; //THIS NEW tile is now largest (error with value always being 1 smaller... is grid[i][k] == -1?)
                        }
                        if (k < GRIDSIZE/2) { //IF 'k' is less than half of the gridsize, then...
                           j = j + 1; //Allow 'j' to increase. BECAUSE if this doesn't happen, ArrayOutOfBounds exception happens
                        }
                     }
                  }
               }
            }
         }
         //END COMBINING
         //BEGIN SHIFTING
         for (int i = 0; i < GRIDSIZE; i++){
            for (int j = 0; j < GRIDSIZE; j++){
               k = j;
               while (grid[i][k]== EMPTY_SLOT && k< GRIDSIZE -1){
                  k = k + 1;
               }
               if (grid[i][k] != EMPTY_SLOT && grid[i][j] == EMPTY_SLOT){ //IF slots on right are taken, then check to see how far left the tiles are movable
                  grid[i][j] = grid[i][k]; //Move tile as far left as possible
                  grid[i][k] = EMPTY_SLOT; //Clear moved tile
                  moved = true; //Movement is true
               }
            }//END SHIFTING
         }
      }
      //IF RIGHT ARROW KEY WAS PRESSED
      if (direction == RIGHT_INPUT){
         //BEGIN COMBINING TILES
         for (int i = 0; i < GRIDSIZE; i++){
            for (int j = GRIDSIZE - 1; j > 0; j--){
               if (grid[i][j] != EMPTY_SLOT ) { //checks for tiles
                  k = j;
                  do { 
                     k = k - 1;
                  }while (grid[i][k]== EMPTY_SLOT && k > 0); //keep increasing 'k' until there is a tile found
                  if (grid[i][k] != EMPTY_SLOT){  //if THIS slot is not empty...
                     if (grid[i][j] == grid[i][k]){ //AND if THIS slot value is equal to the adjacent slot...
                        grid[i][j] = grid[i][j] + grid[i][k];  //THEN combine to form the next greater tile value
                        grid[i][k] = EMPTY_SLOT; //Clear old tile slot
                        moved = true; //Movement has happened
                        currentScore = currentScore + grid[i][j]; //Update score
                        gui.setScore(currentScore); //Updates display of score
                        if (grid[i][j] + grid[i][k] > currentLevel){ //IF THIS tile and ADJACENT tile add up to greater than the greatest tile, then the new tile is the largest tile
                           currentLevel = grid[i][j] + grid[i][k] + 1; //THIS NEW tile is now largest
                        }
                        if (k > GRIDSIZE/2) { //IF 'k' is less than half of the gridsize, then...
                           j = j - 1; //Allow 'j' to increase. BECAUSE if this doesn't happen, ArrayOutOfBounds exception happens
                        }
                     }
                  }
               }
            }
         }
         //END COMBINING
         //BEGIN SHIFTING
         for (int i = 0; i < GRIDSIZE; i++){
            for (int j = GRIDSIZE - 1; j > 0; j--){
               k = j;
               while (grid[i][k]== EMPTY_SLOT && k > 0){
                  k = k - 1;
               }
               if (grid[i][k] != EMPTY_SLOT && grid[i][j] == EMPTY_SLOT){ //IF slots on left are taken, then check to see how far right the tiles are movable
                  grid[i][j] = grid[i][k]; //Move tile as far right as possible
                  grid[i][k] = EMPTY_SLOT; //Clear moved tile
                  moved = true; //Movement is true
               }
            }//END SHIFTING
         }
      }
      //IF UP ARROW KEY WAS PRESSED
      if (direction == UP_INPUT){
         //BEGIN COMBINING TILES
         for (int j = 0; j < GRIDSIZE; j++){
            for (int i = 0; i < GRIDSIZE -1; i++){
               if (grid[i][j] != EMPTY_SLOT ) { //checks for tiles
                  k = i;
                  do { 
                     k = k + 1;
                  }while (grid[k][j]== EMPTY_SLOT && k < GRIDSIZE -1); //keep increasing 'k' until there is a tile found
                  if (grid[k][j] != EMPTY_SLOT){ //if THIS slot is not empty...
                     if (grid[i][j] == grid[k][j]){ //AND if THIS slot value is equal to the adjacent slot...
                        grid[i][j] = grid[i][j] + grid[k][j]; //THEN combine to form the next greater tile value
                        grid[k][j] = EMPTY_SLOT; //Clear old tile slot
                        moved = true; //Movement has happened
                        currentScore = currentScore + grid[i][j]; //Update score
                        gui.setScore(currentScore); //Updates display of score
                        if (grid[i][j] + grid[k][j] > currentLevel){ //IF THIS tile and ADJACENT tile add up to greater than the greatest tile, then the new tile is the largest tile
                           currentLevel = grid[i][j] + grid[k][j] + 1; //THIS NEW tile is now largest
                        }
                        if (k < GRIDSIZE/2) { //IF 'k' is less than half of the gridsize, then...
                           i = i + 1; //Allow 'j' to increase. BECAUSE if this doesn't happen, ArrayOutOfBounds exception happens
                        }
                     }
                  }
               }
            }
         }
         //END COMBINING
         //BEGIN SHIFTING
         for (int j = 0; j < GRIDSIZE; j++){
            for (int i = 0; i < GRIDSIZE - 1; i++){
               k = i;
               while (grid[k][j]== EMPTY_SLOT && k< GRIDSIZE -1){
                  k = k + 1;
               }
               if (grid[k][j] != EMPTY_SLOT && grid[i][j] == EMPTY_SLOT){ //IF slots on bottom are taken, then check to see how far up the tiles are movable
                  grid[i][j] = grid[k][j]; //Move tile as far up as possible
                  grid[k][j] = EMPTY_SLOT; //Clear moved tile
                  moved = true; //Movement is true
               }
            }//END SHIFTING
         }
      }
      //IF DOWN ARROW KEY WAS PRESSED
      if (direction == DOWN_INPUT){
         //BEGIN COMBINING TILES
         for (int j = 0; j < GRIDSIZE; j++){
            for (int i = GRIDSIZE - 1; i > 0; i--){
               if (grid[i][j] != EMPTY_SLOT ) { //checks for tiles
                  k = i;
                  do { 
                     k = k - 1;
                  }while (grid[k][j]== EMPTY_SLOT && k > 0); //keep increasing 'k' until there is a tile found
                  if (grid[k][j] != EMPTY_SLOT){ //if THIS slot is not empty...
                     if (grid[i][j] == grid[k][j]){ //AND if THIS slot value is equal to the adjacent slot...
                        grid[i][j] = grid[i][j] + grid[k][j]; //THEN combine to form the next greater tile value
                        grid[k][j] = EMPTY_SLOT; //Clear old tile slot
                        moved = true; //Movement has happened
                        currentScore = currentScore + grid[i][j]; //Update score
                        gui.setScore(currentScore); //Updates display of score
                        
                        if (grid[i][j] + grid[k][j] > currentLevel){ //IF THIS tile and ADJACENT tile add up to greater than the greatest tile, then the new tile is the largest tile
                           currentLevel = grid[i][j] + grid[k][j] + 1; //THIS NEW tile is now largest
                        }
                        if (k < GRIDSIZE/2) { //IF 'k' is less than half of the gridsize, then...
                           i = i - 1; //Allow 'j' to increase. BECAUSE if this doesn't happen, ArrayOutOfBounds exception happens
                        }
                     }
                  }
               }
            }
         }
         //END COMBINING
         //BEGIN SHIFTING
         for (int j = 0; j < GRIDSIZE; j++){
            for (int i = GRIDSIZE - 1; i > 0; i--){
               k = i;
               while (grid[k][j]== EMPTY_SLOT && k > 0){
                  k = k - 1;
               }
               if (grid[k][j] != EMPTY_SLOT && grid[i][j] == EMPTY_SLOT){ //IF slots on top are taken, then check to see how far down the tiles are movable
                  grid[i][j] = grid[k][j]; //Move tile as far down as possible
                  grid[k][j] = EMPTY_SLOT; //Clear moved tile
                  moved = true; //Movement is true
               }
            }//END SHIFTING
         }
      }
      gui.setGridByValue(grid); //updates game board
      
      if (currentLevel >= winningLevel){ //If largest tile is greater or equals to the winning tile size...
         gui.showGameWon(); //popup game won
      }
      
      for (int i = 0; i < GRIDSIZE; i++){
         for (int j = 0; j < GRIDSIZE; j++){
            if (grid[i][j] != EMPTY_SLOT){
               spacesTaken++; //Counts the amount of slots taken
            } 
         }
      }
      if (spacesTaken == 16){ //if the board is full...
         int i;
         int j;
         i = 0;
         do{
            canMove = false; //initialize canMove to be false
            j = 0;
            do{
               j++;
            } while ((grid[i][j-1] != grid[i][j]) && j < GRIDSIZE - 1);
            if (grid[i][j-1] == grid[i][j]){ //if tiles adjacent to each other HORIZONTALLY are equal size...
               canMove = true; //canMove is true
            }
            i++;
         } while (canMove == false && i < GRIDSIZE - 1);
        
         if (canMove == false){ //if canMove is false horizontally, the check vertically
            j = 0;
            do{
               canMove = false; //reinitialize canMove to false
               i = 0;
               do{
                  i++;
               } while ((grid[i-1][j] != grid[i][j]) && i < GRIDSIZE - 1);
               if (grid[i-1][j] == grid[i][j]){ //if tiles adjacent to each other VERTICALLY are equal size...
                  canMove = true; //canMove is true
               }
               j++;
            } while (canMove == false && j < GRIDSIZE - 1);
         }
         if (canMove == false){ //if canMove is false in both HORIZONTAL and VERTICAL, then...
            gui.showGameOver(); //popup game lost
         }
      } 
      else { //if the amount of slots taken up is not 16, then reset counter for next time
         spacesTaken = 0;
      }
   }//END PLAY
}
