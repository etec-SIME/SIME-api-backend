package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.escola.Escola;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Tipo_perfil")
@Schema(description = "Dados dos tipos de perfil do sistema")
public class TipoPerfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_perfil", nullable = false)
    @Schema(example = "1")
    private Long idTipoPerfil;

    @Column(name = "nome_tipo_perfil", length = 30)
    @Schema(example = "Gestor")
    private String nomeTipoPerfil;

    @ManyToMany(mappedBy = "tipoPerfilList")
    @JsonManagedReference
    private List<Escola> escolaList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinTable(
            name = "Possui",
            joinColumns = @JoinColumn(name = "id_tipo_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_permissao")
    )
    private List<Permissao> permissaoList;
}
