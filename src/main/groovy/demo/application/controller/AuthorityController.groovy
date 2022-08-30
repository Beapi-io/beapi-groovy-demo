package demo.application.controller

import demo.application.domain.Authority
import demo.application.domain.User
import demo.application.domain.UserAuthority
import demo.application.service.AuthorityService
import demo.application.service.JwtUserDetailsService
import demo.application.service.UserAuthorityService
import demo.application.service.UserService
import io.beapi.api.controller.BeapiController
import io.beapi.api.properties.ApiProperties
import io.beapi.api.service.PrincipleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class AuthorityController extends BeapiController {

	@Autowired
	public PasswordEncoder passwordEncoder

	@Autowired
	private ApiProperties apiProperties

	@Autowired
	PrincipleService principle

	@Autowired
	UserService userService

	@Autowired
	private AuthorityService authService

	@Autowired
	private UserAuthorityService uAuthService

	@Autowired
	private JwtUserDetailsService jwtService;

	def list(HttpServletRequest request, HttpServletResponse response){
		try{
			List<Authority> auth = authService.findAll()
			return auth
		}catch(Exception e){
			throw new Exception("[AuthorityController : list] : Exception - full stack trace follows:",e)
		}
	}

	def create(HttpServletRequest request, HttpServletResponse response){
		try{
			String authority = params.authority

			Authority[] auth = authService.findByAuthority(authority)
			if(!auth){
                Authority newAuth = new Authority();
                newAuth.setAuthority(authority);
                return authService.save(newAuth)
			}else{
				throw new Exception("[AuthorityController : create] : Authority '${authority}' already exists.")
			}
		}catch(Exception e){
			throw new Exception("[AuthorityController : create] : Exception - full stack trace follows:",e)
		}
	}

/*
	LinkedHashMap update(){
		try{
			User user
			if(isSuperuser() && params?.id){
				user = User.get(params?.id?.toLong())
			}else{
				user = User.get(springSecurityService.principal.id)
			}
			if(user){
				user.username = params.username
				user.password = params.password
				user.email = params.email

				if(isSuperuser()){
					user.enabled = params.enabled
				}

				if(!user.save(flush:true,failOnError:true)){
					user.errors.allErrors.each { println(it) }
				}
				return [person:user]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonController : update] : Exception - full stack trace follows:",e)
		}
	}
*/


}
