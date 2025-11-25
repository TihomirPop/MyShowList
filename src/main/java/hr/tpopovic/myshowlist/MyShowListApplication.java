package hr.tpopovic.myshowlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "hr.tpopovic.myshowlist.adapter.out")
public class MyShowListApplication {

    static void main(String[] args) {
        SpringApplication.run(MyShowListApplication.class, args);
    }

}

