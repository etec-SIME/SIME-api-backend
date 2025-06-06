package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.escola.Escola;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Tipo_perfil")
public class TipoPerfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_perfil", nullable = false)
    private Long idTipoPerfil;

    @Column(name = "nome_tipo_perfil", length = 30)
    private String nomeTipoPerfil;

    @ManyToMany(mappedBy = "tipoPerfilList")
    private List<Escola> escolaList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Possui",
            joinColumns = @JoinColumn(name = "id_tipo_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_permissao")
    )
    private List<Permissao> permissaoList;
}
