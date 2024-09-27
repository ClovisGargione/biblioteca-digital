package org.sankhya.restConfig;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	    throws IOException, ServletException {
	HttpServletResponse response = (HttpServletResponse) servletResponse;
	response.setHeader("Access-Control-Allow-Origin", "*");
	// response.setHeader("Access-Control-Allow-Credentials", "true");
	response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
	response.setHeader("Access-Control-Allow-Headers",
		"Access-Control-Allow-Origin, Content-Type, Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Access-Control-Request-Method, Access-Control-Request-Headers");
	filterChain.doFilter(servletRequest, servletResponse);
    }

}
