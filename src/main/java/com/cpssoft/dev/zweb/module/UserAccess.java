package com.cpssoft.dev.zweb.module;

import com.cpssoft.dev.zweb.orm.UserEntity;

public class UserAccess {

	public UserAccess(UserEntity user) {
		// parse(user.getAccessData());
	}

	public boolean manageUser() {
		// get(UserAccessType.MANAGE_USER);
		return false;
	}

	public void setManageUser(boolean value) {
		// set(UserAccessType.MANAGE_USER, value);
	}

}
