package br.com.sime.api.services;

import br.com.sime.api.DTOs.PermissaoDTO;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.PermissaoRepository;
import br.com.sime.api.repositories.TipoPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissaoService {
    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;

    public List<Permissao> getAllPermissoes(){
        try {
            return permissaoRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar os permissões: " + e.getMessage(), e);
        }
    }

    public Permissao getPermissaoById(Long idPermissao){
        return permissaoRepository.findById(idPermissao)
                .orElseThrow(() -> new NotFoundException("Permissao de ID: " + idPermissao + "não encontrado"));
    }


    public Permissao criarPermissao(PermissaoDTO dto){
        Permissao permissao = new Permissao();
        permissao.setNomePermissao(dto.getNomePermissao());
        permissao.setDescricaoPermissao(dto.getDescricaoPermissao());

        return permissaoRepository.save(permissao);
    }

    public  Permissao editarPermissao(Permissao permissaoAtualizada){
        Permissao permissao = permissaoRepository.findById(permissaoAtualizada.getIdPermissao())
                .orElseThrow(() -> new NotFoundException("Permissao de ID: " + permissaoAtualizada.getIdPermissao() + "não encontrado"));

        permissao.setNomePermissao(permissaoAtualizada.getNomePermissao());
        permissao.setDescricaoPermissao(permissaoAtualizada.getDescricaoPermissao());

        return permissaoRepository.save(permissao);

    }
}
