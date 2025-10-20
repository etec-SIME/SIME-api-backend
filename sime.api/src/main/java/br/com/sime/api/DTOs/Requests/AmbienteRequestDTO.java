package br.com.sime.api.DTOs.Requests;

import java.util.List;

public record AmbienteRequestDTO(
        Long idAmbiente,
        Long numAmbiente,
        String descricaoAmbiente,
        Long idTipoAmbiente,
        String nomeTipoAmbiente,
        List<CodEquipamentos> equipamentosList
) {
    public record CodEquipamentos (
            String codEquipamento
    ) { }
}
