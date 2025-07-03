package br.com.sime.api.entities.escola;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
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

    @Column(name = "desc_ambiente", length = 450, nullable = false)
    private String descricaoAmbiente;

    @Column(name = "nome_ambiente", length = 50, nullable = false)
    private String nomeAmbiente;

    @OneToMany(mappedBy = "ambiente")
    @JsonManagedReference
    private List<Chamado> chamadosList;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "Contem",
            joinColumns = @JoinColumn(name = "id_ambiente"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo_equipamento")
    )
    private List<TipoEquipamento> tipoEquipamentoList;

}
