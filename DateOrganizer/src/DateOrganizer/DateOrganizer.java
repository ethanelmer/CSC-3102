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
        PQueue<Date> dates;
        
        String fileName = args[0];
        String sortCode = args[1];
        int sortNum = Integer.parseInt(sortCode);

        //Sort Code -2
        Comparator<Date> MonthDayYearDescending = (x, y)-> {
            if(x.getMonth() < y.getMonth())
                return 1;
            if(x.getMonth() > y.getMonth())
                return -1;
            if(x.getDay() < y.getDay())
                return 1;
            if (x.getDay() > y.getDay())
                return -1;
            if (x.getYear() < y.getYear())
                return 1;
            else   
                return -1;
        };

        //Sort Code 2
        Comparator<Date> MonthDayYearAscending = (x, y)-> {
            if(x.getMonth() > y.getMonth())
                return 1;
            if(x.getMonth() < y.getMonth())
                return -1;
            if(x.getDay() > y.getDay())
                return 1;
            if (x.getDay() < y.getDay())
                return -1;
            if (x.getYear() > y.getYear())
                return 1;
            return -1;
        };

        //Sort Code 0
        Comparator<Date> WeekDayNumberMonthName = (x, y)-> {
            if(getDayNum(x) > getDayNum(y))
                return 1;
            if(getDayNum(x) < getDayNum(y))
                return -1;
            if(!(x.getMonthName().equals(y.getMonthName())))
                return x.getMonthName().compareTo(y.getMonthName());
            if (x.getDay() > y.getDay())
                return -1;
            if (x.getYear() < y.getYear())
                return -1;
            else   
                return -1;
        };

        if(sortNum == -2){
            dates = new PQueue<Date>(MonthDayYearDescending);
        }
        else if(sortNum == -1){
        	dates = new PQueue<Date>((x,y)->-1*x.compareTo(y));
        }
        else if (sortNum == 0){
            dates = new PQueue<Date>(WeekDayNumberMonthName);
        }
        else if (sortNum == 1){
        	dates = new PQueue<Date>();
        }
        else{
            dates = new PQueue<Date>(MonthDayYearAscending);
        }

        try (FileReader reader = new FileReader(fileName)){
            Scanner fileScan = new Scanner(reader);

            while(fileScan.hasNextLine()){
                String line = fileScan.nextLine();
                String [] splitter = line.split("/");
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
        System.out.printf("Dates from %s in %s order:%n%n", fileName, sortNum);
        while(!dates.isEmpty()){
            Date current = dates.remove();
            System.out.printf("%s, %s %s, %s%n",current.getDayOfWeek(),current.getMonthName(),current.getDay(),current.getYear());
        }
    }
}