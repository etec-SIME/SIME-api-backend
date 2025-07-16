package br.com.sime.api.repositories;

import br.com.sime.api.entities.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Consulta via JPQL que traduz os objetos e relacionamentos do JPA para SQL, ou seja, faz uma consulta SQL através dos objetos do Java.
    // É possível fazer uma consulta com SQL, porém essa é mais legível e fácil de manter.
    // A consulta busca um objeto Usuario (u) que tenha um TipoPerfil (tp) e uma Escola (e) associados.

    // u -> Objeto Usuario
    // tp -> Objeto TipoPerfil
    // e -> Objeto Escola
    // MEMBER OF -> Verifica se o TipoPerfil (tp) é membro da lista de TipoPerfil (tipoPerfilList) da Escola (e). É necessário utilizar porque não existe a entidade Cadastra mapeada na API que é quem realiza o N:N de TipoPerfil e Escola e o JPQL não aceita JOIN N:N diretamente.
    // : rmUsuario, :idTipoPerfil e :codEscola são parâmetros que serão passados na consulta, representam o @Param("rmUsuario"), @Param("idTipoPerfil") e @Param("codEscola") respectivamente.
    @Query(value = """
        SELECT u FROM Usuario u
        JOIN u.tipoPerfil tp
        JOIN Escola e ON tp MEMBER OF e.tipoPerfilList
        WHERE u.rmUsuario = :rmUsuario
            AND tp.idTipoPerfil = :idTipoPerfil
            AND e.codEscola = :codEscola
    """)
    Optional<Usuario> findUsuarioTipoPerfilAndEscola(@Param("rmUsuario") String rmUsuario,
                                                     @Param("idTipoPerfil") Long idTipoPerfil,
                                                     @Param("codEscola") String codEscola);

    Optional<Usuario> findByRmUsuario(String rmUsuario);

    List<Usuario> findAllByTipoPerfil_IdTipoPerfilAndDepartamentoList_IdDepartamento(Long idtipoPerfil, Long idDepartamento);

    Boolean existsByRmUsuario(String rmUsuario);
    Boolean existsByCpfUsuario(String cpfUsuario);

}
