package cs.rsa.ts14.circuitbreakerState;

public interface CircuitBreaker<RES> {

	public CircuitBreaker<RES> call();
	
	public RES getResult();

}