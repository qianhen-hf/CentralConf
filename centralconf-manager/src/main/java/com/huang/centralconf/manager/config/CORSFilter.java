package com.huang.centralconf.manager.config;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;

import freemarker.template.utility.StringUtil;

/**
 * 
 * <pre>
 * <b>跨域访问.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> wangjiesong@cmhk.com
 * <b>Date:</b> 2016年10月14日上午9:47:30
 * <b>Copyright:</b> Copyright ©2013-2016 cmytc.com Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   ----------------------------------------------------------------------------
 *   Ver   Date                    Author                           Detail
 *   ----------------------------------------------------------------------------
 *   1.0   2016年10月14日上午9:47:30   wangjiesong@cmhk.com            new file.
 * </pre>
 */
@Component
public class CORSFilter implements Filter{

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {  
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest)req;
        String referer=request.getHeader("referer");
        if(StringUtils.isEmpty(referer)) {
        	response.setHeader("Access-Control-Allow-Origin", "*");
        } else {
        	URL url = new URL(referer);
        	response.setHeader("Access-Control-Allow-Origin", "http://" + url.getHost() + ":" + url.getPort());  
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");  
        response.setHeader("Access-Control-Allow-Credentials", "true");  
        chain.doFilter(req, res);  
    }  
  
    public void init(FilterConfig filterConfig) {}  
  
    public void destroy() {}  

}
