package br.com.sime.api.DTOs.Responses;

import br.com.sime.api.DTOs.Requests.AmbienteRequestDTO;

import java.util.List;

public record EquipamentoCodigosResponseDTO(
        List<CodEquipamentoResponseDTO> equipamentos
) {
}
