package br.com.jhonnyazevedo.job_vacancy_management.services;

import br.com.jhonnyazevedo.job_vacancy_management.dtos.AuthCompanyDTO;
import br.com.jhonnyazevedo.job_vacancy_management.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthCompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        // Verificar se a Company, se não existir lançar uma exceção
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                   throw new UsernameNotFoundException("Company not found");
                }
        );

        // Caso exista a Company
        // Verificar se a senha esta correta
        //                                           (senha passada pelo Front, senha do banco com criptografia)
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        // Se não estiver correta -> Erro
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Se estiver correta -> Gerar o token

    }

}
