package br.com.jhonnyazevedo.job_vacancy_management.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {

    private String password;
    private String username;
}
