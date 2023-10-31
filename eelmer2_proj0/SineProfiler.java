/**
 * A program to profile sine approximation algorithms that use
 * the Taylor series expansion of the sine function: 
 * sine(x) = x - x^3/3! + x^5/5! - x^7/7! + x^9/9! .....   
 * @author William Duncan, Ethan Elmer
 * @see SineUtil
 * <pre>
 * Date: 09-04-2023
 * Course: csc 3102
 * Project # 0
 * Instructor: Dr. Duncan
 * </pre>
 */

import java.util.Scanner;

public class SineProfiler
{
	public static void main(String[] args) 
	{
        final Scanner myScanner = new Scanner(System.in);
        
        System.out.println("Enter the angle in radians ->");
        double x = myScanner.nextDouble();
        
        double alpha = SineUtil.naiveSine(x, 100);
		System.out.printf("naive-sine(%.4f) = %.4f\n", x, alpha);
		
        alpha = SineUtil.fastSine(x, 100);
        System.out.printf("fast-sine(%.4f) = %.4f\n", x, alpha);
        
        System.out.println("n\t|Naive Time(us)\t|Fast Time(us)");
        System.out.println("----------------------------------------");
        
        for (int i=1000; i<=15000; i += 1000) 
        {
        	double naiveStart = System.nanoTime();
        	SineUtil.naiveSine(x, i);
        	double naiveElapsedMicroseconds = (System.nanoTime()-naiveStart)/1000;
        	
        	double fastStart = System.nanoTime();
        	SineUtil.fastSine(x, i);
        	double fastElapsedMicroseconds = (System.nanoTime()-fastStart)/1000;
        	
        	double nTerms = i;
        	System.out.printf("%.0f\t|%.2f\t|%.2f\n", nTerms,naiveElapsedMicroseconds,fastElapsedMicroseconds);
        }
        System.out.println("----------------------------------------");
        myScanner.close();
	}
}