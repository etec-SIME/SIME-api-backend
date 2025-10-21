package br.com.sime.api.entities.escola.ambiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Ambiente")
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ambiente", nullable = false)
    private Long idAmbiente;

    @Column(name = "num_ambiente",  nullable = false)
    private Long numAmbiente;

    @Column(name = "desc_ambiente", length = 450, nullable = false)
    private String descricaoAmbiente;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "id_tipo_ambiente")
    private TipoAmbiente tipoAmbiente;

    @OneToMany(mappedBy = "ambiente")
    @JsonManagedReference
    private List<Equipamento> equipamentosList;

}
