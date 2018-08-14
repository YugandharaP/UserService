package com.bridgelabz.userservice.utilservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author yuga
 * @since 17/07/2018
 *<p><b>It is the intercepter/filter to intercept between url and controller.
 *Spring Interceptors has the ability to pre-handle and post-handle the web requests. 
 *Each interceptor class should extend the HandlerInterceptorAdapter class. 
 *Here we create a Logger Interceptor by extending the HandlerInterceptorAdapter class.   </b></p> */

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {
	static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
	/**
	 * <p><b> Before a request is handled by a request handler.</b></p>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object){
		logger.info("Request before " +request.getRequestURI());
		return true;
	}
	/**
	 *<p><b> After a request is handled by a request handler. 
	 * It gives access to the returned ModelAndView object, so you can manipulate the model attributes in it.</b></p>
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object object, ModelAndView model)
			{
		logger.info("Request for "+request.getRequestURI()+" after execution");
	}
	/**<p><b>After the completion of all request processing i.e. after the view has been rendered.</b></p>
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception argument)
			{
		logger.info("Request for "+request.getRequestURI()+" is complete");
	}
}

