package cs.rsa.ts14dist.circuitbreakableconnection;


import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClosedCircuitBreaker implements CircuitBreaker {

	static Logger log = LoggerFactory.getLogger(ClosedCircuitBreaker.class);
	private int threshold = 2;
	private int faultCount = 0;

	public ClosedCircuitBreaker() {
		log.info("CircuitBreaker entering Closed state");
	}

	
	public Representation call(CircuitbreakableConnection conn) {
		Representation result = null;
		// do all the stuff to call the connetion.
		try {
			result = conn.executeGet();
			// Følgende udføres kun hvis der ikke er exception
			log.info("successfull get");
			faultCount = 0;
		} catch (Exception e) {
			faultCount++;
			log.info("unsuccecssfull get, faultCount: "
					+ faultCount);
			if (faultCount >= threshold) {
				log.info("number of faults passed threshold. Set to Open state");
				// update state
				conn.setBreaker(new OpenCircuitBreaker());
			}
			
			// re throw exception
			throw e;
		}
		return result;

	}

}
