package br.com.sime.api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Gerenciar")
public class Gerenciar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gerenciar", nullable = false)
    private Integer idGerenciar;

    @ManyToOne
    @JoinColumn(name = "rm_usuario", referencedColumnName = "rm_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cod_escola", referencedColumnName = "cod_escola", nullable = false)
    private Escola escola;
}
