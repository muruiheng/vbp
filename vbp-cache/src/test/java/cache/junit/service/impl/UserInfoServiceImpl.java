package cache.junit.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;

import cache.junit.entity.UserInfo;
import cache.junit.service.UserInfoService;

@EnableCaching
public class UserInfoServiceImpl implements UserInfoService {

	@Caching(
			put={
					@CachePut(value="user", key="\"user_\" + #user.id"),
					@CachePut(value="user", key="#user.name")
			}
			)
	@Override
	public void addUser(UserInfo user) {
		// TODO Auto-generated method stub

	}

	@Caching(
			put={
					@CachePut(value="user", key="\"user_\" + #user.id"),
					@CachePut(value="user", key="#user.name")
			}
			)
	@Override
	public void update(UserInfo user) {
		// TODO Auto-generated method stub

	}

	@CacheEvict(key = "\"user_\" + #id")
	@Override
	public void delUser(UserInfo user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void query(String userName) {
		// TODO Auto-generated method stub

	}

}
