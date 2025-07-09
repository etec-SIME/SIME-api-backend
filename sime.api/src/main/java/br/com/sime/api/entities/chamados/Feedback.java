package br.com.sime.api.entities.chamados;

import br.com.sime.api.entities.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback", nullable = false)
    private Long idFeedback;

    @Column(name = "destinatario_feedback", length = 50, nullable = false)
    private String destinatarioFeedback;

    @Column(name = "remetente_feedback", length = 50, nullable = false)
    private String remetenteFeedback;

    @Column(name = "dt_feedback")
    private LocalDateTime dtFeedback;

    @Column(name = "desc_feedback", length = 450)
    private String descFeedback;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "rm_usuario", referencedColumnName = "rm_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_chamado", referencedColumnName = "id_chamado", nullable = false)
    private Chamado chamado;
}
