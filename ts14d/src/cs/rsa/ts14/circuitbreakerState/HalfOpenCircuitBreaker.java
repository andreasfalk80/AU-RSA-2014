package cs.rsa.ts14.circuitbreakerState;

public class HalfOpenCircuitBreaker<CONN,RES> implements CircuitBreaker<CONN,RES> {

	FaultyConnection<CONN, RES> managedConn;
	RES result = null;
	
	protected HalfOpenCircuitBreaker(FaultyConnection<CONN, RES> conn){
		managedConn = conn;
	}
	
	@SuppressWarnings ("unused")
	private HalfOpenCircuitBreaker(){
	}

	
	@Override
	public CircuitBreaker<CONN,RES> call(){
		CircuitBreaker<CONN,RES> state = this;
		
		try{
			result = managedConn.execute();
			state = new ClosedCircuitBreaker<CONN,RES>(managedConn,getResult()); 
		}
		catch(FaultyConnectionException e){
			state = new OpenCircuitBreaker<CONN,RES>(managedConn);
			//TODO logging of incident
		}		
		catch(Exception e){
			//TODO logging of incident
			throw e;
		}

		
		return state;
	}

	@Override
	//Doesn't need to do anything, since this state is only for internal use
	public RES getResult() {
		return null;
	}
	
	
}
