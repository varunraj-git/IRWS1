import java.io.*;

public class irws {
	static double count;
    static double countR=0;
    static String Engine;
    static double relevant; 
	 public static void main(String [] args) throws Exception {

	        // This will reference one line at a time
	        String line = null;
	        String Enarray[];
//	        double count;
//	        double countR=0;
	        
	      
	        FileReader fileReader = 
	                new FileReader(args[0]);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	            		Enarray=line.split(";");
	            		
	            	relevant=Double.valueOf(Enarray[3]);
	            		//For loop for each string to print after semicolon
	            	for(int i=0; i<4; i++) {  
	            		//System.out.println(array[i]);
	            	}
	            	
	                String Engine=Enarray[2];
	                //Finding Length
	                count=(double)Enarray[2].length();
	               // System.out.println(Engine);
	                
	                for(int j=0;j<count;j++)
	                {
	                	if(Engine.charAt(j)=='R') {
	                		countR++;
		                	System.out.println(countR);
		                	precision();
		                	recall();
		                	patfive();

	                	}
	                }
	            	 countR=0.0;

	             	
	                // Assume default encoding.
//	                FileWriter fileWriter =
//	                    new FileWriter("");
//
//	                // Always wrap FileWriter in BufferedWriter.
//	                BufferedWriter bufferedWriter =
//	                    new BufferedWriter(fileWriter);

	                // Note that write() does not automatically
	                // append a newline character.
//	                bufferedWriter.write(line);
//	              
//	                // Always close files.
//	                bufferedWriter.close(); 
	                
	            }   
	         // Always close files.
	            bufferedReader.close();  
	            //System.out.println(args[0]);  
	 }
	 
	  static void precision() {
		  double precision;
		  precision = countR/count;
		  
		  System.out.println("The precision is: " + precision);
	  }
	  static void recall() {
		  double recall;
		  System.out.println("The recall is:"+countR/relevant);
	  }
	  static void patfive() {
		  
		  
	  }
}