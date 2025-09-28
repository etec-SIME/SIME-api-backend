package br.com.sime.api.DTOs;

public record ChamadoCardDTO (
    Long idChamado,
    String dtAberturaChamado,
    String descChamado,
    String prioridadeChamado,
    String statusChamado
) {}
