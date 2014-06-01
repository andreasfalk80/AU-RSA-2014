package cs.rsa.ts14dist.doubles;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class StubCookieServiceServerFastResponse extends ServerResource implements Runnable{
			static int count = 0;
			static int called = 0;
			
	
		   @Get
		   public String toString() { 
			   called++;
			   System.err.println("fastServer called " +called);
			   count++;
		      return "FastResponse "+count;  
		   }

		@Override
		public void run() {
			try{
				// Create the HTTP server and listen on port 8182  
				new Server(Protocol.HTTP, 8182, StubCookieServiceServerFastResponse.class).start();  
			}
			catch(Exception e){
				//do nothing
			}
			
		}

		}  

