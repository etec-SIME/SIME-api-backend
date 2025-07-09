package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    public List<Usuario> getAllFuncionarios() {
        try {
            return usuarioRepository.findByTipoPerfilNomeTipoPerfil("Funcionário");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionarios: " + e.getMessage(), e);
        }
    }

    public List<Chamado> getAllChamadosPendentes(){ // Fazer um metodo que exiba tambem apenas os pendentes de um funcionario especifico para as estatisticas?
        try{
            return chamadoRepository.findByStatusChamado(StatusChamadoEnum.PENDENTE);
        } catch (Exception e){
            throw new RuntimeException("Erro ao encontrar os chamados pendentes: " + e.getMessage(), e);
        }
    }

    public List<Chamado> getAllChamadosConcluidos(){ // Fazer um metodo que exiba tambem apenas os concluidos de um funcionário especifico para as estatisticas?
        try{
            return chamadoRepository.findByStatusChamado(StatusChamadoEnum.CONCLUIDO);
        } catch (Exception e){
            throw new RuntimeException("Erro ao encontrar os chamados concluídos: " + e.getMessage(), e);
        }
    }

    /*public List<Chamado> verificarResolucaoChamado() { // Verifica todos os chamados, pendentes e concluidos
        try {
            return chamadoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar chamados: " + e.getMessage(), e);
        }
    }*/

}
