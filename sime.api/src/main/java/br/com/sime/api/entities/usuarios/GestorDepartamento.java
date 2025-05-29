package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.outros.Departamento;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GESTOR_DEPARTAMENTO")
public class GestorDepartamento extends Usuario {
    @OneToOne
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;
}
