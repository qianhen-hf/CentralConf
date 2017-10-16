package com.huang.centralconf.manager.web.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.huang.centralconf.core.common.Errorcode;
import com.huang.centralconf.core.common.WebResponse;
import com.huang.centralconf.core.common.YtException;
import com.huang.centralconf.manager.exception.YtfmUserErrors;

@RestController
@ControllerAdvice
public class ExceptionHandlerController {

	private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(Exception.class)
	public WebResponse exceptionHandler(Exception e) {

		WebResponse br = new WebResponse();
		logger.error("", e);
		br.setCode(-1);
		br.setMsg("inner error:" + e.getMessage());
		return br;
	}

	@ExceptionHandler(YtException.class)
	public WebResponse ytExceptionHandler(YtException e) {
		WebResponse br = new WebResponse();
		Errorcode code = e.getErrorcode();
		if (code.getCode() == YtfmUserErrors.PARAMETER_VALUE_EMPTY.getCode()) {
			br.setCode(YtfmUserErrors.PARAMETER_VALUE_EMPTY.getCode());
			br.setMsg(YtfmUserErrors.PARAMETER_VALUE_EMPTY.getDesc());
		} else if (code.getCode() == YtfmUserErrors.USER_DISABLE.getCode()) {
			br.setCode(YtfmUserErrors.USER_DISABLE.getCode());
			br.setMsg(YtfmUserErrors.USER_DISABLE.getDesc());
		} else if (code.getCode() == YtfmUserErrors.USER_EMPTY.getCode()) {
			br.setCode(YtfmUserErrors.USER_EMPTY.getCode());
			br.setMsg(YtfmUserErrors.USER_EMPTY.getDesc());
		} else if (code.getCode() == YtfmUserErrors.ASSIGNED_ORG_EMPTY.getCode()) {
			br.setCode(YtfmUserErrors.ASSIGNED_ORG_EMPTY.getCode());
			br.setMsg(YtfmUserErrors.ASSIGNED_ORG_EMPTY.getDesc());
		} else if (code.getCode() == YtfmUserErrors.ORGID_EMPTY.getCode()) {
			br.setCode(YtfmUserErrors.ORGID_EMPTY.getCode());
			br.setMsg(YtfmUserErrors.ORGID_EMPTY.getDesc());
		} else if (code.getCode() == YtfmUserErrors.ORG_MATCHING.getCode()) {
			br.setCode(YtfmUserErrors.ORG_MATCHING.getCode());
			br.setMsg(YtfmUserErrors.ORG_MATCHING.getDesc());
		} else if (code.getCode() == YtfmUserErrors.USER_EXIST.getCode()) {
			br.setCode(YtfmUserErrors.USER_EXIST.getCode());
			br.setMsg(YtfmUserErrors.USER_EXIST.getDesc());
		} else if (code.getCode() == YtfmUserErrors.MAPPER_VALUE.getCode()) {
			br.setCode(YtfmUserErrors.MAPPER_VALUE.getCode());
			br.setMsg(YtfmUserErrors.MAPPER_VALUE.getDesc());
		} else if (code.getCode() == YtfmUserErrors.COPY_PROPRER.getCode()) {
			br.setCode(YtfmUserErrors.COPY_PROPRER.getCode());
			br.setMsg(YtfmUserErrors.COPY_PROPRER.getDesc());
		} else {
			logger.error("not process ytexception", e);
			br.setCode(-1);
			br.setMsg("inner error");
		}

		br.setData(((YtException) e).getExtraInfo());
		return br;
	}

	@ExceptionHandler(UnknownAccountException.class)
	public WebResponse unknownAccountException(UnknownAccountException e) {
		WebResponse br = new WebResponse();
		br.setCode(201);
		br.setMsg("用户名或密码错误");
		logger.error("用户名或密码错误", e);
		return br;
	}

	@ExceptionHandler(IncorrectCredentialsException.class)
	public WebResponse incorrectCredentialsException(IncorrectCredentialsException e) {
		WebResponse br = new WebResponse();
		br.setCode(201);
		br.setMsg("用户名或密码错误");
		logger.error("密码错误", e);
		return br;
	}

	@ExceptionHandler(AuthenticationException.class)
	public WebResponse authenticationException(AuthenticationException e) {
		WebResponse br = new WebResponse();
		br.setCode(201);
		br.setMsg("验证未通过");
		Throwable cause = e.getCause();
		if (cause instanceof YtException) {
			YtException y = (YtException) cause;
			br.setMsg(y.getErrorcode().getDesc());
		}
		return br;
	}
}
