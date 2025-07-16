package br.com.sime.api.services;

import br.com.sime.api.DTOs.PermissaoTipoPerfilDTO;
import br.com.sime.api.DTOs.TipoPerfilDTO;
import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.EscolaRepository;
import br.com.sime.api.repositories.PermissaoRepository;
import br.com.sime.api.repositories.TipoPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TipoPerfilService {

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<TipoPerfil> getAllTipoPerfis(){
        try {
            return tipoPerfilRepository.findAll();
        }catch (Exception e)
        {
            throw new RuntimeException("Erro ao buscar os departamentos: " + e.getMessage(), e);
        }
    }


    public List<Permissao> getAllPermissaoTipoPerfil(Long idTipoPerfil){
       TipoPerfil tipoPerfil = tipoPerfilRepository.findById(idTipoPerfil)
               .orElseThrow(() -> new NotFoundException("Tipo perfil de ID: " + idTipoPerfil +"não encontrado"));

       return tipoPerfil.getPermissaoList();
    }

    public TipoPerfil criarTipoPerfil(TipoPerfilDTO dto){
        TipoPerfil tipoPerfil = new TipoPerfil();
        tipoPerfil.setNomeTipoPerfil(dto.getNomeTipoPerfil());
        return tipoPerfilRepository.save(tipoPerfil);
    }

    public TipoPerfil atribuirPermissoes(Long idTipoPerfil, PermissaoTipoPerfilDTO dto){
        TipoPerfil tipoPerfil = tipoPerfilRepository.findById(idTipoPerfil)
                .orElseThrow(() -> new NotFoundException("Tipo perfil de ID: " + idTipoPerfil +"não encontrado"));

        List<Permissao> permissaos = permissaoRepository.findAllById(dto.getIdPermissoes());

        Set<Long> idsEncontrados = permissaos.stream()
                .map(Permissao::getIdPermissao)
                .collect(Collectors.toSet());

        List<Long> idsNaoEncontrados = dto.getIdPermissoes().stream()
                .filter( id -> !idsEncontrados.contains(id))
                .toList();

        if (!idsNaoEncontrados.isEmpty()) {
            throw new NotFoundException(
                    "Permissões não encontradas",
                    "IDs inválidos: " + idsNaoEncontrados
            );
        }

        tipoPerfil.setPermissaoList(permissaos);
        return tipoPerfilRepository.save(tipoPerfil);

    }

    public TipoPerfil editarTipoPerfil(Long idTipoPerfil, TipoPerfilDTO tipoPerfilDTO){
        TipoPerfil tipoPerfil = tipoPerfilRepository.findById(idTipoPerfil)
                .orElseThrow(() -> new NotFoundException("Tipo perfil de ID: " + idTipoPerfil +"não encontrado"));

        tipoPerfil.setNomeTipoPerfil(tipoPerfilDTO.getNomeTipoPerfil());
        return tipoPerfilRepository.save(tipoPerfil);
    }

}
