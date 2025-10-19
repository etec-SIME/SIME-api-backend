package br.com.sime.api.DTOs.Requests;

import br.com.sime.api.DTOs.Responses.CodEquipamentoResponseDTO;

import java.util.List;

public record AmbienteRequestDTO(
    Long idAmbiente,
    Long numAmbiente,
    String descricaoAmbiente,
    Long idTipoAmbiente,
    String nomeTipoAmbiente,
    List<CodEquipamentoResponseDTO> equipamentosList
) {
}
