package br.com.sime.api.entities.escola.ambiente;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Ambiente")
@Schema(description = "Dados dos Ambientes")
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ambiente", nullable = false)
    @Schema(example = "1")
    private Long idAmbiente;

    @Column(name = "num_ambiente",  nullable = false)
    private Long numAmbiente;

    @Column(name = "desc_ambiente", length = 450, nullable = false)
    @Schema(example = "Laboratório 2, perto do pátio")
    private String descricaoAmbiente;

//    @Column(name = "nome_ambiente", length = 50, nullable = false)
//    @Schema(example = "Laboratório 2")
//    private String nomeAmbiente;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_tipo_ambiente")
    private Tipo_Ambiente tipoAmbiente;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "Contem",
            joinColumns = @JoinColumn(name = "id_ambiente"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo_equipamento")
    )
    private List<TipoEquipamento> tipoEquipamentoList;

}
