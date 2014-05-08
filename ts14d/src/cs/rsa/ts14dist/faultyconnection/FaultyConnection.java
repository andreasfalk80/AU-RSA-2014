package cs.rsa.ts14dist.faultyconnection;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.ClientInfo;
import org.restlet.data.Conditions;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.data.Dimension;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Range;
import org.restlet.data.Reference;
import org.restlet.data.ServerInfo;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.service.ConverterService;
import org.restlet.service.MetadataService;
import org.restlet.service.StatusService;
import org.restlet.util.Series;
import org.slf4j.LoggerFactory;

public class FaultyConnection {
	
	static org.slf4j.Logger log = LoggerFactory.getLogger(FaultyConnection.class);
	private CircuitBreaker breaker = new ClosedCircuitBreaker();
	private ClientResource resource;
	
	public FaultyConnection(ClientResource originalClientResource){
		resource = originalClientResource;
	}
	
	void setBreaker(CircuitBreaker breaker){
		this.breaker = breaker;
	}

	//delegate funktion der kalder Clientresource via circuitbreaker pattern.
	public Representation get() throws ResourceException{
			return breaker.call(this);
	}
	
	public Representation executeGet(){
		//call the real ClientResource
		return resource.get();
	}

	//Delegating functions *************************************************************************
	
	public Representation delete() throws ResourceException {
		return resource.delete();
	}

	public <T> T delete(Class<T> resultClass) throws ResourceException {
		return resource.delete(resultClass);
	}

	public Representation delete(MediaType mediaType) throws ResourceException {
		return resource.delete(mediaType);
	}

	public boolean equals(Object arg0) {
		return resource.equals(arg0);
	}

	public <T> T get(Class<T> resultClass) throws ResourceException {
		return resource.get(resultClass);
	}

	public Representation get(MediaType mediaType) throws ResourceException {
		return resource.get(mediaType);
	}

	public Set<Method> getAllowedMethods() {
		return resource.getAllowedMethods();
	}

	public Application getApplication() {
		return resource.getApplication();
	}

	public List<ChallengeRequest> getChallengeRequests() {
		return resource.getChallengeRequests();
	}

	public ChallengeResponse getChallengeResponse() {
		return resource.getChallengeResponse();
	}

	public <T> T getChild(Reference relativeRef,
			Class<? extends T> resourceInterface) throws ResourceException {
		return resource.getChild(relativeRef, resourceInterface);
	}

	public ClientResource getChild(Reference relativeRef)
			throws ResourceException {
		return resource.getChild(relativeRef);
	}

	public <T> T getChild(String relativeUri,
			Class<? extends T> resourceInterface) throws ResourceException {
		return resource.getChild(relativeUri, resourceInterface);
	}

	public ClientResource getChild(String relativeUri) throws ResourceException {
		return resource.getChild(relativeUri);
	}

	public ClientInfo getClientInfo() {
		return resource.getClientInfo();
	}

	public Conditions getConditions() {
		return resource.getConditions();
	}

	public Context getContext() {
		return resource.getContext();
	}

	public ConverterService getConverterService() {
		return resource.getConverterService();
	}

	public Series<Cookie> getCookies() {
		return resource.getCookies();
	}

	public Series<CookieSetting> getCookieSettings() {
		return resource.getCookieSettings();
	}

	public Set<Dimension> getDimensions() {
		return resource.getDimensions();
	}

	public Reference getHostRef() {
		return resource.getHostRef();
	}

	public Reference getLocationRef() {
		return resource.getLocationRef();
	}

	public Logger getLogger() {
		return resource.getLogger();
	}

	public Form getMatrix() {
		return resource.getMatrix();
	}

	public int getMaxForwards() {
		return resource.getMaxForwards();
	}

	public MetadataService getMetadataService() {
		return resource.getMetadataService();
	}

	public Method getMethod() {
		return resource.getMethod();
	}

	public Uniform getNext() {
		return resource.getNext();
	}

	public Uniform getOnResponse() {
		return resource.getOnResponse();
	}

	public Uniform getOnSent() {
		return resource.getOnSent();
	}

	public Reference getOriginalRef() {
		return resource.getOriginalRef();
	}

	public ClientResource getParent() throws ResourceException {
		return resource.getParent();
	}

	public <T> T getParent(Class<? extends T> resourceInterface)
			throws ResourceException {
		return resource.getParent(resourceInterface);
	}

	public Protocol getProtocol() {
		return resource.getProtocol();
	}

	public Form getQuery() {
		return resource.getQuery();
	}

	public List<Range> getRanges() {
		return resource.getRanges();
	}

	public Reference getReference() {
		return resource.getReference();
	}

	public Reference getReferrerRef() {
		return resource.getReferrerRef();
	}

	public Request getRequest() {
		return resource.getRequest();
	}

	public Map<String, Object> getRequestAttributes() {
		return resource.getRequestAttributes();
	}

	public Representation getRequestEntity() {
		return resource.getRequestEntity();
	}

	public Response getResponse() {
		return resource.getResponse();
	}

	public Map<String, Object> getResponseAttributes() {
		return resource.getResponseAttributes();
	}

