package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.lang.GString;
import lombok.Data;
import org.junit.Test;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

    private int category_id;
    private String category_title;
    private int seller_id;
    private String email;
    List<CustomResponse> responses;







}
