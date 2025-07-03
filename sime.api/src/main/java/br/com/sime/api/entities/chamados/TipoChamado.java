package br.com.sime.api.entities.chamados;

import br.com.sime.api.entities.outros.Departamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Departamento departamento;
}
