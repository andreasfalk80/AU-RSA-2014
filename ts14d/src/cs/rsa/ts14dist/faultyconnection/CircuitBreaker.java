package cs.rsa.ts14dist.faultyconnection;

import org.restlet.representation.Representation;

public interface CircuitBreaker {
	
//	public void setThreshold(int tripLimit);
//	public int getThreshold();
//	
//	public void setWaitTime(int milliseconds);
//	public int getWaitTime();
	
	public Representation call(FaultyConnection conn); 
}
