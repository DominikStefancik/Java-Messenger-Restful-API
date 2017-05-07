package org.web.restful.messenger.resources;

import java.util.ArrayList;
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
import javax.ws.rs.core.MediaType;

import org.web.restful.messenger.model.Comment;
import org.web.restful.messenger.service.CommentService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	private CommentService commentService = CommentService.getInstance();

	@GET
	public List<Comment> getComments(@PathParam("messageId") long messageId, @QueryParam("author") String author) {

		if (author != null) {
			return commentService.getCommentsByAuthor(messageId, author);
		} else {
			return new ArrayList<>(commentService.getAllComments(messageId).values());
		}
	}

	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment) {
		return commentService.addComment(messageId, comment);
	}

	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		return commentService.getComment(messageId, commentId);
	}

	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId,
	    Comment comment) {
		comment.setId(commentId);
		return commentService.updateComment(messageId, comment);
	}

	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		commentService.deleteComment(messageId, commentId);
	}
}
