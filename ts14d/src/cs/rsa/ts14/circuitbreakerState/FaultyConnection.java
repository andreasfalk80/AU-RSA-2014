package cs.rsa.ts14.circuitbreakerState;

public interface FaultyConnection <CONN, RES> {
	

	public void setConnection(CONN connection);
	
	public RES execute() throws FaultyConnectionException;
		
}
