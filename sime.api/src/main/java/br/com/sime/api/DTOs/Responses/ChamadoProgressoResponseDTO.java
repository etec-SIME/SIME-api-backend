package br.com.sime.api.DTOs.Responses;

import java.time.LocalDateTime;
import java.util.List;

public record ChamadoProgressoResponseDTO(
    Long idChamado,
    String statusAtualProgressoChamado,
    List<HistoricoChamadoList> historicoChamadoList
) {
    public record HistoricoChamadoList (
        String statusProgresso,
        String diaSemana,
        LocalDateTime dtAlteracao
    ) { }
}
