package demo.application.controller

import demo.application.domain.Authority
import demo.application.domain.UserAuthority
import demo.application.service.AuthorityService
import demo.application.service.UserAuthorityService
import io.beapi.api.service.PrincipleService
import io.beapi.api.controller.BeapiController
import io.beapi.api.properties.ApiProperties

import demo.application.domain.User
import demo.application.service.JwtUserDetailsService
import demo.application.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller

@Controller
class UserController extends BeapiController {

	@Autowired
	public PasswordEncoder passwordEncoder

	@Autowired
	PrincipleService principle

	@Autowired
	UserService userService

	@Autowired
	private AuthorityService authService

	@Autowired
	private UserAuthorityService uAuthService



	def list(HttpServletRequest request, HttpServletResponse response){
		try{
			ArrayList<User> users = userService.getAllUsers()
			return users
		}catch(Exception e){
			throw new Exception("[UserController : get] : Exception - full stack trace follows:",e)
		}
	}

	def show(HttpServletRequest request, HttpServletResponse response){
		try{
			String username
			if(principle.isSuperuser()){
				username = (params.id)?(params.id):principle.name()
			}else {
				username = principle.name()
			}

			User user = userService.findByUsername(username)

			if (user) {
				//traceService.endTrace('PersonController','show')
				return user
			} else {

				// todo : use error method in BeapiController
				//traceService.endTrace('PersonController','show')
				//writeErrorResponse(response,'500', request.getRequestURI())
			}

		}catch(Exception e){
			throw new Exception("[UserController : get] : Exception - full stack trace follows:",e)
		}
    }

	// admin can pass a role else defaults to 'ROLE_USER
	def create(HttpServletRequest request, HttpServletResponse response){
		try{
			String role = request.getSession().getAttribute('params').role
			Authority[] auth = authService.findByAuthority(role)

			User user = new User()
			ArrayList auths = []
			auth.each(){ it ->  auths.add(it) }
			user.setUsername(request.getSession().getAttribute('params').login)
			user.setEmail(request.getSession().getAttribute('params').email)
			user.setPassword(passwordEncoder.encode(request.getSession().getAttribute('params').password));

			// todo : need rollback upon fail
			if(userService.save(user)){
				auths.each() {
					UserAuthority uAuth = new UserAuthority()
					uAuth.setUser(user)
					uAuth.setAuthority(it)
					uAuthService.save(uAuth)
				}
			}
		}catch(Exception e){
			throw new Exception("[UserController : create] : Exception - full stack trace follows:",e)
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

	/*
	LinkedHashMap getByUsername(){
		try{
			User user
			user = User.findWhere(username: "params?.username", enabled: true)
			if(user){
				return [person: user]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
			return [person: user]
		}catch(Exception e){
			throw new Exception("[PersonController : getByUsername] : Exception - full stack trace follows:",e)
		}
	}
	 */

/*
	LinkedHashMap delete() {
		User user
		List prole
		try {
			user = User.get(params.id)
			if(user){
					prole = PersonRole.findAllByPerson(user)
					prole.each() {
						it.delete(flush: true, failOnError: true)
					}


					 // additional dependencies to be removed should be put here


					user.delete(flush: true, failOnError: true)
					return [person: [id: params.id.toLong()]]
			}else{
				render(status: 500,text:"Id " + params.id + " does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonController : delete] : Exception - full stack trace follows:",e)
		}
	}
*/

}
