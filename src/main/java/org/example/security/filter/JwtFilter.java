package org.example.security.filter;

import lombok.RequiredArgsConstructor;
import org.example.security.UserDetailsServiceImpl;
import org.example.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
  public static final String AUTHORIZATION = "Authorization";
  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl userDetailsService;
  @Value("${security.secretKey}")
  private String secretKey;


  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    /*if (servletRequest.getServletPath().equals("/api/v1/login") || servletRequest.getServletPath().equals("/api/v1/registration")) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {*/
    String token = getTokenFromRequest((HttpServletRequest) servletRequest);
    if (token != null && jwtUtils.validateAccessToken(token,secretKey)) {
      String userName = jwtUtils.getLoginFromAccessToken(token);
      UserDetails customUserDetails = userDetailsService.loadUserByUsername(userName);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
          customUserDetails, null, customUserDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(servletRequest, servletResponse);

  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearer = request.getHeader(AUTHORIZATION);
    if (bearer != null && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    } else {
      Cookie cookie = WebUtils.getCookie(request, "JWT");
      if (cookie != null) {
        return cookie.getValue();
      }
    }
    return null;
  }

  private String getAccessTokenFromRequest(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, "JWT");
    if (cookie != null) {
      return cookie.getValue();
    }
    return null;
  }

  private String getRefreshTokenFromRequest(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, "JWT-REFRESH");
    if (cookie != null) {
      return cookie.getValue();
    }
    return null;
  }

  private void setAuthentication(String token) {
    String userName = jwtUtils.getLoginFromAccessToken(token);
    UserDetails customUserDetails = userDetailsService.loadUserByUsername(userName);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        customUserDetails, null, customUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

}