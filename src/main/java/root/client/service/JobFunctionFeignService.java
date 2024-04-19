package root.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import root.client.dto.RJobFunction;
import root.entity.JobFunction;
import root.entity.JobFunctionParent;
import root.repository.JobFunctionParentRepo;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobFunctionFeignService {

    private final JobFunctionParentRepo jobFunctionParentRepo;
    private final WSJobFunctionsService wsJobFunctionsService;

    public List<JobFunctionParent> createAllJobFunctions() {

        RJobFunction rJobFunction = wsJobFunctionsService.getAll();

        List<RJobFunction.GroupJobFunction> groupJobFunctions = rJobFunction.data();
        List<RJobFunction.JobFunctionItem> jobFunctionItems = rJobFunction.included();

        List<JobFunctionParent> jobFunctionParents = new ArrayList<>();

        for (RJobFunction.GroupJobFunction groupJobFunction : groupJobFunctions) {
            JobFunctionParent jobFunctionParent = JobFunctionParent
                .builder()
                .id(groupJobFunction.id())
                .name(groupJobFunction.attributes().nameVi())
                .build();

            List<JobFunction> jobFunctions = new ArrayList<>();

            for (RJobFunction.JobFunctionItem
                jobFunctionItem : groupJobFunction.relationships().jobFunction().data()) {

                JobFunction jobFunction = new JobFunction();
                jobFunction.setId(jobFunctionItem.id());
                jobFunction.setName(
                    jobFunctionItems.stream().filter(
                        jobFunctionItem1 -> jobFunctionItem1.id().equals(jobFunctionItem.id())
                    ).findFirst().get().attributes().nameVi()
                );
                jobFunction.setJobFunctionParent(
                    JobFunctionParent.builder()
                        .id(jobFunctionParent.getId())
                        .name(jobFunctionParent.getName())
                        .build()
                );

                jobFunctions.add(jobFunction);
            }

            jobFunctionParent.setJobFunctions(jobFunctions);

            jobFunctionParents.add(jobFunctionParent);
        }

        jobFunctionParentRepo.saveAll(jobFunctionParents);

        return jobFunctionParents;
    }
}

