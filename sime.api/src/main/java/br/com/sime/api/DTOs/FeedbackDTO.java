package br.com.sime.api.DTOs;

import lombok.Data;

@Data
public class FeedbackDTO {
    private String destinatario;
    private String descricao;
}
