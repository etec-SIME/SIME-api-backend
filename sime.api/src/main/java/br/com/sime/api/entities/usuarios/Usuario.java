package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.chamados.Feedback;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @Column(name = "rm_usuario", length = 6, columnDefinition = "CHAR(6)", nullable = false)
    private String rmUsuario;

    @Column(name = "chamados_abertos", length = 3, columnDefinition = "CHAR(3)")
    private String chamadosAbertos;

    @Column(name = "chamados_concluidos", length = 3, columnDefinition = "CHAR(3)")
    private String chamadosConcluidos;

    @Column(name = "email_usuario", length = 50)
    private String emailUsuario;

    @Column(name = "nome_usuario", length = 30)
    private String nomeUsuario;

    @JsonIgnore
    @Column(name = "senha_usuario", length = 30)
    private String senhaUsuario;

    @Column(name = "telefone_usuario", length = 11, columnDefinition = "CHAR(11)")
    private String telefoneUsuario;

    @Column(name = "cpf_usuario", length = 11, columnDefinition = "CHAR(11)")
    private String cpfUsuario;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_tipo_perfil")
    private TipoPerfil tipoPerfil;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Chamado> chamadoList;

    @OneToMany(mappedBy = "usuarioResponsavel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Chamado> chamadoResponsavelList;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "Conduz",
            joinColumns = @JoinColumn(name = "rm_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_departamento")
    )
    private List<Departamento> departamentoList;
}
