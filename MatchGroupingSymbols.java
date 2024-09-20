/*
 * Name: Match Grouping Symbols
 * Author: Leah Boalich
 * Date: September 20, 2024
 * Assignment: Chapter 20 Exercise 11
 * Description: This program accepts a java source code file passed as a command line prompt and checks to make sure the file has matched pairs of grouping symbols.  This program assumes the grouping symbols are not used in comments.
 */

/* Imports */
import java.io.*;
import java.util.*;

/* Main Module to test if Java Source Code file has correct pairs of grouping symbols */
public class MatchGroupingSymbols {
    //Thows exception if incorrect file name passed as argument
    public static void main(String[] args) throws Exception {  
        //Check that one parameter was passed from command line
        if (args.length != 1) {
            //Display error message
            System.out.println("Usage: java MatchGroupingSymbols sourcefile");
            //Exit the program
            System.exit(1);
        }

        //Instantiate the inputed file name as a File
        File sourceFile = new File(args[0]);
        //Check that source file exists
        if (!sourceFile.exists()) {
            //Display error message
            System.out.println("Source file " + args[0] + " does not exist");
            //Exit the program
            System.exit(2);
        }

        //Create file scanner
        try (Scanner input = new Scanner(sourceFile)) {
            //Determine if grouping symbols match
            boolean matched = hasMatchedGroupingSymbols(input);
            //If grouping symbols match
            if (matched) { 
                System.out.println("The file " + args[0] + " does have matched grouping symbols");
            }
            //If grouping symbols do not match
            else { 
                System.out.println("The file " + args[0] + " does not have matched grouping symbols");
            }
        }         
    }

    /* Method to check if file has correct grouping pairs */
    public static boolean hasMatchedGroupingSymbols(Scanner input) {
        //Create openingStack to store the opening half of grouping symbolds
        Stack<String> openingStack = new Stack<>();
        //Assume file has matching grouping symbols
        boolean matched = true;
        //When there is a line that has not been read and grouping error not found
        while (input.hasNextLine() && matched) {
            //Get next line and remove all characters other than (){}[]
            String currentLine = input.nextLine().replaceAll("[^\\(\\)\\{\\}\\[\\]]", "");
            //Loop over characters
            for (int i = 0; i < currentLine.length(); i++) {
                //Convert character at current index to String
                String currentChar = Character.toString(currentLine.charAt(i));
                //Check if character equal to (,{, or [
                if (currentChar.compareTo("[") == 0 || currentChar.compareTo("{") == 0 || currentChar.compareTo("(") == 0) {
                    //Add character to the openingStack
                    openingStack.push(currentChar);
                }
                //Character equal to ),}, or ]
                else {
                    //Make sure stack isn't empty
                    if (openingStack.isEmpty()) {
                        //No match
                        matched = false;
                        break;
                    }
                    //Otherwise, get character at top of stack
                    String poppedCharacter = openingStack.pop();
                    //Check if current character is a grouping match
                    //Current character is ] 
                    if (currentChar.compareTo("]") == 0) {
                        //Popped character must be [
                        if (poppedCharacter.compareTo("[") != 0) {
                            //Popped character not equal to [, update matched to false and break
                            matched = false;
                            break;
                        }
                    }
                    //Current character is }
                    if (currentChar.compareTo("}") == 0) {
                        //Popped character must be {
                        if (poppedCharacter.compareTo("{") != 0) {
                            //Popped character not equal to {, update matched to false and break
                            matched = false;
                            break;
                        }
                    }
                    //Current character is )
                    if (currentChar.compareTo(")") == 0) {
                        //Popped character must be (
                        if (poppedCharacter.compareTo("(") != 0) {
                            //Popped character not equal to (, update matched to false and break
                            matched = false;
                            break;
                        }
                    }
                }    
            }     
        }
        //No more characters to process, make sure stack is empty
        if (!openingStack.isEmpty()) {
            matched = false;
        }
        //Return if file had matched grouping symbols
        return matched; 
    }    
}
