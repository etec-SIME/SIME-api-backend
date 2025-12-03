package br.com.sime.api.DTOs.Responses;

import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import lombok.Data;


public record EquipamentoResponseDTO (
        String codEquipamento,
        Long idTipoEquipamento
) { }
