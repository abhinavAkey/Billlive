package com.beatus.billlive.auth;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.google.firebase.tasks.OnSuccessListener;
import com.google.firebase.tasks.Task;

/**
 * Auth snippets for documentation.
 *
 * See: https://firebase.google.com/docs/auth/admin
 */
@Component(value = "firebaseUserConnection")
public class FirebaseUserConnection {

    private static final Logger LOG = LoggerFactory.getLogger(FirebaseUserConnection.class);

	public static String uid = null;
	
	@Resource(name="firebaseAuth")
	FirebaseAuth firebaseAuth;

	public Task<UserRecord> getUserById(String uid) {
		// [START get_user_by_id]
		LOG.info("In method firebase auth");
		Task<UserRecord> task = firebaseAuth.getUser(uid).addOnSuccessListener(userRecord -> {
			// See the UserRecord reference doc for the contents of userRecord.
			LOG.info("Successfully fetched user data: " + userRecord.getUid());
		}).addOnFailureListener(e -> {
			LOG.error("Error fetching user data: " + e.getMessage());
		});
		// [END get_user_by_id]
		return task;
	}

	public String verifyIdToken(String authToken) {
		// [START verifyIdToken]		
		Task<FirebaseToken> token = firebaseAuth.verifyIdToken(authToken)
				.addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
					@Override
					public void onSuccess(FirebaseToken decodedToken) {
						uid = decodedToken.getUid();
					}
				});
		if(token != null && token.getResult() != null && StringUtils.isNotBlank(token.getResult().getUid())){
			return uid;
		}
		return null;
	}

	public Task<UserRecord> getUserByEmail(String email) {
		// [START get_user_by_email]
		Task<UserRecord> task = firebaseAuth.getUserByEmail(email).addOnSuccessListener(userRecord -> {
			// See the UserRecord reference doc for the contents of userRecord.
			System.out.println("Successfully fetched user data: " + userRecord.getEmail());
		}).addOnFailureListener(e -> {
			System.err.println("Error fetching user data: " + e.getMessage());
		});
		// [END get_user_by_email]

		return task;
	}

	public Task<UserRecord> createUser() {
		// [START create_user]
		CreateRequest request = new CreateRequest().setEmail("user@example.com").setEmailVerified(false)
				.setPassword("secretPassword").setDisplayName("John Doe")
				.setPhotoUrl("http://www.example.com/12345678/photo.png").setDisabled(false);

		Task<UserRecord> task = firebaseAuth.createUser(request).addOnSuccessListener(userRecord -> {
			// See the UserRecord reference doc for the contents of userRecord.
			System.out.println("Successfully created new user: " + userRecord.getUid());
		}).addOnFailureListener(e -> {
			System.err.println("Error creating new user: " + e.getMessage());
		});
		// [END create_user]

		return task;
	}

	public Task<UserRecord> createUser(String email, String password) {
		// [START create_user]
		CreateRequest request = new CreateRequest().setEmail(email).setEmailVerified(false).setPassword(password)
				.setDisabled(false);

		Task<UserRecord> task = firebaseAuth.createUser(request).addOnSuccessListener(userRecord -> {
			// See the UserRecord reference doc for the contents of userRecord.
			System.out.println("Successfully created new user: " + userRecord.getUid());
		}).addOnFailureListener(e -> {
			System.err.println("Error creating new user: " + e.getMessage());
		});
		// [END create_user]

		return task;
	}

	public Task<UserRecord> createUserWithUid() {
		// [START create_user_with_uid]
		CreateRequest request = new CreateRequest().setUid("some-uid").setEmail("user@example.com");

		Task<UserRecord> task = firebaseAuth.createUser(request).addOnSuccessListener(userRecord -> {
			// See the UserRecord reference doc for the contents of userRecord.
			System.out.println("Successfully created new user: " + userRecord.getUid());
		}).addOnFailureListener(e -> {
			System.err.println("Error creating new user: " + e.getMessage());
		});
		// [END create_user_with_uid]

		return task;
	}

	public Task<UserRecord> updateUser(String uid) {
		// [START update_user]
		UpdateRequest request = new UpdateRequest(uid).setEmail("user@example.com").setEmailVerified(true)
				.setPassword("newPassword").setDisplayName("Jane Doe")
				.setPhotoUrl("http://www.example.com/12345678/photo.png").setDisabled(true);

		Task<UserRecord> task = firebaseAuth.updateUser(request).addOnSuccessListener(userRecord -> {
			// See the UserRecord reference doc for the contents of userRecord.
			System.out.println("Successfully updated user: " + userRecord.getUid());
		}).addOnFailureListener(e -> {
			System.err.println("Error updating user: " + e.getMessage());
		});
		// [END update_user]

		return task;
	}

	public Task<Void> deleteUser(String uid) {
		// [START delete_user]
		Task<Void> task = firebaseAuth.deleteUser(uid)
				.addOnSuccessListener(aVoid -> System.out.println("Successfully deleted user."))
				.addOnFailureListener(e -> System.err.println("Error updating user: " + e.getMessage()));
		// [END delete_user]

		return task;
	}
}