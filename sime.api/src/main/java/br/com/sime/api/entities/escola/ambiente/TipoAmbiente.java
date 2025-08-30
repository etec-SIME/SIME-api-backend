package br.com.sime.api.entities.escola.ambiente;

import br.com.sime.api.entities.chamados.Chamado;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Tipo_Ambiente")
public class TipoAmbiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_ambiente", nullable = false)
    private Long idTipoAmbiente;

    @Column(name = "nome_tipo_ambiente", nullable = false)
    private String nomeTipoAmbiente;

    @OneToMany(mappedBy = "tipoAmbiente")
    private List<Chamado> chamadosList;

    @OneToMany(mappedBy = "tipoAmbiente")
    private List<Ambiente> ambienteList;
}
