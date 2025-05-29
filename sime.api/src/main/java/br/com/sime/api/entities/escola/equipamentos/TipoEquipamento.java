package br.com.sime.api.entities.escola.equipamentos;

import br.com.sime.api.entities.escola.Ambiente;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Tipo_equipamento")
public class TipoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_equipamento", nullable = false)
    private Long idTipoEquipamento;

    @Column(name = "nome_tipo_equipamento", length = 50, nullable = false)
    private String nomeTipoEquipamento;

    @Column(name = "img_tipo_equipamento")
    private String imgTipoEquipamento;

    @OneToMany(mappedBy = "tipoEquipamento")
    private List<Equipamento> equipamentoList;

    @ManyToMany(mappedBy = "tipoEquipamentoList")
    private List<Ambiente> ambienteList;
}
