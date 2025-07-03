package br.com.sime.api.entities.chamados;

import br.com.sime.api.entities.escola.Ambiente;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
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
    private LocalDateTime dtAberturaChamado;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status_chamado", nullable = false)
    private StatusChamadoEnum statusChamado;

    @Column(name = "prioridade_chamado", nullable = false)
    private String prioridadeChamado;

    @ManyToOne
    @JoinColumn(name = "rm_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rm_usuario_responsavel")
    private Usuario usuarioResponsavel;

    @ManyToOne
    @JoinColumn(name = "id_ambiente")
    private Ambiente ambiente;

    @ManyToOne
    @JoinColumn(name = "id_tipo_chamado")
    private TipoChamado tipoChamado;

    @OneToMany(mappedBy = "chamado")
    private List<Feedback> feedbackList;

//    @Column(name = "msg_resolucao", length = 500)
//    private String msgResolucao;
}
