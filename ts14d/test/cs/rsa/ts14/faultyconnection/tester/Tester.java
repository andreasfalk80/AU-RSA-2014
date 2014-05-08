package cs.rsa.ts14.faultyconnection.tester;

import org.apache.log4j.BasicConfigurator;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.faultyconnection.FaultyConnection;

public class Tester {
	static Logger log = LoggerFactory.getLogger(Tester.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		ClientResource res = new ClientResource("http://localhost:1500");
		FaultyConnection temp = new FaultyConnection(res);

		Representation result = null;

		for (int i = 0; i < 5; i++) {

			try {
				result = temp.get();
			} catch (Exception e) {
				//
			}
			if (result == null) {
				log.warn("No response was received");
			}
		}

	}

}
