package sn.ism.gestion.data.services;

import sn.ism.gestion.data.entities.Pointages;
import sn.ism.gestion.mobile.dto.Request.EtudiantQrCodeRequest;

import java.util.List;

public interface PointageService
{
    Pointages createPointage(String vigileId , EtudiantQrCodeRequest etudiantQrCode);
    List<Pointages> getPointagesByVigile(String vigileId);

}
