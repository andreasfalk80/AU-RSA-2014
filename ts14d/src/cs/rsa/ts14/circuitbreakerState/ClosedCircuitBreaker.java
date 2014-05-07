package cs.rsa.ts14.circuitbreakerState;


public class ClosedCircuitBreaker<CONN,RES> implements CircuitBreaker<CONN,RES> {

	int threshold = 2;
	int failureCount = 0;
	FaultyConnection<CONN, RES> managedConn;
	
	RES result = null;
	
	@SuppressWarnings ("unused")
	private ClosedCircuitBreaker(){
	}
	
	public ClosedCircuitBreaker(FaultyConnection<CONN, RES> conn){
		if(conn == null){
			throw new IllegalArgumentException("Connection can't be null");
		}
		managedConn = conn;
	}
	
	//This is used when a response has been generated from the HalfOpen State, and needs to be present at the newly created closed state!!
	protected ClosedCircuitBreaker(FaultyConnection<CONN, RES> conn, RES result){
		managedConn = conn;
	}

	
	
	@Override
	public CircuitBreaker<CONN,RES> call(){
		CircuitBreaker<CONN,RES> state = this;
		
		try{
			result = managedConn.execute();
			//hvis kald går godt..
			failureCount = 0;
		}
		catch(FaultyConnectionException e){
			failureCount++;
			if(failureCount >= threshold){
				state = new OpenCircuitBreaker<CONN,RES>(managedConn); 
			}
			
			//TODO logging of incident
		}
		catch(Exception e){
			//TODO logging of incident
			throw e;
		}
		
		return state;
	}

	@Override
	public RES getResult() {
		return result;
	}
	
	
}
