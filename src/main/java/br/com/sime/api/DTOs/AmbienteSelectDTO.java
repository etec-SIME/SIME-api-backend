package br.com.sime.api.DTOs;

import java.util.List;

public record AmbienteSelectDTO (
    Long idAmbiente,
    Long numAmbiente,
    Long idTipoAmbiente,
    String nomeTipoAmbiente
) {}
