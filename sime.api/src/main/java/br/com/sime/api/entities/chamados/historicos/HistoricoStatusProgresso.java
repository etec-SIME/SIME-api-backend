package br.com.sime.api.entities.chamados.historicos;

import br.com.sime.api.entities.chamados.Chamado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Historico_Status_Progresso")
public class HistoricoStatusProgresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico_status_progresso")
    Long idHistoricoStatusProgresso;

    @Column(name = "status_progresso")
    String statusProgresso;

    @Column(name = "dt_alteracao")
    LocalDateTime dtAlteracao;

    @Column(name = "dia_semana")
    String diaSemana;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_chamado")
    Chamado chamado;
}
