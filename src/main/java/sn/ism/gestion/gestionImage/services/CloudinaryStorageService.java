//package sn.ism.gestion.gestionImage.services;
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import org.springframework.beans.factory.annotation.Value;
//
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class CloudinaryStorageService implements StorageService {
//
//    private final Cloudinary cloudinary;
//
//    public CloudinaryStorageService(
//            @Value("${cloudinary.cloud-name}") String cloudName,
//            @Value("${cloudinary.api-key}") String apiKey,
//            @Value("${cloudinary.api-secret}") String apiSecret
//    ) {
//        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", cloudName,
//                "api_key", apiKey,
//                "api_secret", apiSecret
//        ));
//    }
//
//    @Override
//    public String uploadImage(MultipartFile file) throws IOException {
//        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//        return uploadResult.get("secure_url").toString(); // 🔗 URL à stocker en base
//    }
//
//    @Override
//    public ResponseEntity<Resource> getImage(String filename)
//    {
//        Resource resource = loadImageAsResource(filename);
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
//                .body(resource);    }
//
//    private final Path uploadDir = Paths.get("uploads");
//
//    @Override
//    public String saveImage(MultipartFile file) {
//        try {
//            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
//            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
//            Path targetLocation = uploadDir.resolve(filename);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            return "/api/images/" + filename;
//        } catch (IOException e) {
//            throw new RuntimeException("Échec de l'enregistrement de l'image", e);
//        }
//    }
//
//
//    @Override
//    public Resource loadImageAsResource(String filename) {
//        try {
//            Path filePath = uploadDir.resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) return resource;
//            else throw new FileNotFoundException("Image non trouvée");
//        } catch (Exception e) {
//            throw new RuntimeException("Erreur de chargement de l’image", e);
//        }
//    }
//}
