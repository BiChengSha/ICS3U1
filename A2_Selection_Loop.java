/*
 PROGRAM NAME: SHAPE.JAVA
 NAME: BICHENG SHA
 CLASS: ICS3U1-03
 DATE: APRIL 3, 2014
 DESCRIPTION: USER SELECTS ONE SHAPE OUT OF FIVE, ENTERS ITS DIMENSIONS AND THE PROGRAM WILL PRINT OUT THE SHAPE.
 AFTER DRAWING, PROGRAM WILL ASK IF THE USER WISHES TO REPEAT THE PROGRAM.
 WHEN USER ENTERS A NEGATIVE ANSWER ('n' OR 'N'), PROGRAM WILL TERMINATE AND PRINT OUT THE AMOUNT
 OF TIMES EACH SHAPE WAS SELECTED AND DRAWN
 */

import java.util.*;
public class Shape{
  public static void main (String [] args){
    
    //VARIABLE DECLARATIONS
    Scanner input = new Scanner(System.in);
    
    final int MAX = 20; //MAXIMUM DIMENSION VALUE
    final int MIN = 1; //MINIMUM DIMENSION VALUE
    int choice; //USER CHOICE FROM LIST
    int c1 = 0; //COUNTER
    int c2 = 0; //COUNTER
    int c3 = 0; //COUNTER
    int c4 = 0; //COUNTER
    int c5 = 0; //COUNTER
    int width, height; //DIMENSIONS OF SHAPES
    char rep; //USER INPUT TO REPEAT OR EXIT PROGRAM
    boolean exit = false; //DECISION TO REPEAT OR EXIT PROGRAM; DEFAULT IS FALSE
    
    //INTRODUCTION
    System.out.println("Welcome to the Shape Generator: \n");
    
    do {
      System.out.println("This program draws the following shapes: ");
      System.out.println("1) Horizontal Line");
      System.out.println("2) Vertical Line");
      System.out.println("3) Rectangle");
      System.out.println("4) Left slant right angle triangle");
      System.out.println("5) Isosceles triangle\n\n");
      //USER SELECTION AND INPUT
      System.out.println("Enter your choice (1-5): ");
      choice = input.nextInt();
      
      //VALIDATES USER CHOICE FOR WHICH PROGRAM TO RUN
      while (!((choice >= 1) && (choice <= 5))){
        System.out.print("Invalid choice. Choice must be between 1 and 5: ");
        choice = input.nextInt();
      }
      
      //RUNS SUB-PROGRAM BASED ON USER CHOICE
      switch (choice) {
        
        case 1: //CHOICE #1: HORIZONTAL LINE
          System.out.println("Horizontal line selected: ");
          System.out.print("\nEnter width of line: ");
          width = input.nextInt();
          //VALIDATES DIMENSION IS WITHIN RANGE
          while ((width > MAX) || (width < MIN)){
            System.out.println("Invalid dimension. Must be between 1 and 20.");
            System.out.print("Enter again: ");
            width = input.nextInt();
          }
          System.out.println("\n\nHere is the horizontal line: ");
          for (int i = 0; i < width; i = i + 1){
            System.out.print("*");
          }
          c1 = c1 + 1; //COUNTS AMOUNT OF TIMES THE SUB-PROGRAM WAS RUN
          break;
          
        case 2: //CHOICE #2: VERTICAL LINE
          System.out.println("Vertical line selected: ");
          System.out.print("\nEnter height of line: ");
          height = input.nextInt();
          //VALIDATES DIMENSION IS WITHIN RANGE
          while ((height > MAX) || (height < MIN)){
            System.out.println("Invalid dimension. Must be between 1 and 20.");
            System.out.print("Enter again: ");
            height = input.nextInt();
          }
          System.out.println("\nHere is the vertical line:");
          for (int i = 0; i < height; i = i + 1){
            System.out.println("*");
          }
          c2 = c2 + 1; //COUNTS AMOUNT OF TIMES THE SUB-PROGRAM WAS RUN
          break;
          
        case 3: //CHOICE #3: RECTANGLE
          System.out.println("Rectangle selected: ");
          System.out.print("\nEnter height of rectangle: ");
          height = input.nextInt();
          //VALIDATES DIMENSIONS ARE WITHIN RANGE
          while ((height > MAX) || (height < MIN)){
            System.out.println("Invalid dimension. Must be between 1 and 20.");
            System.out.print("Enter again: ");
            height = input.nextInt();
          }
          System.out.print("Enter width of rectangle: ");
          width = input.nextInt();
          while ((width > MAX) || (width < MIN)){
            System.out.println("Invalid dimension. Must be between 1 and 20.");
            System.out.print("Enter again: ");
            width = input.nextInt();
          }
          System.out.println("\nHere is the rectangle: ");
          for(int i = 0; i < height; i = i + 1){
            for (int j = 0; j < width; j = j + 1){
              System.out.print("*");
            }
            System.out.print("\n");
          }
          c3 = c3 + 1; //COUNTS AMOUNT OF TIMES THE SUB-PROGRAM WAS RUN
          break;
          
        case 4: //CHOICE #4: LEFT SLANT RIGHT TRIANGLE
          System.out.println("Left slant right triangle selected: ");
          System.out.print("\nEnter the height of the triangle: ");
          height = input.nextInt();
          //VALIDATES DIMENSION IS WITHIN RANGE
          while ((height > MAX) || (height < MIN)){
            System.out.println("Invalid dimension. Must be between 1 and 20.");
            System.out.print("Enter again: ");
            height = input.nextInt();
          }
          System.out.println("\nHere is the left slant right angle triangle: ");
          int i, j, k; //VARIABLES FOR BOTH THIS TRIANGLE AND ISOSCELES TRIANGLE
          for (i = height; i > 0; i = i - 1){
            for (j = 0; j < i; j = j + 1){
              System.out.print(" ");
            }
            for (k = 0; k < (height - j + 1); k = k + 1){
              System.out.print("*");
            }
            System.out.print("\n");
          }
          c4 = c4 + 1; //COUNTS AMOUNT OF TIMES THE SUB-PROGRAM WAS RUN
          break;
          
        case 5: //CHOICE #5: ISOSCELES TRIANGLE
          System.out.println("Isosceles triangle selected: ");
          System.out.print("\nEnter the height of the triangle: ");
          height = input.nextInt();
          //VALIDATES DIMENSION IS WITHIN RANGE
          while ((height > MAX) || (height < MIN)){
            System.out.println("Invalid dimension. Must be between 1 and 20.");
            System.out.print("Enter again: ");
            height = input.nextInt();
          }
          //DRAWS SHAPE
          System.out.println("\nHere is the isosceles triangle: ");
          for (i = 0; i < height; i = i + 1){
            for (j = 0; j <= (height -i); j = j + 1){
              System.out.print(" ");
            }
            for (k = 0; k <= (2*i); k = k + 1){
              System.out.print("*");
            }
            System.out.println();
          }
          c5 = c5 + 1; //COUNTS AMOUNT OF TIMES THE SUB-PROGRAM WAS RUN
          break;
      }
      
      input.nextLine(); //CLEARS CACHE
      
      
      //ASKS USER FOR REPEAT AND VALIDATES ANSWER
      do {
        System.out.print("\n\nWould you like to draw another one? (y/n)? ");
        rep = input.nextLine().charAt(0);
        if ((rep == 'y')||(rep == 'Y')){
          exit = false;
        } 
        else if ((rep == 'n')||(rep == 'N')) {
          exit = true;
        } 
        else {
          System.out.println("Invaild input. Enter y/n.");
        }        
      } while ((rep != 'y')&&(rep != 'Y') && (rep != 'n')&&(rep != 'N'));
    } while (exit == false);
    
    //SUMMARY OF SHAPES DRAWN
    System.out.println("\n\n\nHere is a summary of the shapes that are drawn: \n");
    System.out.println("Horzontal line: \t\t\t\t" + c1);
    System.out.println("Vertical line: \t\t\t\t" + c2);
    System.out.println("Rectangle: \t\t\t\t" + c3);
    System.out.println("Left slant right angle triangle: \t\t" + c4);
    System.out.println("Isosceles triangle: \t\t\t\t" + c5);
    System.out.println("\n\nThank you for using Shape Generator. Good Bye!");
  }
}
