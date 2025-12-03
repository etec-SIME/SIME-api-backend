package br.com.sime.api.DTOs.Requests;

import java.util.List;

public record PermissaoTipoPerfilRequestDTO (
        List<Long> idPermissoes
) { }
