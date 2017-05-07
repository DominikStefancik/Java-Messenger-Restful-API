package org.web.restful.messenger.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.web.restful.messenger.database.DatabaseMock;
import org.web.restful.messenger.model.Comment;
import org.web.restful.messenger.model.Message;

public class CommentService {

	private Map<Long, Message>		messages	= DatabaseMock.getMessages();
	private static CommentService	instance;

	private CommentService() {

	}

	public static CommentService getInstance() {
		if (instance == null) {
			instance = new CommentService();
		}

		return instance;
	}

	public Map<Long, Comment> getAllComments(long messageId) {
		Message message = messages.get(messageId);

		if (message == null) {
			return new HashMap<>();
		}

		return message.getComments();
	}

	public List<Comment> getCommentsByAuthor(long messageId, String author) {
		Map<Long, Comment> allComments = getAllComments(messageId);

		List<Comment> commentsList = allComments.values().stream()
		    .filter(comment -> comment.getAuthor() != null && comment.getAuthor().equals(author))
		    .collect(Collectors.toList());

		return commentsList;
	}

	public Comment getComment(long messageId, long commentId) {
		Map<Long, Comment> allComments = getAllComments(messageId);

		return allComments.get(commentId);
	}

	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> allComments = getAllComments(messageId);

		comment.setId(allComments.size() + 1);
		allComments.put(comment.getId(), comment);
		return comment;
	}

	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> allComments = getAllComments(messageId);

		allComments.put(comment.getId(), comment);
		return comment;
	}

	public void deleteComment(long messageId, long commentId) {
		Map<Long, Comment> allComments = getAllComments(messageId);
		
		allComments.remove(commentId);
	}

	public void createMockComments(Message message) {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		try {
			Comment comment1 = new Comment(1L, "First comment", "Murco", dateFormat.parse("04.11.2013 08:14:45"));
			Comment comment2 = new Comment(2L, "Second comment", "Drbo", dateFormat.parse("25.09.2015 08:14:45"));
			Comment comment3 = new Comment(3L, "Third comment", "Crossfitak", dateFormat.parse("14.06.2014 08:14:45"));
			Comment comment4 = new Comment(4L, "Fourth comment", "Drbo", dateFormat.parse("08.02.2015 08:14:45"));
			Comment comment5 = new Comment(5L, "Fifth comment", "Murco", dateFormat.parse("12.02.2015 08:14:45"));
			Comment comment6 = new Comment(6L, "Sixth comment", "Drbo", dateFormat.parse("20.04.2016 08:14:45"));
			Comment comment7 = new Comment(7L, "Seventh comment", "Drbo", dateFormat.parse("21.03.2014 08:14:45"));

			Arrays.asList(comment1, comment2, comment3, comment4, comment5, comment6, comment7)
			    .forEach(comment -> message.getComments().put(comment.getId(), comment));
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
	}
}
