SELECT
    u.rm_usuario as rmUsuario, u.senha_usuario as senhaUsuario, tp.nome_tipo_perfil as nomeTipoPerfil, es.cod_escola as codEscola
FROM
    Usuario u
JOIN
    Tipo_perfil tp ON u.id_tipo_perfil = tp.id_tipo_perfil
JOIN
    Cadastra c ON tp.id_tipo_perfil = c.id_tipo_perfil
JOIN
    Escola es ON c.cod_escola = es.cod_escola
