package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.usuarios.TipoPerfil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Permissao")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permissao", nullable = false)
    private Long idPermissao;

    @Column(name = "nome_permissao", length = 50, nullable = false)
    private String nomePermissao;

    @Column(name = "desc_permissao", length = 450)
    private String descricaoPermissao;

    @ManyToMany(mappedBy = "permissaoList")
    @JsonBackReference
    private List<TipoPerfil> tipoPerfilList;
}
