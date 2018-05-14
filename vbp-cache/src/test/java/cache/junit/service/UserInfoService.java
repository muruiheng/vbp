package cache.junit.service;

import cache.junit.entity.UserInfo;

public interface UserInfoService {

	public void addUser(UserInfo user);
	
	public void update(UserInfo user);
	
	public void delUser(UserInfo user);
	
	public void query(String userName);
	
}
