package br.com.sime.api.DTOs;

import lombok.Data;

@Data
public class ChamadoDTO {
    private Long idTipoPerfil;
    private String tituloChamado;
    private String descChamado;
    private String localChamado;
}
