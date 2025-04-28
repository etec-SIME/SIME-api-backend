package br.com.sime.api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Escola")
public class Escola {

    @Id
    @Column(name = "cod_escola", length = 3, columnDefinition = "CHAR(3)", nullable = false)
    private String codEscola;

    @Column(name = "cnpj_escola", length = 14, columnDefinition = "CHAR(14)")
    private String cnpjEscola;

    @Column(name = "cep_escola", length = 8, columnDefinition = "CHAR(8)")
    private String cepEscola;

    @Column(name = "num_endereco_escola")
    private String numEnderecoEscola;

    @Column(name = "nome_escola", length = 100)
    private String nomeEscola;

    @ManyToMany
    @JoinTable(
        name = "Cadastra",
        joinColumns = @JoinColumn(name = "cod_escola"),
        inverseJoinColumns = @JoinColumn(name = "id_tipo_perfil")
    )
    private List<TipoPerfil> tipoPerfilList;
}
