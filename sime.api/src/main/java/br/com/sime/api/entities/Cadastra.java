package br.com.sime.api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Cadastra")
public class Cadastra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cadastra", nullable = false)
    private Integer idCadastra;

    @ManyToOne
    @JoinColumn(name = "cod_escola", referencedColumnName = "cod_escola", nullable = false)
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "id_tipo_perfil", referencedColumnName = "id_tipo_perfil", nullable = false)
    private TipoPerfil tipoPerfil;
}
