package br.com.sime.api.DTOs;

import java.util.List;

public record TipoEquipamentoSelectDTO (
        Long idTipoEquipamento,
        String nomeTipoEquipamento,
        Long idTipoChamado,
        String nomeTipoChamado,
        List<CodEquipamentoList> equipamentoList
) {
    public record CodEquipamentoList (
            String codEquipamento
    ) { }
}
