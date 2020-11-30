package com.ssafy.catchcam.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;

public class SignUpRequest {

	@NotNull
	@Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
	@ApiModelProperty(value = "이메일", dataType = "String", required = true)
	private String email;

	@NotNull
//	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
	@ApiModelProperty(value = "비밀번호", dataType = "String", required = true)
	private String password;
	
	@NotNull
	@ApiModelProperty(value = "닉네임", dataType = "String", required = true)
	private String userName;

	public SignUpRequest() {

	}

	public SignUpRequest(String email, String password, String userName) {
		this.email = email;
		this.password = password;
		this.userName = userName;
	}

	public String getEmail() {
		return email;
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


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "SignUpRequest [email=" + email + ", password=" + password + ", userName=" + userName + "]";
	}

}
