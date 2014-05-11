package cs.rsa.ts14dist.circuitbreakableClientResource;

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
/**
 * The role as Decorator in the Decorator pattern.   
 *
 */
public abstract class ClientResourceDecorator implements ClientResourceInterface {

	protected ClientResourceInterface resource;

	public void accept(Metadata metadata, float quality) {
		resource.accept(metadata, quality);
	}

	public void accept(Metadata... metadata) {
		resource.accept(metadata);
	}

	public Reference addQueryParameter(Parameter parameter) {
		return resource.addQueryParameter(parameter);
	}

	public Reference addQueryParameter(String name, String value) {
		return resource.addQueryParameter(name, value);
	}

	public Reference addQueryParameters(Iterable<Parameter> parameters) {
		return resource.addQueryParameters(parameters);
	}

	public Reference addSegment(String value) {
		return resource.addSegment(value);
	}

	public Request createRequest() {
		return resource.createRequest();
	}

	public Request createRequest(Request prototype) {
		return resource.createRequest(prototype);
	}

	public Representation delete() throws ResourceException {
		return resource.delete();
	}

	public <T> T delete(Class<T> resultClass) throws ResourceException {
		return resource.delete(resultClass);
	}

	public Representation delete(MediaType mediaType) throws ResourceException {
		return resource.delete(mediaType);
	}

	public void doError(Status errorStatus) {
		resource.doError(errorStatus);
	}

	public Representation get() throws ResourceException {
		return resource.get();
	}

	public <T> T get(Class<T> resultClass) throws ResourceException {
		return resource.get(resultClass);
	}

	public Representation get(MediaType mediaType) throws ResourceException {
		return resource.get(mediaType);
	}

	public String getAttribute(String name) {
		return resource.getAttribute(name);
	}

	public <T> T getChild(Reference relativeRef, Class<? extends T> resourceInterface) throws ResourceException {
		return resource.getChild(relativeRef, resourceInterface);
	}

	public ClientResource getChild(Reference relativeRef) throws ResourceException {
		return resource.getChild(relativeRef);
	}

	public <T> T getChild(String relativeUri, Class<? extends T> resourceInterface) throws ResourceException {
		return resource.getChild(relativeUri, resourceInterface);
	}

	public ClientResource getChild(String relativeUri) throws ResourceException {
		return resource.getChild(relativeUri);
	}

	public int getMaxRedirects() {
		return resource.getMaxRedirects();
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

	public ClientResource getParent() throws ResourceException {
		return resource.getParent();
	}

	public <T> T getParent(Class<? extends T> resourceInterface) throws ResourceException {
		return resource.getParent(resourceInterface);
	}

	public int getRetryAttempts() {
		return resource.getRetryAttempts();
	}

	public long getRetryDelay() {
		return resource.getRetryDelay();
	}

	public Representation handle() {
		return resource.handle();
	}

	public Response handle(Request request) {
		return resource.handle(request);
	}

	public Representation handleInbound(Response response) {
		return resource.handleInbound(response);
	}

	public Response handleOutbound(Request request) {
		return resource.handleOutbound(request);
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

	public boolean isFollowingRedirects() {
		return resource.isFollowingRedirects();
	}

	public boolean isRequestEntityBuffering() {
		return resource.isRequestEntityBuffering();
	}

	public boolean isResponseEntityBuffering() {
		return resource.isResponseEntityBuffering();
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

	public <T> T post(Object entity, Class<T> resultClass) throws ResourceException {
		return resource.post(entity, resultClass);
	}

	public Representation post(Object entity, MediaType mediaType) throws ResourceException {
		return resource.post(entity, mediaType);
	}

	public Representation post(Object entity) throws ResourceException {
		return resource.post(entity);
	}

	public Representation post(Representation entity) throws ResourceException {
		return resource.post(entity);
	}

	public <T> T put(Object entity, Class<T> resultClass) throws ResourceException {
		return resource.put(entity, resultClass);
	}

	public Representation put(Object entity, MediaType mediaType) throws ResourceException {
		return resource.put(entity, mediaType);
	}

	public Representation put(Object entity) throws ResourceException {
		return resource.put(entity);
	}

	public Representation put(Representation entity) throws ResourceException {
		return resource.put(entity);
	}

	public void setAttribute(String name, Object value) {
		resource.setAttribute(name, value);
	}

	public void setChallengeResponse(ChallengeResponse challengeResponse) {
		resource.setChallengeResponse(challengeResponse);
	}

	public void setChallengeResponse(ChallengeScheme scheme, String identifier, String secret) {
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

	public void setEntityBuffering(boolean entityBuffering) {
		resource.setEntityBuffering(entityBuffering);
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

	public void setLoggable(boolean loggable) {
		resource.setLoggable(loggable);
	}

	public void setMaxRedirects(int maxRedirects) {
		resource.setMaxRedirects(maxRedirects);
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

	public void setProxyChallengeResponse(ChallengeResponse challengeResponse) {
		resource.setProxyChallengeResponse(challengeResponse);
	}

	public void setProxyChallengeResponse(ChallengeScheme scheme, String identifier, String secret) {
		resource.setProxyChallengeResponse(scheme, identifier, secret);
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

	public void setRequestEntityBuffering(boolean requestEntityBuffering) {
		resource.setRequestEntityBuffering(requestEntityBuffering);
	}

	public void setResponseEntityBuffering(boolean responseEntityBuffering) {
		resource.setResponseEntityBuffering(responseEntityBuffering);
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

	public <T> T wrap(Class<? extends T> resourceInterface) {
		return resource.wrap(resourceInterface);
	}
	
	
	
}
