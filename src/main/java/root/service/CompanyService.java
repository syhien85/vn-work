package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.*;
import root.entity.*;
import root.exception.ResourceNotFoundException;
import root.repository.CompanyRepo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepo companyRepo;

    @Transactional
    public void create(CompanyDTO companyDTO) {
        Company company = new ModelMapper().map(companyDTO, Company.class);

        // set role = Company before save
        Account account = company.getAccount();
        account.setRoles(List.of(Role.builder().id(3).build()));

        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

        company.setAccount(account);

        companyRepo.save(company);
    }

    @Transactional
    public void update(CompanyDTO companyDTO) {
        Company currentCompany = companyRepo.findById(companyDTO.getAccount().getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "company with id [" + companyDTO.getAccount().getId() + "] not found"
            )
        );

        // set role = Company before save
        Account account = currentCompany.getAccount();
        account.setRoles(List.of(Role.builder().id(3).build()));

        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

        currentCompany.setAccount(account);
        currentCompany.setCompanyInfo(
            new ModelMapper().map(companyDTO.getCompanyInfo(), CompanyInfo.class)
        );

        companyRepo.save(currentCompany);
    }

    @Transactional
    public void delete(Long id) {
        if (!companyRepo.existsById(id)) {
            throw new ResourceNotFoundException("company with id [" + id + "] not found");
        }
        companyRepo.deleteById(id);
    }

    public CompanyDTO getById(Long id) {
        return companyRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("company with id [" + id + "] not found")
        );
    }

    public PageDTO<CompanyDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<Company> page = companyRepo.searchByCompanyName(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<CompanyDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    @Transactional
    public void updateCompanyLogo(CompanyDTO companyDTO) {
        Company currentCompany = companyRepo.findById(companyDTO.getAccount().getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "company with id [" + companyDTO.getAccount().getId() + "] not found"
            )
        );
        CompanyInfo companyInfo = currentCompany.getCompanyInfo();
        companyInfo.setCompanyLogoUrl(companyDTO.getCompanyInfo().getCompanyLogoUrl());
        companyRepo.save(currentCompany);
    }

    private CompanyDTO convert(Company company) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(company, CompanyDTO.class);
    }
}

