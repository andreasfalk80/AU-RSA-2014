package cs.rsa.ts14dist.faultyconnection;

import org.restlet.representation.Representation;

public class OpenCircuitBreaker implements CircuitBreaker {
	private int waitTime = 10000; //in millisecond
	private long creationTime; 
	
	OpenCircuitBreaker() {
		creationTime = System.currentTimeMillis();
	}
	
	public Representation call(FaultyConnection conn) {
		Representation result = null;
		//Hvis vi har ventet lang tid nok
		if(System.currentTimeMillis()-creationTime > waitTime){
			//Vi fors�ger om forbindelsen virker igen
			CircuitBreaker tester = new HalfOpenCircuitBreaker();
			result = tester.call(conn);
		}
		else{
			//TODO log incident
		}
		return result;
	}

}
