package br.com.sime.api.entities.escola.equipamentos;

import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Equipamento")
public class Equipamento {

    @Id
    @Column(name = "cod_equipamento", nullable = false)
    private String codEquipamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "id_tipo_equipamento")
    private TipoEquipamento tipoEquipamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "id_ambiente")
    private Ambiente ambiente;
}
