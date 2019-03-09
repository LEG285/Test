package testJava8;

public abstract class Car {
  
	int speed;
  
    public Car () { 
	  speed = 90;
    }
 
    abstract void accelerate(int deltaSpeed);

    public static void main(String[] args) {
	  
	  Car c = new RacingCar();  // line n2
	  c.accelerate(50);         // line n3
  
    	/*int n1 = (int)(byte)(char) -1;
    	  int n2 = (int)(char)(byte) -1;
    	  System.out.println(n1==n2);
    			
    	  System.out.println(n1);
    	  System.out.println(n2);
         */    	
    	
    }
  
}


