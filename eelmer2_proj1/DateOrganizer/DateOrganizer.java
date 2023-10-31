package DateOrganizer;


/**
 * A testbed for a binary heap implementation of a priority queue using 
 * various comparators to sort Gregorian dates
 * @author Duncan, Ethan Elmer
 * @see Date, PQueueAPI, PQueue
 * <pre>
 * Date: 09-25-2023
 * Course: csc 3102
 * File: DateOrganizer.java
 * Instructor: Dr. Duncan
 * </pre>
 */

import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.util.Comparator; 

public class DateOrganizer{
    
    /**
     * Gives the integer value equivalent to the day of the
     * week of the specified date 
     * @param d a date on the Gregorian Calendar
     * @return 0->Sunday, 1->Monday, 2->Tuesday, 3->Wednesday,
     * 4->Thursday, 5->Friday, 6->Saturday; otherwise, -1 
     */
    public static int getDayNum(Date d){
    	//Implement this method
    	String dayString = d.getDayOfWeek();
        switch (dayString){
            case ("Sunday"): return 0;
            case ("Monday"): return 1;
            case ("Tuesday"): return 2;
            case ("Wednesday"): return 3;
            case ("Thursday"): return 4;
            case ("Friday"): return 5;
            case ("Saturday"): return 6;
            default: return 0;
        }
    }
	public static void main(String[] args) throws IOException, PQueueException{
        String usage = "DateOrganizer <date-file-name> <sort-code>%n";
        usage += "sort-code: -2 -month-day-year%n";
        usage += "           -1 -year-month-day%n";
        usage += "            0 +weekDayNumber+monthName+day+year%n";
        usage += "            1 +year+month+day%n";
        usage += "            2 +month+day+year";
        if (args.length != 2){
            System.out.println("Invalid number of command line arguments");
            System.out.printf(usage+"%n%");
            System.exit(1);
        }
        //Complete the implementation of this method
        PQueue<Date> dates;
        
        String fileName = args[0];
        String sortCode = args[1];
        int sortNum = Integer.parseInt(sortCode);
        String sortCase;

        //Sort Code -2 : Sort by Month, Day, Year, Descending Order
        Comparator<Date> MonthDayYearDescending = (object1, object2)-> {
            if(object1.getMonth() < object2.getMonth()) {
                return 1;
            }
            if(object1.getMonth() > object2.getMonth()) {
                return -1;
            }
            if(object1.getDay() < object2.getDay()) {
                return 1;
            }
            if (object1.getDay() > object2.getDay()) {
                return -1;
            }
            if (object1.getYear() < object2.getYear()) {
                return 1;
            }
            else {   
                return -1;
            }
        };

        //Sort Code 2 : Sort by Month, Day, Year, Ascending Order
        Comparator<Date> MonthDayYearAscending = (object1, object2)-> {
            if(object1.getMonth() > object2.getMonth()) {
                return 1;
            }
            if(object1.getMonth() < object2.getMonth()) {
                return -1;
            }
            if(object1.getDay() > object2.getDay()) {
                return 1;
            }
            if (object1.getDay() < object2.getDay()) {
                return -1;
            }
            if (object1.getYear() > object2.getYear()) {
                return 1;
            }
            return -1;
        };

        //Sort Code 0 : Sort by Weekday Number, Month Name, Day, Year (Descending) Order 
        Comparator<Date> WeekDayNumberMonthName = (object1, object2)-> {
            if(getDayNum(object1) > getDayNum(object2)) {
                return 1;
            }
            if(getDayNum(object1) < getDayNum(object2)) {
                return -1;
            }
            if(!(object1.getMonthName().equals(object2.getMonthName()))) {
            	int returnValue = object1.getMonthName().compareTo(object2.getMonthName());
                return returnValue;
            }
            if (object1.getDay() > object2.getDay()) {
                return 1;
            }
            if (object1.getDay() < object2.getDay()) {
                return -1;
            }
            if (object1.getYear() > object2.getYear()) {
                return 1;
            }
            else {   
                return -1;
            }
        };

        if(sortNum == -2){
            dates = new PQueue<Date>(MonthDayYearDescending);
            sortCase = "-month-day-year";
        }
        else if(sortNum == -1){
        	dates = new PQueue<Date>((x,y)->-1*x.compareTo(y));
        	sortCase = "-year-month-day";
        }
        else if (sortNum == 0){
            dates = new PQueue<Date>(WeekDayNumberMonthName);
            sortCase = "+weekDayNumber+monthName+day+year";
        }
        else if (sortNum == 1){
        	dates = new PQueue<Date>();
        	sortCase = "+year+month+day";
        }
        else {
            dates = new PQueue<Date>(MonthDayYearAscending);
            sortCase = "+month+day+year";
        }
        
        //Handles the opening and processing of the input file
        try (FileReader reader = new FileReader(fileName)){
            Scanner fileScan = new Scanner(reader);
            while(fileScan.hasNextLine()){
                String lineInput = fileScan.nextLine();
                String [] splitter = lineInput.split("/");
                int month = Integer.parseInt(splitter[0]);
                int day = Integer.parseInt(splitter[1]);
                int year = Integer.parseInt(splitter[2]);
                Date date = new Date(month,day,year);
                dates.insert(date);
            }
            fileScan.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        
        //Processes the exact file location so that txtFileName just contains the string representing the .txt file
        int LastSplitIndex = fileName.lastIndexOf("\\")+1;
        String txtFileName = fileName.substring(LastSplitIndex);
        System.out.printf("Dates from %s in %s Order:%n", txtFileName, sortCase);
        
        //Handles the printing of the dates in order
        while(!dates.isEmpty()){
            Date current = dates.remove();
            String dayOfWeek = current.getDayOfWeek();
            String monthName = current.getMonthName();
            int day = current.getDay();
            int year = current.getYear();
            
            System.out.printf("%s, %s %s, %s%n",dayOfWeek,monthName,day,year);
        }
    }
}