import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class spoj4{
	
	static Scanner input = new Scanner( System.in );
	
	public static void main( String[] args ){
		
		int testCases;
		String regex = "";
		String toCheck = "";
		String toCheckNew = "";
		int testNumber;
		int start, end;
		
		testCases = input.nextInt();
		input.nextLine();
		
		for( int i = 0; i < testCases; i++ ){
		
		toCheck = input.nextLine();
		regex = input.nextLine();
		
		testNumber = input.nextInt();
		
		for( int j = 0; j < testNumber; j++ ){
			
			start = input.nextInt();
			end = input.nextInt();
			
			toCheckNew = toCheck.substring(start, end);
			
			System.out.println( "" + regexChecker( regex, toCheckNew ) );
			
			toCheckNew = "";
			
		}// End of testNumber
		
		if( i <  (testCases - 1) )
			input.nextLine();
		
		}// End of testCases
		
	}
	
	public static int regexChecker( String theRegex, String str2Check ){
		
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(str2Check);
		int count = 0;
		
		while( regexMatcher.find() )
			count++;
		
		return count;
	}
}