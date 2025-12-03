package br.com.sime.api.DTOs;

import br.com.sime.api.DTOs.Responses.CodEquipamentoResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class AmbienteDTO {
    private Long numAmbiente;
    private String descricaoAmbiente;
    private Long idTipoAmbiente;
    private List<CodEquipamentoResponseDTO> equipamentoList;
}
