package br.com.sime.api.entities.escola.equipamentos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Equipamento")
public class Equipamento {

    @Id
    @Column(name = "cod_equipamento", nullable = false)
    private Long codEquipamento;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_tipo_equipamento")
    private TipoEquipamento tipoEquipamento;
}
