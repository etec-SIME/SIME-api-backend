package br.com.sime.api.DTOs;

public record ChamadoCardDTO (
    Long idChamado,
    String dtAberturaChamado,
    String descChamado,
    String tituloChamado,
    String prioridadeChamado,
    String statusAtualGeralChamado,
    Long numAmbiente,
    String nomeTipoAmbiente
) { }
