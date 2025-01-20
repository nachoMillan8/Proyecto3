package com.example.ventaService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la peticion de validacion de un usuario.
 * Contiene la informacion del usuario a validar.
 * @see UserValidationResponse
 */
@Data
@Builder
public class UserValidationRequest {
    @JsonProperty("user")
    String user;
    @JsonProperty("password")
    String pwd;

    public UserValidationRequest(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
