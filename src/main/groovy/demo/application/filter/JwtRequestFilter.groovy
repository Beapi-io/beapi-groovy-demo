package demo.application.filter

import demo.application.domain.Authority
import demo.application.domain.User
import demo.application.service.UserService
import demo.application.utils.JwtTokenUtil

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority
import io.jsonwebtoken.ExpiredJwtException;
import io.beapi.api.utils.ErrorCodes

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token


		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
			} catch (ExpiredJwtException e) {
				//sendError('401', 'JWT Token has expired', request.requestURI, response)
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				try {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

					//chain.doFilter(request, response)
				}catch(Exception ignored){
					ignored.printStackTrace()

				}
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
			}
		}

		// fix for errorController
		try{
			chain.doFilter(request, response);
		}catch(Exception ignored){
			ignored.printStackTrace();
			String statusCode = '401'
			response.setContentType("application/json")
			response.setStatus(Integer.valueOf(statusCode))
			String message = "{\"timestamp\":\"${System.currentTimeMillis()}\",\"status\":\"${statusCode}\",\"error\":\"${ErrorCodes.codes[statusCode]['short']}\",\"message\": \"${ErrorCodes.codes[statusCode]['long']}\",\"path\":\"${request.getRequestURI()}\"}"
			response.getWriter().write(message)
			response.writer.flush()
		}
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername(String) : {}")

		User user = userService.findByUsername(username);

		if (!user) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		Set<Authority> authorities = user.getAuthorities()

		// TODO : loop through authorities and assign as simpleGrantedAuth
		Set<SimpleGrantedAuthority> updatedAuthorities = []
		authorities.each(){ auth ->
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(auth.getAuthority())
			//SimpleGrantedAuthority authority = new SimpleGrantedAuthority(auth)
			updatedAuthorities.add(authority);
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), updatedAuthorities);
	}

}
