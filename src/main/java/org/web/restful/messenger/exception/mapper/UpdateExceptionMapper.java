package org.web.restful.messenger.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.web.restful.messenger.exception.UpdateException;
import org.web.restful.messenger.model.ErrorMessage;

@Provider
public class UpdateExceptionMapper implements ExceptionMapper<UpdateException> {

	@Override
	public Response toResponse(UpdateException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 403, "www.problem.com");
		return Response.status(Status.FORBIDDEN).entity(errorMessage).build();
	}

}
