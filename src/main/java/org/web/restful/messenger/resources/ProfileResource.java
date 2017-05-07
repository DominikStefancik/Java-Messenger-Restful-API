package org.web.restful.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.web.restful.messenger.model.Profile;
import org.web.restful.messenger.service.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

	private ProfileService profileService = ProfileService.getInstance();

	@GET
	public List<Profile> getProfiles() {
		return profileService.getAllProfiles();
	}

	@POST
	public Profile addProfile(Profile profile) {
		return profileService.addProfile(profile);
	}

	@GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String name) {
		return profileService.getProfile(name);
	}

	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String name, Profile profile) {
		profile.setProfileName(name);
		return profileService.updateProfile(profile);
	}

	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName") String name) {
		profileService.removeProfile(name);
	}
}
