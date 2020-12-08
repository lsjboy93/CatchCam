package com.ssafy.catchcam.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


public class JwtAuthenticationFilter extends GenericFilterBean {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	// Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를
	// filterChain에 등록합니다.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// 헤더에서 JWT를 받아옴
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		// 유효한 토큰인지 확인
		if (token != null && jwtTokenProvider.validateToken(token)) {
			// 토큰이 유효하면 토큰으로 부터 유저 정보 받아옴
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}

