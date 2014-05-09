package cs.rsa.ts14dist.doubles;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.circuitbreakableconnection.PartialClientResource;

public class FakeClientResource implements PartialClientResource {
	Logger log = LoggerFactory.getLogger(FakeClientResource.class);
	private int count = 0; 
	
	@Override
	public Representation get() {
		count ++;
		log.info("Get # " + count);
		if((5 < count && count < 8) ||
				(12 < count && count < 20)){
			throw new ResourceException(0);
		}
		
		return null;
	}

}
