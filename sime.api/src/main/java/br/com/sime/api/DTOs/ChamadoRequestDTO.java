package br.com.sime.api.DTOs;

import br.com.sime.api.enums.PrioridadeChamadoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChamadoRequestDTO {
    @NotBlank(message = "Título do chamado é obrigatório")
    private String tituloChamado;

    @NotBlank(message = "Descrição do chamado é obrigatória")
    private String descChamado;

    @NotBlank(message = "Local do chamado é obrigatório")
    private String localChamado;

    @NotBlank(message = "Email do usuário é obrigatório")
    @Email(message = "Email inválido")
    private String emailUsuario;

    @NotBlank(message = "Tipo de chamado é obrigatório")
    private String tipoChamado;

    @NotBlank(message = "Imagem do chamado é obrigatória")
    private String imgChamado;

    private PrioridadeChamadoEnum prioridadeChamado;

}
