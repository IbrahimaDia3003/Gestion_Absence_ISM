

package sn.ism.gestion.data.entities;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sn.ism.gestion.data.enums.StatutJustification;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "justifications")
public class Justification extends AbstractEntity {
    private String absenceId;
    private String commentaire;
    private String fichierUrl;
    private String publicId;
    private String url;
//    @Transient
//    private MultipartFile imageUrl;
    private StatutJustification statut;
    private LocalDate dateSoumission = LocalDate.now() ;
}