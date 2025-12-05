package br.com.sime.api.DTOs.Responses;
import br.com.sime.api.DTOs.AmbienteSelectDTO;
import br.com.sime.api.DTOs.TipoAmbienteDTO;
import br.com.sime.api.DTOs.TipoChamadoSelectDTO;
import java.time.LocalDateTime;

public record ChamadosAmbienteResponseDTO(
		Long idChamado,
        String tituloChamado,
        String descChamado,
        LocalDateTime dtAberturaChamado,
        String statusAtualGeralChamado,
        AmbienteSelectDTO ambiente,
        TipoChamadoSelectDTO tipoChamado,
        TipoAmbienteDTO tipoAmbiente
) {}
