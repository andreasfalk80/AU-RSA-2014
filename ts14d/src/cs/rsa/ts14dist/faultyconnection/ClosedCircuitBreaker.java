package cs.rsa.ts14dist.faultyconnection;


import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClosedCircuitBreaker implements CircuitBreaker {

	static Logger log = LoggerFactory.getLogger(ClosedCircuitBreaker.class);
	private int threshold = 2;
	private int faultCount = 0;

	public ClosedCircuitBreaker() {
		log.info("CircuitBreaker entering Closed state");
	}

	public Representation call(FaultyConnection conn) {
		Representation result = null;
		// do all the stuff to call the connetion.
		try {
			result = conn.executeGet();
			// F�lgende udf�res kun hvis der ikke er exception
			log.info("CircuitBreaker - successfull get");
			faultCount = 0;
		} catch (ResourceException e) {
			faultCount++;
			log.info("CircuitBreaker - unsuccecssfull get, faultCount: "
					+ faultCount);
			if (faultCount >= threshold) {
				// update state
				conn.setBreaker(new OpenCircuitBreaker());
				// TODO log incident
				
				// rethrow exception
				throw e;

			}
		}
		return result;

	}

}
