package br.com.sime.api.entities.escola.ambientes;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Tipo_ambiente")
public class TipoAmbiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_ambiente", nullable = false)
    private Long idTipoAmbiente;

    @Column(name = "nome_tipo_ambiente", length = 50, nullable = false)
    private String nomeTipoAmbiente;

    @OneToMany(mappedBy = "ambientes")
    @JsonManagedReference
    private List<Ambiente> ambienteList;

    //@OneToMany(mappedBy = "ambiente")
    //@JsonManagedReference
    //private List<Chamado> chamadosList;

}
