package br.com.sime.api.DTOs.Responses;

import br.com.sime.api.entities.chamados.ImagemChamado;

import java.time.LocalDateTime;
import java.util.List;

public record ChamadoResponseDTO(
    Long idChamado,
    String tituloChamado,
    String statusChamado,
    String descChamado,
    String nomeTipoChamado,
    String prioridadeChamado,
    LocalDateTime dtAberturaChamado,
    List<String> caminhoImagensList
) {}