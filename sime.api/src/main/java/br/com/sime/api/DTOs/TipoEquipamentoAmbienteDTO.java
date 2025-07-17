package br.com.sime.api.DTOs;

import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import lombok.Data;

import java.util.List;

@Data
public class TipoEquipamentoAmbienteDTO {
    private List<Long> idsTipoEquipamento;
}
