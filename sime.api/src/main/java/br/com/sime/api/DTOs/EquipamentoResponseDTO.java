package br.com.sime.api.DTOs;

import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import lombok.Data;

@Data
public class EquipamentoResponseDTO {
    private String codEquipamento;
    private Long idtipoEquipamento;
}
