package br.com.sime.api.DTOs;

import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record ChamadoRequestDTO (
    @NotBlank(message = "Título do chamado é obrigatório")
    String tituloChamado,

    @NotBlank(message = "Descrição do chamado é obrigatória")
    String descChamado,

    @NotBlank(message = "Email do usuário é obrigatório")
    @Email(message = "Email inválido")
    String emailUsuario,

    @NotBlank(message = "Imagem do chamado é obrigatória")
    String imgChamado,

    @NotNull
    Long idTipoChamado,

    @NotNull
    String codEquipamento,

    @NotNull
    Long tipoAmbienteId,

    @NotNull
    Long idAmbiente
) {}
