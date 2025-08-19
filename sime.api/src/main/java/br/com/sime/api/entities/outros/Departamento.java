package br.com.sime.api.entities.outros;

import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Departamento")
@Schema(description = "Dados dos departamentos de uma escola")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento", nullable = false)
    @Schema(example = "1")
    private Long idDepartamento;

    @Column(name = "nome_departamento", length = 50, nullable = false)
    @Schema(example = "Estrutural")
    private String nomeDepartamento;

    @Column(name = "desc_departamento", length = 450)
    @Schema(example = "Cuidam de chamados relacionados a estrutura da escola")
    private String descDepartamento;

    @OneToMany(mappedBy = "departamento")
    @JsonManagedReference
    private List<TipoChamado> tipoChamadoList;

    @ManyToMany(mappedBy = "departamentoList")
    @JsonBackReference
    private List<Usuario> usuarioList;
}
