/*
 * Program Name : MA_Scoreboard.java
 * Purpose : Output a scoreboard-style display containing team scores and data read from a text file.
 * Author : Mustafa Al-Sakkaf
 * Data : March. 22, 2021
 */

//Import useful libraries from the java utility package such as the scanner and file class

//Create the needed file objects to pass as arguments to scanner object

//Read the title/description of the contest from the submissions file and store it in a constant String

//Read the contest restrictions (number of teams, contest duration etc) and store them in appropriate constant variables

//Create two 2D Arrays to hold teamId/problemId and teamId/timeStamp, respectively;

//Declaring variables of int type (as tokens) to read data from submissions file

//A while loop to read data from submissions file and assign valid data to submissionsArray and timesArray

	//Reading submission data into appropriate variables eventually passed as arguments to isValidSubmission non-void method.
	
	//if-else statement to check validity of submission and assign valid data to submissions array as well ass times array if appropriate conditions are satisfied.
    
    //keeps track of the number of  valid submissions
    
    //Checks whether the attempt is successful and then assigns the time stamp to the timesArray

    //Keeps track of the number of invalid submissions

//Reads from the teams file the team names and assigns them to an appropriate array, using a for loop

//This array catches the return value of the timeCalc method defined in the methods class

//Calling void method to prepare the title and the headings for the scoreboard




//Import useful libraries from the java utility package such as the scanner and file class
import java.util.Scanner;
import java.io.*;
public class MA_Scoreboard {

	public static void main(String[] args) {
		//This is the beginning of a try-catch block. It allows for the program to compile even if the required files are not found where specified in the code.
		try {
			//Create the needed file objects to pass as arguments to scanner object
			File myFile1 = new File("submissions.txt");
			File myFile2 = new File("teams.txt");
			Scanner fileReader1 = new Scanner(myFile1);
			Scanner fileReader2 = new Scanner(myFile2);
			
			//Declaring constant variables to hold the data regarding the parameters of the contests
			final String CONTEST_TITLE;
			final int NUM_TEAMS;
			final int NUM_PROBLEMS;
			final int CONTEST_DURATION;
			final int TIME_PENALTY;
			
			//Read the title/description of the contest from the submissions file and store it in a constant String
			if (fileReader1.hasNextLine()) {
				String token = fileReader1.nextLine();
				CONTEST_TITLE = token;
			}
			else { 
				CONTEST_TITLE = "NO CONTEST TITLE READ";
			}
			
			//Read the contest restrictions (number of teams, contest duration etc) and store them in appropriate constant variables
			NUM_TEAMS = fileReader1.nextInt();
			NUM_PROBLEMS = fileReader1.nextInt();
			CONTEST_DURATION = fileReader1.nextInt();
			TIME_PENALTY = fileReader1.nextInt();
			
			//Create two 2D Arrays to hold teamId/problemId and teamId/timeStamp, respectively;
			int[][] submissionsArray = new int[NUM_TEAMS][NUM_PROBLEMS];
	 		int[][] timesArray = new int[NUM_TEAMS][NUM_PROBLEMS];
			String[] teamsArray = new String[NUM_TEAMS];
			
	 		//Declaring variables of int type (as tokens) to read data from submissions file
			int teamId;
			int timeStamp;
			int problemNum;
			char judgement;
			
			//Initializing a counter to keep track of invalid submissions read from the file.
			int invalidSubmissionCounter = 0;
			int validSubmissionCounter = 0;
			
			fileReader1.nextLine();
		
			//A while loop to read data from submissions file and assign valid data to submissionsArray and timesArray
			while (fileReader1.hasNextInt()) {
				
				//Reading submission data into appropriate variables eventually passed as arguments to isValidSubmission non-void method.
				teamId = fileReader1.nextInt();
				timeStamp = fileReader1.nextInt();
				problemNum = fileReader1.nextInt();
				judgement = fileReader1.nextLine().charAt(1);
				
				//if-else statement to check validity of submission and assign valid data to submissions array as well ass times array if appropriate conditions are satisfied
				if (MA_ScoreboardMethods.validSubmission(teamId, timeStamp, problemNum, judgement, NUM_TEAMS, NUM_PROBLEMS, CONTEST_DURATION)) {
					submissionsArray[teamId - 1][problemNum - 1]++;
					//keeps track of the number of  valid submissions
					validSubmissionCounter++;
					
					//Checks whether the attempt is successful and then assigns the time stamp to the timesArray
					if (judgement == 'Y') {
						timesArray[teamId - 1][problemNum - 1] = timeStamp;
					}
				}   else {
					//Keeps track of the number of invalid submissions
					invalidSubmissionCounter++;
				}
			}
			
			
			//Reads from the teams file the team names and assigns them to an appropriate array, using a for loop
			for (int i = 0; i < teamsArray.length; i++) {
				teamsArray[fileReader2.nextInt() - 1] = fileReader2.nextLine().trim();;
			}
			
			//This array catches the return value of the timeCalc method defined in the methods class
			int[] totalTimes = MA_ScoreboardMethods.timeCalc(submissionsArray, timesArray, TIME_PENALTY);
			
			//Calling void method to prepare the title and the headings for the scoreboard
			MA_ScoreboardMethods.prepScoreboard(CONTEST_TITLE, NUM_PROBLEMS);
			
			//This array is assigned the return value of the numSolved method defined in the methods class. It is an array with the number of problems solved for each team
			int[] numSolved = MA_ScoreboardMethods.numSolved(timesArray);
			
			//This is a loop that calls the printSubmissionInfo for each team, as needed. Refer to the method defined in the methods class for more details
			for (int i = 0; i < NUM_TEAMS; i++) {
			    MA_ScoreboardMethods.printSubmissionInfo(teamsArray[i], numSolved[i], totalTimes[i], submissionsArray[i], timesArray[i]);
			    
			}
			
			//This reports to the user the number of valid and invalid submissions
		    System.out.println("\n" + validSubmissionCounter + " valid submission(s) were processed.");
		    System.out.println(invalidSubmissionCounter + " submission(s) were invalid and ignored.");
		    
		    
		    //Closes the two scanner objects to avoid resource leaks
		    fileReader1.close();
		    fileReader2.close();
		}
		//This catches the FileNotFoundException and reports to the user that either submission.txt or teams.txt can not be found where already specified during file object instantiation
		catch (FileNotFoundException ex) {
			System.out.println("Exception was caught, message is: " + ex.getMessage() + "\nPlease make sure both submissions.txt and teams.txt are located in this project's folder and try running the program again");
		}
		
	}

}
