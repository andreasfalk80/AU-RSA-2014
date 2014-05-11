package cs.rsa.ts14dist.circuitbreakableClientResource;

import java.net.URI;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.resource.ClientResource;
/**
 * The role as ConcreteComponent in the decorator pattern. 
 * A simple extension of org.restlet.resource.ClientResource, which only adds a declaration of implementing 
 * the ClientResourceInterface, so that the functionality that ClientResource presents, can be accessed in a decorator pattern.<br/>
 * This is the second part of a fix/hack to have a version of ClientResource, which implements a interface. 
 * See ClientResourceInterface for the first part.
 *
 */
public class SimpleClientResource extends ClientResource implements ClientResourceInterface {

	public SimpleClientResource() {
		super();
	}
	public SimpleClientResource(ClientResource resource) {
		super(resource);
	}

	public SimpleClientResource(Context context, Method method,
			Reference reference) {
		super(context, method, reference);
	}

	public SimpleClientResource(Context context, Method method, String uri) {
		super(context, method, uri);
	}

	public SimpleClientResource(Context context, Method method, URI uri) {
		super(context, method, uri);
	}

	public SimpleClientResource(Context context, Reference reference) {
		super(context, reference);
	}

	public SimpleClientResource(Context context, Request request,
			Response response) {
		super(context, request, response);
	}

	public SimpleClientResource(Context context, Request request) {
		super(context, request);
	}

	public SimpleClientResource(Context context, String uri) {
		super(context, uri);
	}

	public SimpleClientResource(Context context, URI uri) {
		super(context, uri);
	}

	public SimpleClientResource(Method method, Reference reference) {
		super(method, reference);
	}

	public SimpleClientResource(Method method, String uri) {
		super(method, uri);
	}

	public SimpleClientResource(Method method, URI uri) {
		super(method, uri);
	}

	public SimpleClientResource(Reference reference) {
		super(reference);
	}

	public SimpleClientResource(Request request, Response response) {
		super(request, response);
	}

	public SimpleClientResource(Request request) {
		super(request);
	}

	public SimpleClientResource(String uri) {
		super(uri);
	}

	public SimpleClientResource(URI uri) {
		super(uri);
	}
}
