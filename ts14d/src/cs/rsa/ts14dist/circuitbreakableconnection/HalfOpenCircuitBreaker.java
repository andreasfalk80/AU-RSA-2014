package cs.rsa.ts14dist.circuitbreakableconnection;

import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HalfOpenCircuitBreaker implements CircuitBreaker {
	static Logger log = LoggerFactory.getLogger(HalfOpenCircuitBreaker.class);

	HalfOpenCircuitBreaker() {
		log.info("CircuitBreaker entering HalfOpen state");
	}

	public Representation call(CircuitbreakableConnection conn) {
		Representation result = null;
		try {
			result = conn.executeGet();
			// hvis det gik godt
			log.info("successfull get. Resetting to Closed state");
			conn.setBreaker(new ClosedCircuitBreaker());
		} catch (Exception e) {
			log.info("unsuccessfull get. Return to Open state");
			conn.setBreaker(new OpenCircuitBreaker());
			throw e;
		}
		return result;
	}
}
