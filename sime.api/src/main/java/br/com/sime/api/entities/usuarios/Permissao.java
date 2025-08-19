package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.usuarios.TipoPerfil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Permissao")
@Schema(description = "Dados das permissões dos usuários")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permissao", nullable = false)
    @Schema(example = "1")
    private Long idPermissao;

    @Column(name = "nome_permissao", length = 50, nullable = false)
    @Schema(example = "Recusar chamados")
    private String nomePermissao;

    @Column(name = "desc_permissao", length = 450)
    @Schema(example = "Permite que os gestores recusem um chamado")
    private String descricaoPermissao;

    @ManyToMany(mappedBy = "permissaoList")
    @JsonBackReference
    private List<TipoPerfil> tipoPerfilList;
}
