package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.usuarios.Usuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GESTOR_GERAL")
public class GestorGeral extends Usuario {
}
