package br.com.sime.api.DTOs;

import java.time.LocalDateTime;

public record ChamadoCardDTO (
    Long idChamado,
    String dtAberturaChamado,
    String descChamado,
    String localChamado,
    String prioridadeChamado,
    String statusChamado
) {}
