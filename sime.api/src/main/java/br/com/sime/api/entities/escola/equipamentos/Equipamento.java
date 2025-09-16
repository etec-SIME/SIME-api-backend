package br.com.sime.api.entities.escola.equipamentos;

import br.com.sime.api.entities.chamados.TipoChamado;
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
    private String codEquipamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "id_tipo_equipamento")
    private TipoEquipamento tipoEquipamento;
}
