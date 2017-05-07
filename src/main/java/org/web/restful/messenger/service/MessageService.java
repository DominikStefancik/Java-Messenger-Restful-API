package org.web.restful.messenger.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.web.restful.messenger.database.DatabaseMock;
import org.web.restful.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> allMessages = DatabaseMock.getMessages();
	private static MessageService	instance;

	public static MessageService getInstance() {
		if (instance == null) {
			instance = new MessageService();
		}
		return instance;
	}

	private MessageService() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		try {
			Message message1 = new Message(1L, "First message", "dominik", dateFormat.parse("04.11.2013 08:14:45"));
			Message message2 = new Message(2L, "Second message", "josh", dateFormat.parse("25.09.2015 08:14:45"));
			Message message3 = new Message(3L, "Third message", "stefancik", dateFormat.parse("14.06.2014 08:14:45"));
			Message message4 = new Message(4L, "Fourth message", "jana",
			    dateFormat.parse("08.02.2015 08:14:45"));

			CommentService commentService = CommentService.getInstance();

			Arrays.asList(message1, message2, message3, message4)
			    .forEach(message -> {
				    commentService.createMockComments(message);
				    allMessages.put(message.getId(), message);
			    });
		} catch (ParseException ex) {
			ex.printStackTrace();
		}


		// allMessages.put(message1.getId(), message1);
		// allMessages.put(message2.getId(), message2);
		// allMessages.put(message3.getId(), message3);
		// allMessages.put(message4.getId(), message4);
	}

	public List<Message> getAllMessages() {
		System.out.println("GET all messages: " + allMessages);
		return new ArrayList<>(allMessages.values());
	}

	public List<Message> getMessagesByYear(int year) {
		List<Message> messagesByYear = getAllMessages().stream().filter(message -> {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(message.getCreated());
			return calendar.get(Calendar.YEAR) == year;
		}).collect(Collectors.toList());

		return messagesByYear;
	}

	public List<Message> getMessagesPaginated(int page, int size) {
		List<Message> allMessages = getAllMessages();
		int messagesCount = allMessages.size();

		int fromIndex = (page - 1) * size;
		int toIndex = fromIndex + size > messagesCount ? messagesCount : fromIndex + size;

		if (fromIndex >= messagesCount) {
			return null;
		}

		return allMessages.subList(fromIndex, toIndex);
	}

	public Message getMessage(long id) {
		return allMessages.get(id);
	}

	public Message addMessage(Message message) {
		if (message == null) {
			return null;
		}

		message.setId(allMessages.size() + 1);
		allMessages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message == null) {
			return null;
		}

		allMessages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(long id) {
		return allMessages.remove(id);
	}

}
