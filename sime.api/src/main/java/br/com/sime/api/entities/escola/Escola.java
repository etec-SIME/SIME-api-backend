package br.com.sime.api.entities.escola;

import br.com.sime.api.entities.usuarios.TipoPerfil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Escola")
@Schema(description = "Dados de uma escola")
public class Escola {

    @Id
    @Column(name = "cod_escola", length = 3, columnDefinition = "CHAR(3)", nullable = false)
    @Schema(example = "064")
    private String codEscola;

    @Column(name = "cnpj_escola", length = 14, columnDefinition = "CHAR(14)")
    @Schema(example = "62.823.257/0064-84")
    private String cnpjEscola;

    @Column(name = "senha_escola", length = 30)
    @Schema(example = "*123456HAS*")
    private String senhaEscola;

    @Column(name = "cep_escola", length = 8, columnDefinition = "CHAR(8)")
    @Schema(example = "02110-010")
    private String cepEscola;

    @Column(name = "num_endereco_escola")
    @Schema(example = "R. Alcântara, 113 - Vila Guilherme")
    private String numEnderecoEscola;

    @Column(name = "nome_escola", length = 100)
    @Schema(example = "Etec Horário Augusto da Silveira")
    private String nomeEscola;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "Cadastra",
        joinColumns = @JoinColumn(name = "cod_escola"),
        inverseJoinColumns = @JoinColumn(name = "id_tipo_perfil")
    )
    private List<TipoPerfil> tipoPerfilList;
}
