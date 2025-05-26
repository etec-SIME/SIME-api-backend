package br.com.sime.api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Tipo_Chamado")
public class TipoChamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_chamado", nullable = false)
    private Long idTipoChamado;

    @Column(name = "nome_tipo_chamado", length = 50, nullable = false)
    private String nomeTipoChamado;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;
}
