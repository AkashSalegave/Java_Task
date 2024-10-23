import java.util.Scanner;

public class MySqroot 
{
	public static void main(String[] args) 
	{
        System.out.println(" Enter any number to find the Square Root ");
        Scanner sc=new Scanner(System.in);

        double x=sc.nextDouble();
		// Check if an input number is provided
		
		//Blank Space 
        if (x == 0) 
        {
            System.out.println("number not provided");
            return;
        }
          
        try 
        {
            // Parse the input number from String to Double 
            //double x = Double.parseDouble(args[0]);

            // Check if the number is negative
            //-16
            if (x < 0) 
            {  
            	//System.out.println(x);
                System.out.printf("%.2f Incorrect number%n", x);
                return;
            }

            // Initialize the Z for the square root
            double z = 1.0;
            int maxIterations = 25; //maximum iteration
            double tolerance=0.001;
            //DecimalFormat df=new DecimalFormat(0.0000);
            for (int i = 0; i < maxIterations; i++) 
            {
                double previousZ = z;
            
                System.out.println("Iteration is: " + i + " Input Value is: "+x  +  " Old Value of Z is: "+ z  + " PreviousZ "+previousZ);
          
                z -= (z * z - x) / (2 * z); //z=z-(z * z - x) / (2 * z)

                System.out.println("Iteration is: " + i + " Input Value is: "+x  +  " New Value of z is: "+z + " PreviousZ "+previousZ +" Math.abs "+Math.abs(z - previousZ)) ;
                System.out.println("....................................");
                 if (Math.abs(z - previousZ) <= 0.001) //1.0 //3.0
                {
                	 System.out.println(Math.abs(z-previousZ));
                    break;
                }
            }

            // Output the result with 4 decimal points
            System.out.printf("%.2f %.4f%n", x, z);

        } 
        catch (NumberFormatException e) 
        {
            // Handle invalid number format
            System.out.printf("%.2f Incorrect number%n", Double.parseDouble(args[0]));
        }
	
	}

    
}