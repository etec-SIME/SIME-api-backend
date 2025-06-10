package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.outros.Departamento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class GestorDepartamento extends Usuario {


//    @ManyToMany
//    @JsonManagedReference
//    @JoinTable(
//            name = "Tem",
//            joinColumns = @JoinColumn(name = "rm_usuario"),
//            inverseJoinColumns = @JoinColumn(name = "id_departamento")
//    )
//    private List<Departamento> departamentoList;
}
