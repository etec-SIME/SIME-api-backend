package br.com.sime.api.entities.outros;

import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.usuarios.GestorDepartamento;
import br.com.sime.api.entities.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento", nullable = false)
    private Long idDepartamento;

    @Column(name = "nome_departamento", length = 50, nullable = false)
    private String nomeDepartamento;

    @Column(name = "desc_departamento", length = 450)
    private String descDepartamento;

    @OneToMany(mappedBy = "departamento")
    private List<TipoChamado> tipoChamadoList;

    @ManyToMany(mappedBy = "departamentoList")
    private List<Usuario> usuarioList;

    @OneToOne(mappedBy = "departamento")
    private GestorDepartamento gestorDepartamento;
}
