package br.com.sime.api.entities;

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

    @Column(name = "dt_feedback")
    private LocalDateTime dtFeedback;

    @Column(name = "desc_feedback", length = 450)
    private String descFeedback;

    @ManyToOne
    @JoinColumn(name = "id_chamado", referencedColumnName = "id_chamado", nullable = false)
    private Chamado chamado;

    @ManyToOne
    @JoinColumn(name = "rm_usuario", referencedColumnName = "rm_usuario", nullable = false)
    private Usuario usuario;
}
