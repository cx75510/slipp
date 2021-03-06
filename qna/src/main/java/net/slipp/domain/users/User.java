package net.slipp.domain.users;

import javax.validation.constraints.Size;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Alias("user")
public class User {
	@NotEmpty
	@Size(min = 4, max = 12)
	private String userId;
	
	@NotEmpty
	@Size(min = 4, max = 12)
	private String password;
	
	@NotEmpty
	private String name;
	
	@Email
	private String email;

	// ������
	public User(String userId, String password, String name, String email) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}
	
	public User() {
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean matchPassword(Authenticate authenticate) {
		if(this.password == null){
			return false;
		}
		return authenticate.matchPassword(this.password);
	}
	
	public boolean isSameUser(String newUserId){
		if(this.userId == null){
			return false;
		}
		return this.userId.equals(newUserId);
	}
	

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public boolean matchUserId(String inputUserId) {
		if(inputUserId == null){
			return false;
		}
		return inputUserId.equals(this.userId);
	}

	public User update(User updateUser) {
		if(!matchUserId(updateUser.userId)){
			throw new IllegalArgumentException();
		}
		
		return new User(this.userId, updateUser.password, updateUser.name, updateUser.email);
	}
	
}
