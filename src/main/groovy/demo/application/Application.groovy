package demo.application


import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.ApplicationArguments
import demo.application.init.BootStrap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.apache.catalina.connector.Connector;

//import org.springframework.boot.context.properties.EnableConfigurationProperties
//import io.beapi.api.properties.ApiProperties


import org.springframework.context.ApplicationContext
import org.slf4j.LoggerFactory;


//@CompileStatic
//@EnableConfigurationProperties([ApiProperties.class])
@ComponentScan(["io.beapi.api.*","demo.application*"])
@SpringBootApplication(exclude = [HibernateJpaAutoConfiguration.class])
class Application implements ApplicationRunner {

    //@Autowired
    //private ApiProperties apiProperties

    @Autowired
    BootStrap bootStrap

    @Autowired
    ApplicationContext applicationContext;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);

    static void main(String[] args) {
        SpringApplication.run Application, args
    }

    void run(ApplicationArguments args) throws Exception {
        bootStrap.init(applicationContext)
    }
}
