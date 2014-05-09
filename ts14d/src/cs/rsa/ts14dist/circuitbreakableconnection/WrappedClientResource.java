package cs.rsa.ts14dist.circuitbreakableconnection;

import java.net.URI;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.resource.ClientResource;

public class WrappedClientResource extends ClientResource implements PartialClientResource {

	public WrappedClientResource() {
		super();
	}

	public WrappedClientResource(ClientResource resource) {
		super(resource);
	}

	public WrappedClientResource(Context context, Method method,
			Reference reference) {
		super(context, method, reference);
	}

	public WrappedClientResource(Context context, Method method, String uri) {
		super(context, method, uri);
	}

	public WrappedClientResource(Context context, Method method, URI uri) {
		super(context, method, uri);
	}

	public WrappedClientResource(Context context, Reference reference) {
		super(context, reference);
	}

	public WrappedClientResource(Context context, Request request,
			Response response) {
		super(context, request, response);
	}

	public WrappedClientResource(Context context, Request request) {
		super(context, request);
	}

	public WrappedClientResource(Context context, String uri) {
		super(context, uri);
	}

	public WrappedClientResource(Context context, URI uri) {
		super(context, uri);
	}

	public WrappedClientResource(Method method, Reference reference) {
		super(method, reference);
	}

	public WrappedClientResource(Method method, String uri) {
		super(method, uri);
	}

	public WrappedClientResource(Method method, URI uri) {
		super(method, uri);
	}

	public WrappedClientResource(Reference reference) {
		super(reference);
	}

	public WrappedClientResource(Request request, Response response) {
		super(request, response);
	}

	public WrappedClientResource(Request request) {
		super(request);
	}

	public WrappedClientResource(String uri) {
		super(uri);
	}

	public WrappedClientResource(URI uri) {
		super(uri);
	}
	
	

}
