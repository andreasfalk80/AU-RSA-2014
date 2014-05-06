package cs.rsa.ts14.circuitbreakerState;

public class OpenCircuitBreaker<CONN,RES> implements CircuitBreaker<RES> {
	int timeOut = 10000; //milliseconds
	long TimeofOpen;
	RES result = null;
	
	FaultyConnection<CONN, RES> managedConn;
	
	@SuppressWarnings ("unused")
	private OpenCircuitBreaker(){
	}

	
	protected OpenCircuitBreaker(FaultyConnection<CONN, RES> conn){
		managedConn = conn;
		TimeofOpen = System.currentTimeMillis();
	}
	
	@Override
	public CircuitBreaker<RES> call(){
		CircuitBreaker<RES> state = this;
		
		if(System.currentTimeMillis()-TimeofOpen > timeOut){ //perform call
			state = new HalfOpenCircuitBreaker<CONN,RES>(managedConn);
			/*
			 * forsøg at lade kaldet køre igennem. Dette betyder at den oprindeligt kaldende state aldrig reelt kommer til at stå med en HalfOpen, 
			 * da der er tale om en intern tilstand.
			 */
			state = state.call();
		}
		return state;
	}

	@Override
	public RES getResult() {
		return result;
	}
	
	
}
