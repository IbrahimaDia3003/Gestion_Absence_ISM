package sn.ism.gestion.mobile.dto;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class RestResponseMobile {

    public static Map<String, Object> of(HttpStatus status, String type, Object results) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("type", type);
        response.put("results", results);
        return response;
    }

    public static Map<String, Object> ofSuccess(String type, Object results) {
        return of(HttpStatus.OK, type, results);
    }

    public static Map<String, Object> ofError(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("type", "error");
        response.put("message", message);
        return response;
    }
}
