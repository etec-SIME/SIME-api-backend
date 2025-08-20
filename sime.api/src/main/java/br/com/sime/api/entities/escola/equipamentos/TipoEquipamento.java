package br.com.sime.api.entities.escola.equipamentos;

import br.com.sime.api.entities.escola.ambiente.Ambiente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Tipo_equipamento")
@Schema(description = "Dados de tipo de equipamento")

public class TipoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_equipamento", nullable = false)
    @Schema(example = "1")
    private Long idTipoEquipamento;

    @Column(name = "nome_tipo_equipamento", length = 50, nullable = false)
    @Schema(example = "Ventilador")
    private String nomeTipoEquipamento;

<<<<<<< HEAD
    @Column(name = "img_tipo_equipamento")
    @Schema(example = "*imagem*")
    private String imgTipoEquipamento;

=======
>>>>>>> 17680922dadb664d43e7b9233c9bc3544f0b4d0e
    @OneToMany(mappedBy = "tipoEquipamento")
    @JsonManagedReference
    private List<Equipamento> equipamentoList;

    @ManyToMany(mappedBy = "tipoEquipamentoList")
    @JsonBackReference
    private List<Ambiente> ambienteList;
}
