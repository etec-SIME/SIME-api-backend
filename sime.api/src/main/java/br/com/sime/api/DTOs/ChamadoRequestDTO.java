package br.com.sime.api.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChamadoRequestDTO {

    @NotNull(message = "Id do usuário é obrigatório")
    private Long idTipoPerfil;

    @NotBlank(message = "Título do chamado é obrigatório")
    private String tituloChamado;

    @NotBlank(message = "Descrição do chamado é obrigatória")
    private String descChamado;

    @NotBlank(message = "Local do chamado é obrigatório")
    private String localChamado;
}
