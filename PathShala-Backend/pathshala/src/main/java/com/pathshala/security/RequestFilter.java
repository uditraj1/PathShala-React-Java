package com.pathshala.security;

import com.pathshala.exception.BaseRuntimeException;
import com.pathshala.exception.UnauthorizedAccessException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class RequestFilter implements Filter {

    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException, BaseRuntimeException {
        if (((RequestFacade) request).getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("OK");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "*");
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            String url = ((RequestFacade) request).getRequestURL().toString();
            tokenService.expireToken();
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // if(false){
           if (!url.contains("login") && !url.contains("signUp")) {
                String token = httpRequest.getHeader("authorization-token");
                String userId = httpRequest.getHeader("userId");
                String userType = httpRequest.getHeader("userType");
                if (tokenService.validateToken(Long.parseLong(userId), token, userType)) {
                    filterChain.doFilter(request, response);
                } else {
                    throw new UnauthorizedAccessException("Hello", "Hello");
                }
            } else {

                ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
                ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "*");
                filterChain.doFilter(request, response);
            }
        }
    }

}
