//package sn.ism.gestion.gestionImage.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import sn.ism.gestion.gestionImage.services.StorageService;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/images")
//@RequiredArgsConstructor
//public class ImageController {
//
//    private final StorageService storageService;
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadToCloud(@RequestParam("file") MultipartFile file) {
//        try {
//            String url = storageService.uploadImage(file);
//            return ResponseEntity.ok(url);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Erreur: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/local")
//    public ResponseEntity<String> saveLocally(@RequestParam("file") MultipartFile file) {
//        try {
//            String path = storageService.saveImage(file);
//            return ResponseEntity.ok(path);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Erreur: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{filename:.+}")
//    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
//        return storageService.getImage(filename);
//    }
//}
