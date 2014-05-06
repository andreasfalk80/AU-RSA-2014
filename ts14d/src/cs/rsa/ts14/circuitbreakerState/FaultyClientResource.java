package cs.rsa.ts14.circuitbreakerState;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class FaultyClientResource implements FaultyConnection<ClientResource, Representation>{
	ClientResource myConn = null;
	
	
	public FaultyClientResource(ClientResource conn){
		myConn = conn;
	}
	
	
		@Override
	public Representation execute() throws FaultyConnectionException{
			Representation result = null;
			try{
				myConn.get();
			}
			catch(ResourceException e){
				throw new FaultyConnectionException(e);
			}
			return result;
	}

		@Override
	public void setConnection(ClientResource connection) {
			myConn = connection;
			
		}
	
	

}
