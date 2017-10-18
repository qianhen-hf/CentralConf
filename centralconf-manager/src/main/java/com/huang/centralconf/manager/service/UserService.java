package com.huang.centralconf.manager.service;

import com.huang.centralconf.manager.entry.po.User;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.entry.vo.UserVo;

@SuppressWarnings("ALL")
public interface UserService {

	public JsonPage<UserVo> getUserByPage(MyPage myPage);

	public void createUser(UserVo userVo);

	public User getUserByName(String userName);

	public void delUser(Long id);

	public void updateUser(UserVo userVo) throws Exception;

	public Boolean isUserExist(String userName);

	public void savePermApp(String value, String userName) throws Exception;

	public Object getPermApp(String userName) throws Exception;

	public Object getPerm(Long userId);

	public void savePerm(String value, Long userId) throws Exception;
}
