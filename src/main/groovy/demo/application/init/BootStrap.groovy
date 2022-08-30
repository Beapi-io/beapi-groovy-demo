package demo.application.init


import demo.application.domain.User
import demo.application.domain.Authority
import demo.application.domain.UserAuthority
import demo.application.service.AuthorityService
import demo.application.service.UserAuthorityService
import demo.application.service.UserService

//import grails.gorm.transactions.Transactional

import org.springframework.stereotype.Component
import org.springframework.context.ApplicationContext
import org.springframework.boot.info.BuildProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import io.beapi.api.properties.ApiProperties

@Component
class BootStrap {

    @Autowired
    public PasswordEncoder passwordEncoder

    // TODO : set this is properties
    ArrayList<String> roles = ['ROLE_ADMIN','ROLE_USER']

    @Autowired
    ApiProperties apiProperties

    @Autowired
    public BuildProperties buildProperties

    @Autowired
    private AuthorityService authService

    @Autowired
    private UserService userService

    @Autowired
    private UserAuthorityService uAuthService


    LinkedHashMap testLoadOrder = [:]


    //void init(ApplicationContext applicationContext,ApiProperties apiProperties) {
    void init(ApplicationContext applicationContext) {


// START BOOTSTRAP AUTHORITIES
        ArrayList roles = []
        roles.add(apiProperties.getSecurity().getSuperuserRole())
        roles.add(apiProperties.getSecurity().getUserRole())

        List auth = authService.findAll()

        List authroles = auth.collect{ it -> return it.authority }

        def commons = authroles.intersect(roles)
        if(roles!=commons){
            def diff = commons.plus(roles)
            diff.each(){ it ->
                Authority newAuth = new Authority();
                newAuth.setAuthority(it);
                authService.save(newAuth)
            }
        }
// END BOOTSTRAP AUTHORITIES

// START BOOTSTRAP SUPERUSER
        LinkedHashMap superUser = apiProperties.getBootstrap().getSuperUser()
        Authority[] adminAuth = authService.findByAuthority(apiProperties.getSecurity().getSuperuserRole())
        Authority[] testAuth = authService.findByAuthority(apiProperties.getSecurity().getUserRole())

        User sUser = userService.findByEmail(superUser['email'])
        if(!sUser){ sUser = userService.findByUsername(superUser['login']) }
        if(sUser){
            // UPDATE SUPERUSER
            sUser.setUsername(superUser['login'])
            sUser.setEmail(superUser['email'])
            sUser.setPassword(passwordEncoder.encode(superUser['password']));
            userService.save(sUser)
        }else{
            // CREATE NEW SUPERUSER
            sUser = new User()
            ArrayList auths1 = []
            adminAuth.each(){ it ->  auths1.add(it) }
            sUser.setUsername(superUser['login'])
            sUser.setEmail(superUser['email'])
            sUser.setPassword(passwordEncoder.encode(superUser['password']));

            // todo : need rollback upon fail
            if(userService.save(sUser)) {
                auths1.each() {
                    UserAuthority uAuth1 = new UserAuthority()
                    uAuth1.setUser(sUser)
                    uAuth1.setAuthority(it)
                    uAuthService.save(uAuth1)
                }
            }
        }
// END BOOTSTRAP SUPERUSER


// START BOOTSTRAP TESTUSER
        LinkedHashMap testUser = apiProperties.getBootstrap().getTestUser()
        User tUser = userService.findByEmail(testUser['email'])
        if(!tUser){ tUser = userService.findByUsername(testUser['login']) }

        if(tUser){
            // UPDATE TESTUSER
            tUser.setUsername(testUser['login'])
            tUser.setEmail(testUser['email'])
            tUser.setPassword(passwordEncoder.encode(testUser['password']));
            userService.save(tUser)
        }else{
            // CREATE NEW TESTUSER
            tUser = new User()
            ArrayList auths2 = []
            testAuth.each(){ it ->  auths2.add(it) }
            tUser.setUsername(testUser['login'])
            tUser.setEmail(testUser['email'])
            tUser.setPassword(passwordEncoder.encode(testUser['password']));

            // todo : need rollback upon fail
            if(userService.save(tUser)){
                auths2.each() {
                    UserAuthority uAuth = new UserAuthority()
                    uAuth.setUser(tUser)
                    uAuth.setAuthority(it)
                    uAuthService.save(uAuth)
                }
            }
        }
    }

}
