package br.com.sime.api.entities.chamados;

import br.com.sime.api.entities.chamados.historicos.HistoricoStatusProgresso;
import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
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

    @Column(name = "titulo_chamado")
    private String tituloChamado;

    @Column(name = "status_atual_geral_chamado", nullable = false)
    private String statusAtualGeralChamado;

    @Column(name = "status_atual_progresso_chamado", nullable = false)
    private String statusAtualProgressoChamado;

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
    @JsonIgnoreProperties("chamadoList")
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

    @OneToMany(mappedBy = "chamado")
    @JsonManagedReference
    private List<HistoricoStatusProgresso> historicoStatusProgressoList;
}
