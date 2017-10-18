package com.huang.centralconf.manager.mapper;

import com.huang.centralconf.manager.entry.po.User;

import tk.mybatis.mapper.common.BaseMapper;

@SuppressWarnings("ALL")
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 用户
	 * 
	 * @param userName
	 */

	public User getUserByName(String userName);

	public void delAllUser();

	public Boolean isUserExist(String userName);

	public User findUser(Long id);
}
