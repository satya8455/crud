package com.ncm.crud.dto;

public class UserVo {
private String Name;
	
	private String lastname;
	
	private String mobile;
	private String email;
private String password;
	
	public String getName() {
	return Name;
}

public void setName(String name) {
	Name = name;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getMobile() {
	return mobile;
}

public void setMobile(String mobile) {
	this.mobile = mobile;
}

public String getEmail() {
	return email;
}

@Override
public String toString() {
	return "UserVo [Name=" + Name + ", lastname=" + lastname + ", mobile=" + mobile + ", email=" + email + ", password="
			+ password + ", confirmPassword=" + confirmPassword + "]";
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getConfirmPassword() {
	return confirmPassword;
}

public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}

	private String confirmPassword;
}
