package cs.rsa.ts14.circuitbreakerState;

public class FaultyConnectionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private Exception original;
	
	public FaultyConnectionException(Exception original){
		this.original = original;
	}
		
	public Exception getCause(){
		return original;
	}

}
