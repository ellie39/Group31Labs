import lejos.nxt.NXTRegulatedMotor;

public class TwoWheeledRobot {
	public static final double DEFAULT_LEFT_RADIUS = 2.125;
	public static final double DEFAULT_RIGHT_RADIUS = 2.125;
	public static final double DEFAULT_WIDTH = 14.9;
	private NXTRegulatedMotor leftMotor, rightMotor;
	private double leftRadius, rightRadius, width;
	private double forwardSpeed;
	double rotationSpeed;
	
	public TwoWheeledRobot(NXTRegulatedMotor leftMotor,
						   NXTRegulatedMotor rightMotor,
						   double width,
						   double leftRadius,
						   double rightRadius) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.leftRadius = leftRadius;
		this.rightRadius = rightRadius;
		this.width = width;
	}
	
	public NXTRegulatedMotor getRightMotor (){
		return this.rightMotor;
	}
	public NXTRegulatedMotor getLeftMotor (){
		return this.leftMotor;
	}
	
	public TwoWheeledRobot(NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor) {
		this(leftMotor, rightMotor, DEFAULT_WIDTH, DEFAULT_LEFT_RADIUS, DEFAULT_RIGHT_RADIUS);
	}
	
	public TwoWheeledRobot(NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor, double width) {
		this(leftMotor, rightMotor, width, DEFAULT_LEFT_RADIUS, DEFAULT_RIGHT_RADIUS);
	}
	
	// accessors
	public double getDisplacement() {
		return (leftMotor.getTachoCount() * leftRadius +
				rightMotor.getTachoCount() * rightRadius) *
				Math.PI / 360.0;
	}
	
	public double getHeading() {
		return (leftMotor.getTachoCount() * leftRadius -
				rightMotor.getTachoCount() * rightRadius) / width;
	}
	
	public void getDisplacementAndHeading(double [] data) {
		int leftTacho, rightTacho;
		leftTacho = leftMotor.getTachoCount();
		rightTacho = rightMotor.getTachoCount();
		
		data[0] = (leftTacho * leftRadius + rightTacho * rightRadius) *	Math.PI / 360.0;
		data[1] = (leftTacho * leftRadius - rightTacho * rightRadius) / width;
	}
	
	// mutators
	public void setForwardSpeed(double speed) {
		forwardSpeed = speed;
		setSpeeds(forwardSpeed, rotationSpeed);
	}
	
	public void setRotationSpeed(double speed) {
		rotationSpeed = speed;
		setSpeeds(forwardSpeed, rotationSpeed);
	}
	
	public void goForward(){
		leftMotor.forward();
		rightMotor.forward();
	}
	
	
	public void rotateClockwise(){
		leftMotor.setSpeed((int)rotationSpeed);
		rightMotor.setSpeed((int)rotationSpeed);
		leftMotor.forward();
		rightMotor.backward();
	}
	
	public void rotateCounterClockwise(){
		leftMotor.setSpeed((int)rotationSpeed);
		rightMotor.setSpeed((int)rotationSpeed);
		leftMotor.backward();
		rightMotor.forward();
	}
	
	public void stahp(){
		leftMotor.stop();
		rightMotor.stop();
	}
	
	public void setSpeeds(double forwardSpeed, double rotationalSpeed) {
		double leftSpeed, rightSpeed;

		this.forwardSpeed = forwardSpeed;
		this.rotationSpeed = rotationalSpeed; 

		leftSpeed = (forwardSpeed + rotationalSpeed * width * Math.PI / 360.0) *
				180.0 / (leftRadius * Math.PI);
		rightSpeed = (forwardSpeed - rotationalSpeed * width * Math.PI / 360.0) *
				180.0 / (rightRadius * Math.PI);

		// set motor directions
		if (leftSpeed > 0.0)
			leftMotor.forward();
		else {
			leftMotor.backward();
			leftSpeed = -leftSpeed;
		}
		
		if (rightSpeed > 0.0)
			rightMotor.forward();
		else {
			rightMotor.backward();
			rightSpeed = -rightSpeed;
		}
		
		// set motor speeds
		if (leftSpeed > 900.0)
			leftMotor.setSpeed(900);
		else
			leftMotor.setSpeed((int)leftSpeed);
		
		if (rightSpeed > 900.0)
			rightMotor.setSpeed(900);
		else
			rightMotor.setSpeed((int)rightSpeed);
	}
}
