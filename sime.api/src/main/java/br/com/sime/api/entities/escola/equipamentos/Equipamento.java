package br.com.sime.api.entities.escola.equipamentos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Equipamento")
@Schema(description = "Dados dos equipamentos")
public class Equipamento {

    @Id
    @Column(name = "cod_equipamento", nullable = false)
    @Schema(example = "1")
    private Long codEquipamento;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_tipo_equipamento")
    private TipoEquipamento tipoEquipamento;
}
