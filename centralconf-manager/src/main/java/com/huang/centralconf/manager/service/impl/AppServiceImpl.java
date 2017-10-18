package com.huang.centralconf.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huang.centralconf.core.common.YtException;
import com.huang.centralconf.manager.entry.po.App;
import com.huang.centralconf.manager.entry.vo.AppVo;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.exception.YtfmUserErrors;
import com.huang.centralconf.manager.mapper.AppEnvMapper;
import com.huang.centralconf.manager.mapper.AppMapper;
import com.huang.centralconf.manager.service.AppService;
import com.huang.centralconf.manager.service.EnvService;
import com.huang.centralconf.manager.util.IdGen;

@SuppressWarnings("ALL")
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    AppMapper appMapper;
    @Autowired
    AppEnvMapper appEnvMapper;
    @Autowired
    Environment env;
    @Autowired
    EnvService envService;

    @Override
    public JsonPage<AppVo> getAllAppByPage(MyPage myPage, Long userId, Integer type) {
        Page<AppVo> page = PageHelper.startPage(myPage.getPage(), myPage.getResults(), true);
        List<App> permList = appMapper.selectAllByUserId(userId, type);
        List<AppVo> result = new ArrayList<AppVo>();
        JsonPage<AppVo> resultList = new JsonPage<AppVo>(page);
        for (App App : permList) {
            AppVo AppVo = new AppVo();
            try {
                BeanUtils.copyProperties(App,AppVo);
            } catch (Exception e) {
                throw new YtException(YtfmUserErrors.COPY_PROPRER);
            }
            result.add(AppVo);
        }
        resultList.setRows(result);
        return resultList;
    }

    // public List<App> getAllApp() {
    // return appMapper.selectAll();
    // }

    @Override
    public void createApp(AppVo appVo) {
        App app = new App();
        try {
            BeanUtils.copyProperties(appVo,app);
        } catch (Exception e) {
            throw new YtException(YtfmUserErrors.COPY_PROPRER);
        }
        final IdGen idGen = IdGen.get();
        app.setId(idGen.nextId());
        app.setCreateTime(System.currentTimeMillis());
        appMapper.insert(app);
        envService.createCommonEnv(app.getId());
    }

    @Override
    public AppVo getAppById(Long id) {
        App app = appMapper.selectByPrimaryKey(id);
        AppVo appVo = new AppVo();
        try {
            BeanUtils.copyProperties(app,appVo);
        } catch (Exception e) {
            throw new YtException(YtfmUserErrors.COPY_PROPRER);
        }
        return appVo;
    }

    @Override
    public void delApp(Long id) {
        appMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateApp(AppVo AppVo) {
        App App = new App();
        try {
            BeanUtils.copyProperties(AppVo,App);
        } catch (Exception e) {
            throw new YtException(YtfmUserErrors.COPY_PROPRER);
        }
        appMapper.updateByPrimaryKeySelective(App);
    }

    @Override
    public AppVo getAppByName(String appName) {
        App condition = new App();
        condition.setAppName(appName);
        App app = appMapper.selectOne(condition);
        if (app == null) {
            return null;
        }
        AppVo appVo = new AppVo();
        try {
            BeanUtils.copyProperties(app,appVo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new YtException(YtfmUserErrors.COPY_PROPRER);
        }
        return appVo;
    }

}