	public Representation getResponseEntity() {
		return resource.getResponseEntity();
	}

	public int getRetryAttempts() {
		return resource.getRetryAttempts();
	}

	public long getRetryDelay() {
		return resource.getRetryDelay();
	}

	public Reference getRootRef() {
		return resource.getRootRef();
	}

	public ServerInfo getServerInfo() {
		return resource.getServerInfo();
	}

	public Status getStatus() {
		return resource.getStatus();
	}

	public StatusService getStatusService() {
		return resource.getStatusService();
	}

	public Representation handle() {
		return resource.handle();
	}

	public int hashCode() {
		return resource.hashCode();
	}

	public boolean hasNext() {
		return resource.hasNext();
	}

	public Representation head() throws ResourceException {
		return resource.head();
	}

	public Representation head(MediaType mediaType) throws ResourceException {
		return resource.head(mediaType);
	}

	public final void init(Context arg0, Request arg1, Response arg2) {
		resource.init(arg0, arg1, arg2);
	}

	public boolean isConfidential() {
		return resource.isConfidential();
	}

	public boolean isFollowingRedirects() {
		return resource.isFollowingRedirects();
	}

	public boolean isRetryOnError() {
		return resource.isRetryOnError();
	}

	public Representation options() throws ResourceException {
		return resource.options();
	}

	public <T> T options(Class<T> resultClass) throws ResourceException {
		return resource.options(resultClass);
	}

	public Representation options(MediaType mediaType) throws ResourceException {
		return resource.options(mediaType);
	}

	public <T> T post(Object entity, Class<T> resultClass)
			throws ResourceException {
		return resource.post(entity, resultClass);
	}

	public Representation post(Object entity, MediaType mediaType)
			throws ResourceException {
		return resource.post(entity, mediaType);
	}

	public Representation post(Object entity) throws ResourceException {
		return resource.post(entity);
	}

	public Representation post(Representation entity) throws ResourceException {
		return resource.post(entity);
	}

	public <T> T put(Object entity, Class<T> resultClass)
			throws ResourceException {
		return resource.put(entity, resultClass);
	}

	public Representation put(Object entity, MediaType mediaType)
			throws ResourceException {
		return resource.put(entity, mediaType);
	}

	public Representation put(Object entity) throws ResourceException {
		return resource.put(entity);
	}

	public Representation put(Representation entity) throws ResourceException {
		return resource.put(entity);
	}

	public final void release() {
		resource.release();
	}

	public void setChallengeResponse(ChallengeResponse challengeResponse) {
		resource.setChallengeResponse(challengeResponse);
	}

	public void setChallengeResponse(ChallengeScheme scheme, String identifier,
			String secret) {
		resource.setChallengeResponse(scheme, identifier, secret);
	}

	public void setClientInfo(ClientInfo clientInfo) {
		resource.setClientInfo(clientInfo);
	}

	public void setConditions(Conditions conditions) {
		resource.setConditions(conditions);
	}

	public void setCookies(Series<Cookie> cookies) {
		resource.setCookies(cookies);
	}

	public void setFollowingRedirects(boolean followingRedirects) {
		resource.setFollowingRedirects(followingRedirects);
	}

	public void setHostRef(Reference hostRef) {
		resource.setHostRef(hostRef);
	}

	public void setHostRef(String hostUri) {
		resource.setHostRef(hostUri);
	}

	public void setMethod(Method method) {
		resource.setMethod(method);
	}

	public void setNext(Uniform arg0) {
		resource.setNext(arg0);
	}

	public void setOnResponse(Uniform onResponseCallback) {
		resource.setOnResponse(onResponseCallback);
	}

	public void setOnSent(Uniform onSentCallback) {
		resource.setOnSent(onSentCallback);
	}

	public void setOriginalRef(Reference originalRef) {
		resource.setOriginalRef(originalRef);
	}

	public void setProtocol(Protocol protocol) {
		resource.setProtocol(protocol);
	}

	public void setRanges(List<Range> ranges) {
		resource.setRanges(ranges);
	}

	public void setReference(Reference reference) {
		resource.setReference(reference);
	}

	public void setReference(String uri) {
		resource.setReference(uri);
	}

	public void setReferrerRef(Reference referrerRef) {
		resource.setReferrerRef(referrerRef);
	}

	public void setReferrerRef(String referrerUri) {
		resource.setReferrerRef(referrerUri);
	}

	public void setRequest(Request request) {
		resource.setRequest(request);
	}

	public void setResponse(Response response) {
		resource.setResponse(response);
	}

	public void setRetryAttempts(int retryAttempts) {
		resource.setRetryAttempts(retryAttempts);
	}

	public void setRetryDelay(long retryDelay) {
		resource.setRetryDelay(retryDelay);
	}

	public void setRetryOnError(boolean retryOnError) {
		resource.setRetryOnError(retryOnError);
	}

	public String toString() {
		return resource.toString();
	}

	public <T> T wrap(Class<? extends T> resourceInterface) {
		return resource.wrap(resourceInterface);
	}
	
	
}
