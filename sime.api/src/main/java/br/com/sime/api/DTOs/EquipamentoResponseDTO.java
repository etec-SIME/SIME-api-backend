package br.com.sime.api.DTOs;

import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import lombok.Data;

@Data
public class EquipamentoResponseDTO {
    private Long codEquipamento;
    private Long idtipoEquipamento;
}
