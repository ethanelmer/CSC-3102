/**
 * Provides implementations of Sine Approximation Algorithms
 * and auxiliary methods
 * @author William Duncan, Ethan Elmer 
 * <pre>
 * Date: 09-04-2021
 * Course: csc 3102
 * Project # 0
 * Instructor: Dr. Duncan
 * </pre>
 */

public class SineUtil
{
    /**
     * Computes the factorial of the specified number
     * @param n the number whose factorial is to be determined
     * @return nfactor
     * @throw IllegalArgumentException when n < 0 
     */
    private static double factorial(int n)
    {
    	//Implement this method
    	double nfactor;
    	if (n<0)
    	{
    		throw new IllegalArgumentException("FACTORIAL IS UNDEFINED FOR N<0");
    	}
    	if (n==0) 
    	{
        	nfactor = 1;
        }
        else 
        {
        	nfactor = n*(factorial(n-1));
        }
        return nfactor;
    }
    
    /**
     * Computes the specified power
     * @param x the base of the power
     * @param n the exponent of the power
     * @return x^n
     * @throw IllegalArgumentException when x = 0 and n <= 0 
     */
    private static double pow(double x, int n)
    {
        //Implement this method
        if(x==0 && n<=0) //check this
        {
        	throw new IllegalArgumentException("POWER SERIES IS INDETERMINATE FOR N<=0");
        }
        if(x==0 && n>0) 
        {
        	return 0;
        }
        if(n==0) 
        {
        	return 1;
        }
        if(x==1) 
        {
        	return 1;
        }
        if(x==-1&&n%2==0) 
        {
        	return 1;
        }
        if (x==-1&&n%2!=0) 
        {
        	return -1;
        }
        else 
        {
        	if (n<0) 
        	{
        		n=n*(-1);
        	}
        	return x * pow(x,n-1);
        } 
    }
    /**
     * Computes the sine of an angle using the Taylor Series approximation of the
     * sine function and naive exponentiation
     * @param x angle in radians
     * @param n number of terms
     * @return sine(x) = x - x^3/3! + x^5/5! - x^7/7! .....
     * @throw IllegalArgumentException when n <= 0
     */
    public static double naiveSine(double x, int n)
    {
        //Implement this method
    	double alpha = 0;
    	if (n<=0)
    	{
    		throw new IllegalArgumentException("UNDEFINED FOR N<=0");
    	}
    	else 
    	{
    		for (int i=1;i<n;i++) 
    		{
    			if (i%2 == 0) 
    			{
    				alpha -= ((pow(x,(2*i)-1))/factorial((2*i)-1));
    			}
    			else 
    			{
    				alpha += ((pow(x,(2*i)-1))/factorial((2*i)-1));
    			}
    		}
    		return alpha;
    	}
    }    
    
    /**
     * Computes the sine of an angle using the Taylor Series approximation of the
     * sine function and fast exponentiation
     * @param x angle in radians
     * @param n number of terms
     * @return sine(x) = x - x^3/3! + x^5/5! - x^7/7! .....
     * @throw IllegalArgumentException when n <= 0
     */
    public static double fastSine(double x, int n)
    {
    	//Implement this method
        double alpha = x;
        if(n<=0) 
        {
        	throw new IllegalArgumentException("UNDEFINED FOR N<=0");
        }
        else 
        {
            int denom = 3;
            int factor = -1;
            double base = x;
            for(int i=2; i<n; i++) 
            {
                base = base*(x/denom)*(x/(denom-1));
                denom = denom + 2;
                alpha = alpha + (base*factor);
                factor = factor*(-1);
            }
            return alpha;
        }
    }
}