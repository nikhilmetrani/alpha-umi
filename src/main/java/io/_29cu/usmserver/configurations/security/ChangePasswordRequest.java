package io._29cu.usmserver.configurations.security;

import java.io.Serializable;

public class ChangePasswordRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String currentPwd;
	private String newPwd;

	public String getCurrentPwd() {
		return currentPwd;
	}

	public void setCurrentPwd(String currentPwd) {
		this.currentPwd = currentPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
}
