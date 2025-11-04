package br.com.sime.api.DTOs;

import java.util.List;

public record AmbienteChamadoSelectDTO(
    Long idAmbiente,
    Long numAmbiente,
    Long idTipoAmbiente,
    String nomeTipoAmbiente,
    List<TipoEquipamentoSelectDTO> tipoEquipamentoList
) {}
