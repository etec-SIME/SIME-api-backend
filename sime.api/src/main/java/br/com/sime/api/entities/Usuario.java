package br.com.sime.api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @Column(name = "rm_usuario", length = 6, columnDefinition = "CHAR(6)", nullable = false)
    private String rmUsuario;

    @Column(name = "email_usuario", length = 50)
    private String emailUsuario;

    @Column(name = "nome_usuario", length = 30)
    private String nomeUsuario;

    @Column(name = "dt_nascimento_usuario")
    private LocalDateTime dtNascimentoUsuario;

    @Column(name = "telefone_usuario", length = 11, columnDefinition = "CHAR(11)")
    private String telefoneUsuario;

    @ManyToOne
    @JoinColumn(name = "id_tipo_perfil")
    private TipoPerfil tipoPerfil;

    @OneToMany(mappedBy = "usuario")
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chamado> chamadoList;
}
