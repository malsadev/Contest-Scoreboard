/*
 * Program name: ScoreboardMethods
 * Purpose: Has several methods that are used in the main class. It enhances code readability in the main class
 * Author: Mustafa Al-Sakkaf
 * Date: April. 9, 2021
 */





public class MA_ScoreboardMethods {
    /*
     * Method name : validSubmission
     * Purpose : to check whether a submission is valid, returns a boolean value of true or false
     * Parameters : team id, time it took to solve problem, the problem id, and the result. Remaining arguments are the restrictions like number of problems and  number of teams
     */
	public static boolean validSubmission(int teamId, int timeStamp, int problemNum, char judgement, int numTeams, int numProblems, int maxTime) {
		//First if- statement checks if the numerical values fall within acceptable ranges
		if ((teamId >=1 && teamId <= numTeams) && (timeStamp > 0 && timeStamp <= maxTime)) {
			//Second-if statement checks if the judgement is valid. Could only be Y or N
			if ((problemNum >=1 && problemNum <= numProblems) && (judgement == 'Y' || judgement == 'N')) {
				return true;
			}
		 }
		//returns false if the first if-statements fails
		return false; 
	}
	
	
	/*
	 * Method name : numSolved
	 * Purpose : based on time stamp, this method returns an array which tells us the number of problems each team has solved
	 * Parameters : only accepts the timesArray as it holds values only for successful submissions
	 */
	public static int[] numSolved(int[][] timesArray) {
		//initialize an array to hold number od problems solved per team
		int[] numSolved = new int[timesArray.length];
		//loops through timesArray which checks if the time stamp for a specific problem is more than zero
		for (int i = 0; i < timesArray.length; i++) {
			
			for (int j = 0; j < timesArray[i].length; j++) {
				
				if (timesArray[i][j] > 0 ) {
					//the number of problems solves increases for appropriate team each time the condition is true
					numSolved[i]++;
				}
			}
		}		
		return numSolved;
 	}
	
	
	/*
	 * Method name : timeCalc
	 * Purpose : This method returns an array which holds the total time taken for each team to finish the contest per the contest rules
	 * Parameters : as this calcualtes the times it took each team, it checks values from both the submission array and the times array before assigning the total time to an array
	 */
	public static int[] timeCalc(int[][] submissionsArray, int[][] timesArray, int timePenalty) {
		//initialzed an array that will hold values for the number of teams
		int[] totalTimes = new int[submissionsArray.length];
		//for loop to assign values to totalTimes
		for (int i = 0; i < totalTimes.length; i++) {
			
			for (int j = 0; j < timesArray[i].length; j++) {
				//Calculates base time from the timesArray
				totalTimes[i] += timesArray[i][j]; 
				//This if-statement checks if extra time should be added to base time...if a problem is solved but there were at least one unsucessuful submission  
				if (timesArray[i][j] > 0 && submissionsArray[i][j] > 1) {
					totalTimes[i] += timePenalty * (submissionsArray[i][j] - 1);					
				}
			}	
		}
		return totalTimes;
	}
	/*
	 * Method name : prepScoreboard
	 * Purpose : prints out contest title and column headings with appropriate spacing
	 * Parameters : Accepts a string (contest title) and the num of problems to print out the required number of columns
	 */
	public static void prepScoreboard(String contestTitle, int numProblems) {
		
		System.out.println(contestTitle + "\n");
		//formatted output and some whitespace using printf
		System.out.printf("%-16s Slv/Time" , "Team");
		//prints the column headings for the the problem numbers
		for (int i = 0; i < numProblems; i++) {
			System.out.printf("%5s", "P");
			System.out.print(i +1);
			
		}
		//two blank lines
		System.out.println("\n");
		
	}
	/*
	 * Method name : printSubmissionInfo
	 * Purpose : this prints out the submission details for any team when given the id 
	 * Parameters :"accepts integer values for the most part but the last two are arrays since each team has the possibility of submitting multiple problems 
	 */
	public static void printSubmissionInfo (String teamName, int numSolved, int totalTime, int[] attempts, int[] times) {
		//This changes performance information from integers to a String object data type
		String performanceIndicator = String.valueOf(numSolved) + "/" + String.valueOf(totalTime);
		//Prints performance info under the Slv/Time column heading 
		System.out.printf("%-18s%7s", teamName,  performanceIndicator);
		String numSubmissions;
		//This if-Statement checks whether to print Y or N in front of attempts for the first column
		if (times[0] > 0 ) {
			numSubmissions = "Y/" + String.valueOf(attempts[0]);
		    System.out.printf("%6s", numSubmissions);
		} else {
			numSubmissions = "N/" + String.valueOf(attempts[0]);
			System.out.printf("%6s", numSubmissions);
		}
		 
		//This loop is here to print the number of attempts under each problem column
		for (int i = 1; i < attempts.length ; i++) {
			
			if ((i + 1) >= 10 && (i + 1) < 100) {
				//This is to make sure output is correctly formatted under the problem numbers 10 and above
				System.out.print(" ");
				
			}
			//This if-Statement checks whether to print Y or N in front of attempts
			if (times[i] > 0 ) {
				numSubmissions = "Y/" + String.valueOf(attempts[i]);
			    System.out.printf("%6s", numSubmissions);
			} else {
				numSubmissions = "N/" + String.valueOf(attempts[i]);
				System.out.printf("%6s", numSubmissions);
			}
		}
		System.out.println();
	}	
}
	


