package com.ssafy.catchcam.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@WebFilter(urlPatterns = { "/api/**" }, description = "API 필터")
@Component("CorsFilter")
public class CORSFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		httpServletRequest.setCharacterEncoding("utf-8");
		// set header

		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, PATCH, OPTIONS");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, x-auth-token");
		httpServletResponse.setHeader("Accept-Charset", "utf-8");
		httpServletResponse.setHeader("Cache-Control", "no-cache");
		httpServletResponse.setHeader("Expires", "-1");
		httpServletResponse.setHeader("Pragma", "no-cache");

		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	@Override
	public void destroy() {

	}
}