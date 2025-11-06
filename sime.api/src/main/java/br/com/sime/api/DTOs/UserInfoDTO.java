package br.com.sime.api.DTOs;

import java.util.List;

public record UserInfoDTO(
        String entidade,
        String identificador,
        List<String> permissoes
) { }
