package br.com.sime.api.entities.chamados;

import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.entities.outros.Departamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "tipoChamado")
    //@JsonManagedReference
    @JsonIgnoreProperties("tipoChamado")
    private List<Chamado> chamadoList;

    @OneToMany(mappedBy = "tipoChamado")
    @JsonManagedReference
    private List<TipoEquipamento> tipoEquipamentoList;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    @JsonManagedReference
    private Departamento departamento;
}
