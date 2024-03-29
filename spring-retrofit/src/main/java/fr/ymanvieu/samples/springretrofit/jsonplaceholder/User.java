package fr.ymanvieu.samples.springretrofit.jsonplaceholder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Long id;
    private String name;
    private String username;
    private String email;
}
