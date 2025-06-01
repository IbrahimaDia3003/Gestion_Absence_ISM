package sn.ism.gestion.web.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class RestResponse {

    static Map<String,Object> response = new HashMap<String,Object>();

    public static Map<String, Object> response(HttpStatus status, Object results, Object type) {
        response.put("status", status.value());
        response.put("type", type);
        response.put("results", results);
        return response;
    }

    public static Map<String, Object> responsePaginate(
                                        HttpStatus status,
                                        Object results,
                                        Object currentPage,
                                        Integer totalPages,
                                        Object totalItems,
                                        Boolean first,
                                        Boolean last,
                                        String type) {
        response.put("status", status.value());
        response.put("results", results);
        response.put("pages", new int[totalPages]);
        response.put("currentPage", currentPage);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);
        response.put("first", first);
        response.put("last", last);
        response.put("type", type);
        return response;
    }
 

}
