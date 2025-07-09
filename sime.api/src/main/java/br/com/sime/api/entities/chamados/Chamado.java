package br.com.sime.api.entities.chamados;

import br.com.sime.api.entities.escola.Ambiente;
import br.com.sime.api.entities.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "status_chamado", nullable = false)
    private String statusChamado;

    @Column(name = "prioridade_chamado", nullable = false)
    private String prioridadeChamado;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "rm_usuario")
    private Usuario usuario;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "rm_usuario_responsavel")
    private Usuario usuarioResponsavel;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_ambiente")
    private Ambiente ambiente;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_tipo_chamado")
    private TipoChamado tipoChamado;

    @OneToMany(mappedBy = "chamado")
    @JsonManagedReference
    private List<Feedback> feedbackList;
}
