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

SELECT
	te.nome_tipo_equipamento, e.cod_equipamento, ta.id_tipo_ambiente ,ta.nome_tipo_ambiente, a.id_ambiente, tc.id_tipo_chamado, tc.nome_tipo_chamado
FROM
	Tipo_equipamento te
INNER JOIN Equipamento e
	ON e.id_tipo_equipamento = te.id_tipo_equipamento
INNER JOIN Ambiente a
	ON te.id_ambiente = a.id_ambiente
INNER JOIN Tipo_Ambiente ta
	ON ta.id_tipo_ambiente = a.id_tipo_ambiente
INNER JOIN Chamado c
	ON c.id_tipo_ambiente = ta.id_tipo_ambiente
INNER JOIN Tipo_Chamado tc
	ON tc.id_tipo_chamado = c.id_tipo_chamado


SELECT u.rm_usuario, u.id_tipo_perfil, u.senha_usuario, es.cod_escola
FROM Usuario u
JOIN Tipo_perfil tp ON u.id_tipo_perfil = tp.id_tipo_perfil
JOIN Cadastra cd ON tp.id_tipo_perfil = cd.id_tipo_perfil
JOIN Escola es ON cd.cod_escola = es.cod_escola

