package net.slipp.domain.users;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Authenticate {
	private String userId;
	
	private String password;
	
	
	public Authenticate(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public Authenticate() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

}
