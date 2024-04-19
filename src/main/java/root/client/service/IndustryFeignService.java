package root.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import root.client.dto.RIndustry;
import root.entity.Industry;
import root.repository.IndustryRepo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndustryFeignService {

    private final IndustryRepo industryRepo;
    private final WSIndustriesService wsIndustriesService;

    public List<RIndustry.IndustryData.IndustryDataRelationships.CompanyIndustryItem>
    createAllIndustries() {

        List<Industry> industries = wsIndustriesService.getAll().data().relationships().data()
            .stream()
            .map(companyIndustryItem ->
                Industry.builder().name(companyIndustryItem.attributes().nameVi()).build()
            ).toList();

        industryRepo.saveAll(industries);

        return wsIndustriesService.getAll().data().relationships().data();
    }
}

