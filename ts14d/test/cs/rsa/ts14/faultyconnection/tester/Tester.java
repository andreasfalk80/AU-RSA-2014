package cs.rsa.ts14.faultyconnection.tester;

import org.apache.log4j.BasicConfigurator;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.common.Constants;
import cs.rsa.ts14dist.faultyconnection.FaultyConnection;

public class Tester {
	static Logger log = LoggerFactory.getLogger(Tester.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		ClientResource res = new ClientResource("http://localhost:1500");
//		ClientResource res = new ClientResource("http://"+Constants.DIGITALOCEAN_INSTANCE_IP+":"+Constants.COOKIE_REST_PORT+"/rsa/cookie");
		FaultyConnection temp = new FaultyConnection(res);

		Representation result = null;

		for (int i = 0; i < 10; i++) {

			try {
				result = temp.get();
			} catch (ResourceException e) {
				log.info("Exception!");
			}
			if (result == null) {
				log.warn("No response was received");
			}
		}

		try{
			log.info("Sleeping for 10 seconds");
			Thread.sleep(10000);
		}
		catch(InterruptedException e){
			//do nothing, just continue
		}
		for (int i = 0; i < 10; i++) {

			try {
				result = temp.get();
			} catch (ResourceException e) {
				log.info("Exception!");
			}
			if (result == null) {
				log.warn("No response was received");
			}
		}


	}

}
