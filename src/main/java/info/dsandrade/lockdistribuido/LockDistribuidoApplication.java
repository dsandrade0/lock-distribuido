package info.dsandrade.lockdistribuido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LockDistribuidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockDistribuidoApplication.class, args);
    }

}
