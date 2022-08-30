package demo.application.service

import demo.application.domain.User
import org.slf4j.LoggerFactory
import demo.application.domain.Authority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.context.annotation.RequestScope;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder bcryptEncoder;


	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);


	@RequestScope
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername(String) : {}")
        User user = userService.findByUsername(username);

		if (!user) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		List<Authority> authorities = user.getAuthorities()

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