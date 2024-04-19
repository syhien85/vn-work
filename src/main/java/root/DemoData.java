package root;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import root.client.service.IndustryFeignService;
import root.client.service.JobFunctionFeignService;
import root.client.service.ProvinceFeignService;
import root.entity.*;
import root.enums.CompanySizeEnum;
import root.enums.GenderEnum;
import root.repository.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class DemoData implements ApplicationRunner {

    private final RoleRepo roleRepo;
    private final AccountRepo accountRepo;

    private final LanguageRepo languageRepo;
    private final SkillRepo skillRepo;

    private final JobFunctionParentRepo jobFunctionParentRepo;
    private final JobFunctionRepo jobFunctionRepo;
    private final IndustryRepo industryRepo;

    private final LocationCityRepo locationCityRepo;
    private final LocationDistrictRepo locationDistrictRepo;

    private final UserRepo userRepo;
    private final UserContactRepo userContactRepo;
    private final CompanyRepo companyRepo;

    private final IndustryFeignService industryFeignService;
    private final JobFunctionFeignService jobFunctionFeignService;
    private final ProvinceFeignService provinceFeignService;

    // Insert demo data
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn("DemoData!");

        Role role0 = Role.builder().name("ADMIN").build(); // id:1
        if (!roleRepo.existsByName(role0.getName())) {
            try {

                roleRepo.save(role0);
                roleRepo.save(Role.builder().name("USER").build()); // id:2
                roleRepo.save(Role.builder().name("COMPANY").build()); // id:3

                Account account0 = Account.builder()
                    .username("admin")
                    .password(new BCryptPasswordEncoder().encode("123123"))
                    .email("syhien85@hotmail.com")
                    .roles(List.of(role0))
                    .build();
                accountRepo.save(account0);

                languageRepo.save(Language.builder().name("Tiếng Việt").build());
                languageRepo.save(Language.builder().name("English").build());

                skillRepo.save(Skill.builder().name("Java").build());

                /*jobFunctionParentRepo.save(
                    JobFunctionParent.builder().name("CNTT - Viễn thông").build()
                );
                jobFunctionRepo.save(
                    JobFunction.builder()
                        .name("CNTT")
                        .jobFunctionParent(JobFunctionParent.builder().id(1).build())
                        .build()
                );*/

                jobFunctionFeignService.createAllJobFunctions();

//                industryRepo.save(Industry.builder().name("IT Support").build());

                industryFeignService.createAllIndustries();

                /*locationCityRepo.save(LocationCity.builder().name("Hà Nội").build());
                locationDistrictRepo.save(
                    LocationDistrict.builder()
                        .name("Quận Ba Đình")
                        .locationCity(LocationCity.builder().id(1).build())
                        .build()
                );*/

                provinceFeignService.createAllProvincesAndDistricts();

                User user1 = User.builder()
                    .account(
                        Account.builder()
                            .username("user1")
                            .password(new BCryptPasswordEncoder().encode("123123"))
                            .email("user1@syhien.com")
                            .roles(List.of(Role.builder().id(2).build()))
                            .build()
                    )
                    .build();
                userRepo.save(user1);

                UserContact userContact1 = UserContact.builder()
                    .email(user1.getAccount().getEmail())
                    .phone("0123123123")
                    .birthdate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1991"))
                    .gender(GenderEnum.MALE)
                    .maritalStatus(false)
                    .nationality(false)
                    .location(
                        Location.builder()
                            .address("111 Kim Mã")
                            .locationDistrict(
                                LocationDistrict.builder()
                                    .id(1)
                                    .locationCity(
                                        LocationCity.builder()
                                            .id(1)
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .user(User.builder().accountId(user1.getAccountId()).build())
                    .build();
                userContactRepo.save(userContact1);

                companyRepo.save(
                    Company.builder()
                        .account(
                            Account.builder()
                                .username("company1")
                                .password(new BCryptPasswordEncoder().encode("123123"))
                                .email("company1@syhien.com")
                                .roles(List.of(Role.builder().id(3).build()))
                                .build()
                        )
                        .companyInfo(
                            CompanyInfo.builder()
                                .companyName("companyName")
                                .companyLogoUrl("companyLogoUrl")
                                .companyProfile("companyProfile")
                                .companySize(CompanySizeEnum.MEDIUM)
                                .contactName("contactName")
                                .website("website")
                                .contactEmail("contactEmail")
                                .address("address")
                                .whyWorkWithUs("whyWorkWithUs")
                                .build()
                        )
                        .build()
                );

                log.warn("Create initial DemoData!!!");
            } catch (Exception e) {

            }
        }
    }
}

