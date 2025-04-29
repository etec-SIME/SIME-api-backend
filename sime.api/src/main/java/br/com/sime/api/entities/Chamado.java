package br.com.sime.api.entities;

import br.com.sime.api.enums.TipoChamadoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Chamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chamado", nullable = false)
    private Long idChamado;

    @Column(name = "dt_abertura_chamado")
    private LocalDateTime dt_aberturaChamado;

    @Column(name = "desc_chamado", length = 450)
    private String descChamado;

    @Column(name = "dt_conclusao_chamado")
    private LocalDateTime dtConclusaoChamado;

    @Column(name = "img_chamado")
    private String imgChamado;

    @Column(name = "local_chamado")
    private String localChamado;

    @Column(name = "titulo_chamado")
    private String tituloChamado;

    @OneToMany(mappedBy = "chamado")
    private List<Feedback> feedbackList;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_chamado", nullable = false)
    private TipoChamadoEnum tipoChamado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_chamado", nullable = false)
    private TipoChamadoEnum statusChamado;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade_chamado", nullable = false)
    private TipoChamadoEnum prioridadeChamado;

    @ManyToMany(mappedBy = "chamadoList")
    private List<Usuario> usuarioList;
}
