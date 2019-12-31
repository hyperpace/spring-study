package goozy.springstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @Configuration, @ComponentScan 확장
// 즉, 이클래스 자체가 빈 설정 파일이고 알아서 해준다.
// SpringBoot가 아주 편하게 해준다.
@SpringBootApplication
public class SpringStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }
}
