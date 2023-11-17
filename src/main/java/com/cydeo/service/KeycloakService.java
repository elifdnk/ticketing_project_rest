package com.cydeo.service;
import com.cydeo.dto.UserDTO;

import javax.ws.rs.core.Response;
public interface KeycloakService {

    Response userCreate(UserDTO dto); //crate user in keycloak
    void delete(String username); //delete  user in keycloak
}
