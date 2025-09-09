package br.com.sime.api.DTOs;

public record AmbienteSelectDTO(
    Long idAmbiente,
    Long numAmbiente,
    Long idTipoAmbiente,
    String nomeTipoAmbiente
) {}
