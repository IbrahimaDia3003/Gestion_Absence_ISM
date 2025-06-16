package sn.ism.gestion.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.Salle;
import sn.ism.gestion.data.repositories.SalleRepository;
import sn.ism.gestion.data.services.ISalleService;
import sn.ism.gestion.web.dto.Response.SalleResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalleServiceImpl implements ISalleService
{
    @Autowired
    private SalleRepository salleRepository;
    @Override
    public List<SalleResponse> findAllSalles()
    {
        List<Salle> salles = salleRepository.findAll();
        // Convert Salle entities to SalleResponse DTOs

        return salles.stream()
                .map(salle ->
                        {
                            SalleResponse response = new SalleResponse();
                            response.setId(salle.getId());
                            response.setNumero(salle.getNumero());
                            response.setNombrePlaces(salle.getNombrePlaces());
                            return response;
                        }
                ).toList();

    }
}
