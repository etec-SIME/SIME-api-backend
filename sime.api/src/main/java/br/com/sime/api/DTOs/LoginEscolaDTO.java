package br.com.sime.api.DTOs;

import lombok.Data;

@Data
public class LoginEscolaDTO {
    private String codEscola;
    private String cnpjEscola;
    private String nomeEscola;
}
