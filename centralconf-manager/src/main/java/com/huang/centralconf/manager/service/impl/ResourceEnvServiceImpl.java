package com.huang.centralconf.manager.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huang.centralconf.core.common.YtException;
import com.huang.centralconf.manager.entry.po.ResEnv;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.entry.vo.ResEnvVo;
import com.huang.centralconf.manager.exception.YtfmUserErrors;
import com.huang.centralconf.manager.mapper.ResEnvMapper;
import com.huang.centralconf.manager.service.ResourceEnvService;
import com.huang.centralconf.manager.util.ConvertUtil;
import com.huang.centralconf.manager.util.IdGen;

@SuppressWarnings("ALL")
@Service
@Transactional
public class ResourceEnvServiceImpl implements ResourceEnvService {
  @Autowired
  ResEnvMapper resEnvMapper;

  @Override
  @Override
  public JsonPage<ResEnvVo> getEnv(Long resId, Long id, Integer type, MyPage myPage) {
    Page<ResEnvVo> page = PageHelper.startPage(myPage.getPage(), myPage.getResults(), true);
    JsonPage<ResEnvVo> resultList = new JsonPage<ResEnvVo>(page);
    List<ResEnv> envList = resEnvMapper.geEnv(resId, id, type);
    resultList.setRows(ConvertUtil.copyTo(envList, ResEnvVo.class));
    return resultList;
  }
  @Override
  @Override
  public ResEnvVo getEnvByName(Long resId, String envName) {
    ResEnv resEnvWhere = new ResEnv();
    resEnvWhere.setResId(resId);
    resEnvWhere.setEnvName(envName);
    ResEnv resEnv = resEnvMapper.selectOne(resEnvWhere);
    if (resEnv == null) {
      return null;
    }
    ResEnvVo resEnvVo = new ResEnvVo();
    try {
      BeanUtils.copyProperties(resEnv,resEnvVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return resEnvVo;
  }

  @Override
  @Override
  public void addenv(ResEnvVo resEnvVo) {
    ResEnv resEnv = new ResEnv();
    try {
      BeanUtils.copyProperties(resEnvVo,resEnv);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    resEnv.setId(IdGen.get().nextId());
    resEnv.setCreateTime(System.currentTimeMillis());
    resEnvMapper.insert(resEnv);
  }

  @Override
  @Override
  public void delEnv(Long envId) {
    resEnvMapper.deleteByPrimaryKey(envId);
    // UserAppEnv userAppEnv = new UserAppEnv();
    // userAppEnv.setEnvId(envId);
    // userAppEnvMapper.delete(userAppEnv);
  }

  @Override
  @Override
  public ResEnvVo getEnvById(Long envId) {
    ResEnv resEnv = resEnvMapper.selectByPrimaryKey(envId);
    if (resEnv == null) {
      return null;
    }
    ResEnvVo resEnvVo = new ResEnvVo();
    try {
      BeanUtils.copyProperties(resEnv,resEnvVo);
    } catch (Exception e) {
      throw new YtException(YtfmUserErrors.COPY_PROPRER);
    }
    return resEnvVo;
  }

  @Override
  @Override
  public List<ResEnvVo> getAllEnvByResId(Long resId) {
    ResEnv resEnvCon = new ResEnv();
    resEnvCon.setResId(resId);
    List<ResEnv> selectAll = resEnvMapper.select(resEnvCon);
    return ConvertUtil.copyTo(selectAll, ResEnvVo.class);
  }

}
