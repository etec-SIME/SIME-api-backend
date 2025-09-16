package br.com.sime.api.entities.chamados;

import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
import br.com.sime.api.entities.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private Long idChamado;

    @Column(name = "dt_abertura_chamado")
    @Schema(example = "18/08/2025")
    private LocalDateTime dtAberturaChamado;

    @Column(name = "desc_chamado", length = 450)
    @Schema(example = "Computador quebrado no laboratório 2")
    private String descChamado;

    @Column(name = "dt_conclusao_chamado")
    @Schema(example = "26/08/2025")
    private LocalDateTime dtConclusaoChamado;

    @Column(name = "titulo_chamado")
    @Schema(example = "Computador quebrado")
    private String tituloChamado;

    @Column(name = "status_chamado", nullable = false)
    @Schema(example = "Pendente")
    private String statusChamado;

    @Column(name = "prioridade_chamado", nullable = false)
    @Schema(example = "Média")
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
    @JoinColumn(name = "id_tipo_chamado")
    private TipoChamado tipoChamado;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "id_tipo_ambiente")
    private TipoAmbiente tipoAmbiente;

    @OneToMany(mappedBy = "chamado")
    @JsonManagedReference
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "chamado")
    @JsonManagedReference
    private List<ImagemChamado> imagemChamadoList;
}
