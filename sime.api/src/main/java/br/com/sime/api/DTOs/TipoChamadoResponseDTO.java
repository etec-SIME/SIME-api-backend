package br.com.sime.api.DTOs;

import br.com.sime.api.entities.outros.Departamento;
import lombok.Data;

@Data
public class TipoChamadoResponseDTO {
    private Long idTipoChamado;
    private String nomeTipoChamado;
    private Departamento departamento;
}
