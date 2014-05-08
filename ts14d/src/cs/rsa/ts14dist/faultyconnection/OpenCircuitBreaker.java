package cs.rsa.ts14dist.faultyconnection;

import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenCircuitBreaker implements CircuitBreaker {
	static Logger log = LoggerFactory.getLogger(OpenCircuitBreaker.class);
	private int waitTime = 10000; //in millisecond
	private long creationTime; 
	
	OpenCircuitBreaker() {
		log.info("CircuitBreaker entering Open state");
		creationTime = System.currentTimeMillis();
	}
	
	public Representation call(FaultyConnection conn) {
		Representation result = null;
		//Hvis vi har ventet lang tid nok
		if(System.currentTimeMillis()-creationTime > waitTime){
			log.info("Attempted call, after timeout periode completed. Attempting reset");
			
			//Vi fors�ger om forbindelsen virker igen
			CircuitBreaker tester = new HalfOpenCircuitBreaker();
			conn.setBreaker(tester);
			result = tester.call(conn);
		}
		else{
			log.info("Attempted call, before timeout periode completed. Call fail");
		}
		return result;
	}

}
