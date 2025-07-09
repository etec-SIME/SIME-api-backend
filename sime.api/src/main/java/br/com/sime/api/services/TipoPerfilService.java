package br.com.sime.api.services;

import br.com.sime.api.DTOs.TipoPerfilDTO;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.PermissaoRepository;
import br.com.sime.api.repositories.TipoPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TipoPerfilService {

    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<TipoPerfil> getAllTipoPerfil(){
        try {
            return tipoPerfilRepository.findAll();
        }catch (Exception e)
        {
            throw new RuntimeException("Erro ao buscar os departamentos: " + e.getMessage(), e);
        }
    }

    private TipoPerfil criarTipoPerfil(TipoPerfilDTO dto){

        List<Long> ids = dto.getPermissaoIds();
        List<Permissao> permissaos = permissaoRepository.findAllById(ids);

        Set<Long> idsEncontrados = permissaos.stream()
                .map(Permissao::getIdPermissao)
                .collect(Collectors.toSet());

        List<Long> idsNaoEncontrados = ids.stream()
                .filter( id -> !idsEncontrados.contains(id))//Verifica se o idPermissao existe
                .toList();

        if (!idsNaoEncontrados.isEmpty()) {
            throw new NotFoundException(
                    "Permissões não encontradas",
                    "IDs inválidos: " + idsNaoEncontrados
            );
        }

        TipoPerfil tipoPerfil = new TipoPerfil();
        tipoPerfil.setNomeTipoPerfil(dto.getNomeTipoPerfil());
        tipoPerfil.setPermissaoList(permissaos);
        return tipoPerfilRepository.save(tipoPerfil);
    }

    private TipoPerfil editarTipoPerfil(TipoPerfil tipoPerfilAtualizado){

        TipoPerfil tipoPerfil =  tipoPerfilRepository.findById(tipoPerfilAtualizado.getIdTipoPerfil())
                .orElseThrow(() -> new NotFoundException("Tipo Perfil de ID: " + tipoPerfilAtualizado.getIdTipoPerfil() + "não encontrado"));

        tipoPerfil.setNomeTipoPerfil(tipoPerfilAtualizado.getNomeTipoPerfil());
        return tipoPerfilRepository.save(tipoPerfil);
    }

}
