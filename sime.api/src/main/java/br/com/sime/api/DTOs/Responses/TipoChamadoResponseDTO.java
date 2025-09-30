package br.com.sime.api.DTOs.Responses;

import br.com.sime.api.entities.outros.Departamento;
import lombok.Data;

@Data
public class TipoChamadoResponseDTO {
    private Long idDepartamento;
    private String nomeTipoChamado;
}
