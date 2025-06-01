package sn.ism.gestion.Config;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface Controller<T> {

    @GetMapping("")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size);

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donnee trouvé"),
            @ApiResponse(responseCode = "404", description = "Donnee non trouvé")

    })
    ResponseEntity<Map<String, Object>> SelectdById(@PathVariable String id);

    @PutMapping("/update/{id}")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Map<String, Object>> Update(@PathVariable String id, @RequestBody T request);

    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Map<String, Object>> Delete(@PathVariable String id);

}
