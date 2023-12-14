package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResponseDTO {

    private String access_token; //name should be look like this. because keycloak send me json obj and field names are should match
    //by using this class I will be capture access token

}