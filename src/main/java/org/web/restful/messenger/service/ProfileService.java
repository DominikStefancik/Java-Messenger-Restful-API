package org.web.restful.messenger.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.web.restful.messenger.database.DatabaseMock;
import org.web.restful.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> allProfiles = DatabaseMock.getProfiles();
	private static ProfileService	instance;

	public static ProfileService getInstance() {
		if (instance == null) {
			instance = new ProfileService();
		}
		return instance;
	}

	private ProfileService() {
		Profile profile1 = new Profile(1L, "dominik", "Dominik", "Stefancik");
		Profile profile2 = new Profile(2L, "jana", "Jana", "Novosadova");
		Profile profile3 = new Profile(3L, "josh", "Josh", "Bridges");
		Profile profile4 = new Profile(3L, "stefancik", "Jan", "Stefancik");

		Arrays.asList(profile1, profile2, profile3, profile4)
		    .forEach(profile -> allProfiles.put(profile.getProfileName(), profile));
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<>(allProfiles.values());
	}

	public Profile getProfile(String profileName) {
		return allProfiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		if (profile == null || profile.getProfileName().isEmpty()) {
			return null;
		}

		profile.setId(allProfiles.size() + 1);
		allProfiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile == null || profile.getProfileName().isEmpty()) {
			return null;
		}

		allProfiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public void removeProfile(String profileName) {
		allProfiles.remove(profileName);
	}
}
