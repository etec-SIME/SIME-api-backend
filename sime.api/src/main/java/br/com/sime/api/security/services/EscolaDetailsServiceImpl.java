package br.com.sime.api.security.services;

import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.repositories.EscolaRepository;
import br.com.sime.api.security.EscolaDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("escolaDetailsService")
public class EscolaDetailsServiceImpl implements UserDetailsService {

    @Autowired private EscolaRepository escolaRepository;

    @Override
    public UserDetails loadUserByUsername(String cnpj) throws UsernameNotFoundException {
        Escola escola = escolaRepository.findByCnpjEscola(cnpj)
                .orElseThrow(() -> new UsernameNotFoundException("CNPJ não encontrado"));
        return new EscolaDetailsImpl(escola, "ESCOLA");
    }
}
