package org.web.restful.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.web.restful.messenger.exception.DataNotFoundException;
import org.web.restful.messenger.exception.UpdateException;
import org.web.restful.messenger.model.Message;
import org.web.restful.messenger.service.MessageService;
import org.web.restful.messenger.service.ProfileService;
import org.web.restful.messenger.util.ResourceUtil;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	private MessageService		messageService						= MessageService.getInstance();
	private ProfileService		profileService						= ProfileService.getInstance();
	private static final int	PRIVATE_MESSAGES_START_ID	= 500;

	@GET
	public List<Message> getMessages(@QueryParam("year") int year, @QueryParam("page") int page,
	    @QueryParam("size") int size) {

		if (year > 0) {
			return messageService.getMessagesByYear(year);
		} else if (page > 0 && size > 0) {
			return messageService.getMessagesPaginated(page, size);
		} else {
			return messageService.getAllMessages();
		}
	}

	@POST
	public Response addMesssage(@Context UriInfo uriInfo, Message message) {
		Message newMessage = messageService.addMessage(message);
		String messageId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(messageId).build();
		return Response.created(uri).entity(newMessage).build();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);

		if (message == null) {
			throw new DataNotFoundException("The message with id " + id + " doesn't exist.");
		}
		
		message.getLinks().add(ResourceUtil.createMessageLink(uriInfo, message));
		message.getLinks().add(
		    ResourceUtil.createProfileLink(uriInfo, profileService.getProfile(message.getAuthor())));

		try {
			message.getLinks().add(ResourceUtil.createCommentsLink(uriInfo, message,
			    MessageResource.class.getMethod("delegateToCommentResource"), "messageId"));
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		}

		return message;
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		if (id >= PRIVATE_MESSAGES_START_ID) {
			throw new UpdateException(
			    "Messages with id " + PRIVATE_MESSAGES_START_ID + " or higher are private and cannot be changed");
		}
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messsageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	@Path("/{messageId}/comments")
	public CommentResource delegateToCommentResource() {
		return new CommentResource();
	}

}
