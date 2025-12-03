package br.com.sime.api.repositories;

import br.com.sime.api.entities.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestorGeralRepository extends JpaRepository<Usuario, String> {
}
