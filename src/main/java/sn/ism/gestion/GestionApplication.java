package sn.ism.gestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = {
        "sn.ism.gestion",
        "gestion_image.traite.img" // très important
})
public class GestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionApplication.class, args);
    }

}
