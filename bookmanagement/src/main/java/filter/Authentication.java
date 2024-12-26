/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Ato
 */
@WebFilter(filterName  = "AuthenticationFilter", urlPatterns = {"/api/v1/admin/*", "/api/v1/auth"})
public class Authentication implements Filter {
//    private final UserService userService;
//    
//    public Authentication() {
//        userService = new UserService();
//    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;
        chain.doFilter(request, response);
//        Cookie[] cookies = request.getCookies();
//        if(cookies == null) {
//            System.out.println("Authen");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        } else {
////            for(Cookie cookie : cookies) {
////                String cookieName = cookie.getName();
////                if(cookieName.equals("sessionid")) {
////                    String sessionId = cookie.getValue();
////                    if(sessionId != null) {
////                        boolean isLogined = userService.checkLogining(sessionId);
////                        if(isLogined)
////                    }
////                }
////            }
//        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
