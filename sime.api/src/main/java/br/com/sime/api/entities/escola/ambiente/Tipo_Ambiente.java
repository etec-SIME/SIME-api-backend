package br.com.sime.api.entities.escola.ambiente;

import br.com.sime.api.entities.chamados.Chamado;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Tipo_Ambiente")
public class Tipo_Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_ambiente", nullable = false)
    private Long idTipoAmbiente;

    @Column(name = "nome_tipo_ambiente", nullable = false)
    private String nomeTipoAmbiente;

    @OneToMany(mappedBy = "tipoAmbiente")
    @JsonManagedReference
    private List<Chamado> chamadosList;

    @OneToMany(mappedBy = "tipoAmbiente")
    @JsonManagedReference
    private List<Ambiente> ambienteList;
}
