package com.huang.centralconf.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huang.centralconf.core.common.YtException;
import com.huang.centralconf.manager.entry.po.User;
import com.huang.centralconf.manager.entry.po.UserPerm;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.entry.vo.UserVo;
import com.huang.centralconf.manager.exception.YtfmUserErrors;
import com.huang.centralconf.manager.mapper.UserMapper;
import com.huang.centralconf.manager.mapper.UserPermMapper;
import com.huang.centralconf.manager.service.UserService;
import com.huang.centralconf.manager.util.IdGen;

@SuppressWarnings("ALL")
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserPermMapper userPermMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public JsonPage<UserVo> getUserByPage(MyPage myPage) {
        Page<UserVo> page = PageHelper.startPage(myPage.getPage(), myPage.getResults(), true);
        List<User> userList = userMapper.selectAll();
        List<UserVo> result = new ArrayList<UserVo>();
        JsonPage<UserVo> resultList = new JsonPage<UserVo>(page);
        for (User user : userList) {
            UserVo userVo = new UserVo();
            try {
                BeanUtils.copyProperties(user,userVo);
            } catch (Exception e) {
                throw new YtException(YtfmUserErrors.COPY_PROPRER);
            }
            result.add(userVo);
        }
        resultList.setRows(result);
        return resultList;
    }

    @Override
    public void createUser(UserVo userVo) {
        User user = new User();
        try {
            BeanUtils.copyProperties(userVo,user);
        } catch (Exception e) {
            throw new YtException(YtfmUserErrors.COPY_PROPRER);
        }
        Random random = new Random();
        String salt = "";
        for (int i = 0; i < 2; i++) {
            salt += (char) (random.nextInt(26) + 97);
        }
        user.setSalt(salt);
        user.setId(IdGen.get().nextId());
        user.setPassword("root");
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1);
        user.setPassword(md5Hash.toString());
        userMapper.insert(user);

    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    @Override
    public void delUser(Long id) {
        userMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void updateUser(UserVo userVo) throws Exception {
        User user = new User();
        try {
            BeanUtils.copyProperties(userVo, user);
        } catch (Exception e) {
            throw new YtException(YtfmUserErrors.COPY_PROPRER);
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    // public void delAllUser() {
    // userMapper.delAllUser();
    // }
    @Override
    public Boolean isUserExist(String userName) {
        return userMapper.isUserExist(userName);
    }

    @Override
    public void savePermApp(String value, String userName) throws Exception {
        Map<String, Map<String, Object>> map;
        try {
            map = objectMapper.readValue(value, Map.class);
        } catch (Exception e) {
            throw new YtException(YtfmUserErrors.MAPPER_VALUE);
        }
        User user = getUserByName(userName);
        if (user == null) {
            throw new YtException(YtfmUserErrors.USER_EMPTY);
        }
        // user.setApp(map);
        // userMapper.updateUser(user);
    }

    @Override
    public Object getPermApp(String userName) throws Exception {
        User user = getUserByName(userName);
        if (user == null) {
            throw new YtException(YtfmUserErrors.USER_EMPTY);
        }
        // return user.getApp();
        return null;
    }

    @Override
    public Object getPerm(Long userId) {
        UserPerm userPermSelect = new UserPerm();
        userPermSelect.setUserId(userId);
        List<UserPerm> userPermList = userPermMapper.select(userPermSelect);
        List<Long> perms = new ArrayList<Long>();
        for (UserPerm up : userPermList) {
            perms.add(up.getPermId());
        }
        return perms;
    }

    @Override
    public void savePerm(String value, Long userId) throws Exception {
        List<String> perm;
        try {
            perm = objectMapper.readValue(value, ArrayList.class);
        } catch (Exception e) {
            throw new YtException(YtfmUserErrors.MAPPER_VALUE);
        }
        UserPerm userPermDel = new UserPerm();
        userPermDel.setUserId(userId);
        userPermMapper.delete(userPermDel);
        for (String p : perm) {
            UserPerm userPerm = new UserPerm();
            userPerm.setId(IdGen.get().nextId());
            userPerm.setUserId(userId);
            userPerm.setPermId(Long.parseLong(p));
            userPermMapper.insert(userPerm);
        }
    }

}
