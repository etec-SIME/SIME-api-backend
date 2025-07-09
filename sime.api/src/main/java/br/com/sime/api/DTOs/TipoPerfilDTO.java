package br.com.sime.api.DTOs;

import br.com.sime.api.entities.usuarios.Permissao;
import lombok.Data;

import java.util.List;

@Data
public class TipoPerfilDTO {
    private String nomeTipoPerfil;
    private List<Long> permissaoIds;
}
