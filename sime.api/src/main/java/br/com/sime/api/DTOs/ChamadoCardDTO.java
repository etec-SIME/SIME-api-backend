package br.com.sime.api.DTOs;

import java.time.LocalDateTime;

public record ChamadoCardDTO (
    Long idChamado,
     LocalDateTime dtAberturaChamado,
     String descChamado,
     String localChamado,
     String prioridadeChamado
) {}
