//package sn.ism.gestion.Config;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Bean;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//
//@Configuration
//public class MongoConfig
//{
//    @Bean
//    public MongoClient mongoClient() {
//        Dotenv dotenv = Dotenv.configure().load();
//        String uri = dotenv.get("MONGODB_URI");
//
//        if (uri == null || uri.isBlank())
//        {
//            throw new IllegalArgumentException("La variable d'environnement MONGODB_URI est manquante ou vide.");
//        }
//        return MongoClients.create(uri);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate(MongoClient mongoClient, MongoMappingContext mappingContext) {
//        return new MongoTemplate(mongoClient, "ISMAbsence");
//    }
//
//    @Bean
//    public MongoMappingContext mongoMappingContext() {
//        return new MongoMappingContext();
//    }
//}
