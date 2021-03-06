package com.huang.centralconf.configService.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.huang.centralconf.core.common.Errorcode;
import com.huang.centralconf.core.common.WebResponse;
import com.huang.centralconf.core.common.YtException;


@SuppressWarnings("ALL")
@RestController
@ControllerAdvice
public class ExceptionHandlerController {

	private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(Exception.class)
	public WebResponse exceptionHandler(Exception e) {

		WebResponse br = new WebResponse();
		logger.error("", e);
		br.setCode(-1);
		br.setMsg("inner error:"+e.getMessage());
		return br;
	}

	@ExceptionHandler(YtException.class)
	public WebResponse ytExceptionHandler(YtException e) {
		WebResponse br = new WebResponse();
		Errorcode code = e.getErrorcode();
		if (code.getCode() == YtfmUserErrors.RES_REPEAT.getCode()) {
			br.setCode(YtfmUserErrors.RES_REPEAT.getCode());
			br.setMsg(YtfmUserErrors.RES_REPEAT.getDesc());
		}  else if (code.getCode() == YtfmUserErrors.COPY_PROPRER.getCode()) {
			br.setCode(YtfmUserErrors.COPY_PROPRER.getCode());
			br.setMsg(YtfmUserErrors.COPY_PROPRER.getDesc());
		}else {
			logger.error("not process ytexception", e);
			br.setCode(-1);
			br.setMsg("inner error");
		}
		br.setData(((YtException) e).getExtraInfo());
		return br;
	}

}
