package com.sgai.vbp.web.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;

import com.sgai.vbp.web.http.servlet.ReadableHttpServletRequestWrapper;

@Order(1)
@WebFilter(filterName="readableHttpFilter", urlPatterns="/*")
public class ReadableHttpFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ReadableHttpServletRequestWrapper readableRequest = new ReadableHttpServletRequestWrapper((HttpServletRequest)request);
		chain.doFilter(readableRequest, response);
	}

	@Override
	public void destroy() {

	}

}
