package cs.rsa.ts14dist.doubles;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class StubCookieServiceServerSlowResponse extends ServerResource implements Runnable{
			static int count = 0;
			static int called = 0;
			
	
		   @Get
		   public String toString() { 
			   called++;
			   System.err.println("slowServer called " +called);
			   
			   try {
				   Thread.sleep(10000);
			} catch (InterruptedException e) {
				//Do nothing
			}
			   count++;
		      return "SlowResponse "+count;  
		   }

		@Override
		public void run() {
			try{
				// Create the HTTP server and listen on port 8183  
				new Server(Protocol.HTTP, 8183, StubCookieServiceServerSlowResponse.class).start();  
			}
			catch(Exception e){
				//do nothing
			}
			
		}

		}  

