package br.com.sime.api.DTOs.Responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ChamadoResponseDTO(
    Long idChamado,
    String tituloChamado,
    String statusGeralAtualChamado,
    String descChamado,
    String nomeTipoChamado,
    String prioridadeChamado,
    LocalDateTime dtAberturaChamado,
    LocalDateTime dtConclusaoChamado,
    List<String> caminhoImagensList
) {}