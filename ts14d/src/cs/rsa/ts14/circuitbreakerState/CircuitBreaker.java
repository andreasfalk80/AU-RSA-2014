package cs.rsa.ts14.circuitbreakerState;

//TODO måske er det ikke nødvendigt med CONN som parameter
public interface CircuitBreaker<CONN,RES> {

	public CircuitBreaker<CONN, RES> call();
	
	public RES getResult();

}