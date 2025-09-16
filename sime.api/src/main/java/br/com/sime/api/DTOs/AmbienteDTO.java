package br.com.sime.api.DTOs;

import lombok.Data;

@Data
public class AmbienteDTO {
    private Long numAmbiente;
    private String descricaoAmbiente;
    private Long idTipoAmbiente;
}
