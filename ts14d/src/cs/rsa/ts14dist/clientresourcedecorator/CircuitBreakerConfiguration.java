package cs.rsa.ts14dist.clientresourcedecorator;

public class CircuitBreakerConfiguration {
	private int faultThreshold;
	private int waitUntilReset;
	
	/**
	 * Creates a configuration object for the circuitbreaker
	 * @param faultThreshold number of consecutive failures before circuit breaker trips
	 * @param waitUntilReset time spent in open state before a reset is attempted. Specified in milliseconds
	 */
	public CircuitBreakerConfiguration(int faultThreshold, int waitUntilReset){
		this.faultThreshold = faultThreshold;
		this.waitUntilReset = waitUntilReset;
	}
	
	
	public int getFaultThreshold() {
		return faultThreshold;
	}
	public int getWaitUntilReset() {
		return waitUntilReset;
	}
	
	
}
