package br.com.sime.api.DTOs;

import lombok.Data;

@Data
public class TokenEscolaDTO {
    private String tokenEscola;

    public TokenEscolaDTO(String tokenEscola){this.tokenEscola = tokenEscola;}
}
