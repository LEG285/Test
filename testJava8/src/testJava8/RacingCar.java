package testJava8;

public class RacingCar extends Car {
	
	public RacingCar() { 
		   speed = 180; 
	}
	
	public void accelerate(int deltaSpeed) {
	       speed += deltaSpeed;
	       System.out.println("The new speed is : " + speed);
	}
	
}