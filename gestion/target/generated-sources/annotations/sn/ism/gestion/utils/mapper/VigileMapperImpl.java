package sn.ism.gestion.utils.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.ism.gestion.data.entities.Vigile;
import sn.ism.gestion.web.dto.Request.VigileSimpleRequest;
import sn.ism.gestion.web.dto.Response.VigileAllResponse;
import sn.ism.gestion.web.dto.Response.VigileSimpleResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T14:10:14+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class VigileMapperImpl implements VigileMapper {

    @Override
    public VigileSimpleResponse toDto(Vigile vigile) {
        if ( vigile == null ) {
            return null;
        }

        VigileSimpleResponse vigileSimpleResponse = new VigileSimpleResponse();

        vigileSimpleResponse.setUtilisateurId( vigile.getUtilisateurId() );

        return vigileSimpleResponse;
    }

    @Override
    public Vigile toEntity(Vigile request) {
        if ( request == null ) {
            return null;
        }

        Vigile vigile = new Vigile();

        vigile.setId( request.getId() );
        vigile.setCreatedAt( request.getCreatedAt() );
        vigile.setUpdatedAt( request.getUpdatedAt() );
        vigile.setUtilisateurId( request.getUtilisateurId() );

        return vigile;
    }

    @Override
    public VigileAllResponse toDtoAll(VigileAllResponse vigil) {
        if ( vigil == null ) {
            return null;
        }

        VigileAllResponse vigileAllResponse = new VigileAllResponse();

        vigileAllResponse.setId( vigil.getId() );
        vigileAllResponse.setNom( vigil.getNom() );
        vigileAllResponse.setPrenom( vigil.getPrenom() );
        vigileAllResponse.setLogin( vigil.getLogin() );
        vigileAllResponse.setUtilisateurId( vigil.getUtilisateurId() );

        return vigileAllResponse;
    }

    @Override
    public Vigile toEntityR(VigileSimpleRequest request) {
        if ( request == null ) {
            return null;
        }

        Vigile vigile = new Vigile();

        return vigile;
    }
}
