package br.com.sime.api.entities.usuarios;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacao")
    private Long idNotificacao;

    @Column(name = "data_notificacao")
    private LocalDateTime dataNotificacao;

    @Column(name = "visualizacao")
    private Boolean visualizacao;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_mensagem")
    private Mensagem mensagem;

    @ManyToMany(mappedBy = "notificacaoList")
    @JsonBackReference
    private List<Usuario> usuarioList;
}
