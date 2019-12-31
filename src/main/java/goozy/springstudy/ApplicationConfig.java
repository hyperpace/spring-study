package goozy.springstudy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = "goozy.springstudy")
@ComponentScan(basePackageClasses = SpringStudyApplication.class) // Type Safe한 방법
public class ApplicationConfig {

}
