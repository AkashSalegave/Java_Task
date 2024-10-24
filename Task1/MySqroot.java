package Task1;
import java.text.DecimalFormat;

public class MySqroot{
    public static void main(String[] args) {
    	// Argument in entered for check
        if(args.length == 0){
            System.out.print("Number not Provided!!");
            return;
        }
        //parse input
        double input;
        try {
            input = Double.parseDouble(args[0]);
        } catch (Exception e) {
            System.out.println("Number is Invalid!!");
            return;
        }
        //Handle negative input
        if(input < 0){
            System.out.println(input + "number is negative!!");
            return;
        }
       //Initialize Newton's Method
        double z=1.0;
        int maxIteration=25;
        double tolerance=0.001;
        DecimalFormat df=new DecimalFormat("0.000");
        
        for(int i=0;i<maxIteration;i++) {
        	double previousZ=z;
        	//Newton's Formulae
        	z-=(z*z-input)/(2*z);
        	
        	//Check for 
        	if(Math.abs(z-previousZ)<=tolerance) {
        		break;
        	}
        }
        
        //Print Result
        System.out.println(df.format(input)+" "+df.format(z));
    }
        
}




