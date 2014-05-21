package cs.rsa.ts14dist.doubles;

import java.util.List;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.ClientInfo;
import org.restlet.data.Conditions;
import org.restlet.data.Cookie;
import org.restlet.data.MediaType;
import org.restlet.data.Metadata;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.data.Range;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.circuitbreakableClientResource.ClientResourceInterface;
/**
 * 
 * @author Andreas
 *
 */
public class FakeClientResource implements ClientResourceInterface {
	Logger log = LoggerFactory.getLogger(FakeClientResource.class);
	private int count = 0;
	private boolean fail = false;
	

	//Method to help control behavior during testing
	public void setResourceToFail(){
		fail = true;
	}
	
	//Method to help control behavior during testing
	public void setResourceToSucceed(){
		fail = false;
	}

	//Method to help verify testing, by returning the number of recieved calls
	public int getCount(){
		return count;
	}
	
	
	@Override
	public Representation get() {
		count ++;
		log.debug("FakeClientResource recived get # " + count);
		if(fail){
			throw new ResourceException(0);
		}
		return null;
	}

	@Override
	public void accept(Metadata metadata, float quality) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void accept(Metadata... metadata) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Reference addQueryParameter(Parameter parameter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reference addQueryParameter(String name, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reference addQueryParameters(Iterable<Parameter> parameters) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reference addSegment(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Request createRequest() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Request createRequest(Request prototype) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Representation delete() throws ResourceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T delete(Class<T> resultClass) throws ResourceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Representation delete(MediaType mediaType) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void doError(Status errorStatus) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public <T> T get(Class<T> resultClass) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation get(MediaType mediaType) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public String getAttribute(String name) {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T getChild(Reference relativeRef, Class<? extends T> resourceInterface) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public ClientResource getChild(Reference relativeRef) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T getChild(String relativeUri, Class<? extends T> resourceInterface) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public ClientResource getChild(String relativeUri) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getMaxRedirects() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Uniform getNext() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Uniform getOnResponse() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Uniform getOnSent() {
		throw new UnsupportedOperationException();

	}

	@Override
	public ClientResource getParent() throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T getParent(Class<? extends T> resourceInterface) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getRetryAttempts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getRetryDelay() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Representation handle() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Response handle(Request request) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation handleInbound(Response response) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Response handleOutbound(Request request) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean hasNext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Representation head() throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation head(MediaType mediaType) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isFollowingRedirects() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRequestEntityBuffering() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isResponseEntityBuffering() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRetryOnError() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Representation options() throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T options(Class<T> resultClass) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation options(MediaType mediaType) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T post(Object entity, Class<T> resultClass) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation post(Object entity, MediaType mediaType) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation post(Object entity) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation post(Representation entity) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T put(Object entity, Class<T> resultClass) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation put(Object entity, MediaType mediaType) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation put(Object entity) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Representation put(Representation entity) throws ResourceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setAttribute(String name, Object value) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setChallengeResponse(ChallengeResponse challengeResponse) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setChallengeResponse(ChallengeScheme scheme, String identifier, String secret) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setClientInfo(ClientInfo clientInfo) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setConditions(Conditions conditions) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setCookies(Series<Cookie> cookies) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setEntityBuffering(boolean entityBuffering) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setFollowingRedirects(boolean followingRedirects) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setHostRef(Reference hostRef) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setHostRef(String hostUri) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setLoggable(boolean loggable) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setMaxRedirects(int maxRedirects) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setMethod(Method method) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setNext(Uniform arg0) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setOnResponse(Uniform onResponseCallback) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setOnSent(Uniform onSentCallback) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setOriginalRef(Reference originalRef) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setProtocol(Protocol protocol) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setProxyChallengeResponse(ChallengeResponse challengeResponse) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setProxyChallengeResponse(ChallengeScheme scheme, String identifier, String secret) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setRanges(List<Range> ranges) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setReference(Reference reference) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setReference(String uri) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setReferrerRef(Reference referrerRef) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setReferrerRef(String referrerUri) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setRequestEntityBuffering(boolean requestEntityBuffering) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setResponseEntityBuffering(boolean responseEntityBuffering) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setRetryAttempts(int retryAttempts) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setRetryDelay(long retryDelay) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setRetryOnError(boolean retryOnError) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public <T> T wrap(Class<? extends T> resourceInterface) {
		throw new UnsupportedOperationException();

	}

}
