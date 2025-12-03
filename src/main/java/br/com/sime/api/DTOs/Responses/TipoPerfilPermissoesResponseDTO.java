package br.com.sime.api.DTOs.Responses;

import br.com.sime.api.entities.usuarios.Permissao;

import java.util.List;

public record TipoPerfilPermissoesResponseDTO(
    Long idTipoPerfil,
    String nomeTipoPerfil,
    List<Permissao> permissaoList
) {
}
