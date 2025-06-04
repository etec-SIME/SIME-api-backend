package br.com.sime.api.DTOs;

import br.com.sime.api.entities.chamados.TipoChamado;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    //@NotNull(message = "Data de abertura é obrigatória")
    //private LocalDateTime dtAberturaChamado;

    private String imgChamado;
}
