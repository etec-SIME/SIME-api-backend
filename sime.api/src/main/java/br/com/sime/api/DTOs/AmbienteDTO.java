package br.com.sime.api.DTOs;

import br.com.sime.api.entities.escola.ambiente.Tipo_Ambiente;
import lombok.Data;

@Data
public class AmbienteDTO {
    private Long numAmbiente;
    private String descricaoAmbiente;
    private Long idTipoAmbiente;
}
