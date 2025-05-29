package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.usuarios.Usuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FUNCIONARIO")
public class Funcionario extends Usuario {
}
