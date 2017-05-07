package org.web.restful.messenger.util;

import java.lang.reflect.Method;
import java.net.URI;

import javax.ws.rs.core.UriInfo;

import org.web.restful.messenger.model.Link;
import org.web.restful.messenger.model.Message;
import org.web.restful.messenger.model.Profile;
import org.web.restful.messenger.resources.MessageResource;
import org.web.restful.messenger.resources.ProfileResource;

public class ResourceUtil {

	private static final String	SELF			= "self";
	private static final String	AUTHOR		= "author";
	private static final String	COMMENTS	= "comments";

	private ResourceUtil() {

	}

	public static Link createMessageLink(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(String.valueOf(message.getId())).build();

		return new Link(uri.toString(), SELF);
	}

	public static Link createProfileLink(UriInfo uriInfo, Profile profile) {
		URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(String.valueOf(profile.getProfileName()))
		    .build();

		return new Link(uri.toString(), AUTHOR);
	}

	public static Link createCommentsLink(UriInfo uriInfo, Message message, Method method, String templateVariable) {
		URI uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(method)
		    .resolveTemplate(templateVariable, message.getId()).build();


		return new Link(uri.toString(), COMMENTS);
	}
}
