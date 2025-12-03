package br.com.sime.api.services;
import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.DepartamentoRepository;
import br.com.sime.api.repositories.EscolaRepository;
import br.com.sime.api.DTOs.LoginEscolaDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.security.EscolaDetailsImpl;
import br.com.sime.api.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscolaService {

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private JwtService jwtService;

    public List<Escola> getAllEscolas(){
        try {
            return escolaRepository.findAll();
        }catch (Exception e){
            throw  new RuntimeException("Erro ao buscar escolas: " + e.getMessage(), e);
        }
    }

    public Escola getEscolaById(String codEscola){
        return escolaRepository.findByCodEscola(codEscola)
                .orElseThrow(() -> new NotFoundException("Escola de código: " + codEscola + "não encontrado"));
    }

    public TokenDTO loginEscola(LoginEscolaDTO login) {
        boolean getEscola = escolaRepository.existsByCodEscola(login.getCodEscola());

        if (!getEscola)
            throw new RuntimeException("Escola com código: " + login.getCodEscola() + " não encontrada");

        Escola escola = escolaRepository.findByCnpjEscola(login.getCnpjEscola())
                .orElseThrow(() -> new RuntimeException("CNPJ: " + login.getCnpjEscola() + " não encontrado"));

        if (!escola.getSenhaEscola().equals(login.getSenhaEscola()))
            throw new RuntimeException("Senha incorreta para a escola com CNPJ: " + login.getCnpjEscola());

        String token = jwtService.generateToken(new EscolaDetailsImpl(escola, "ESCOLA"));

        return new TokenDTO(token);
    }

}
