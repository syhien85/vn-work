package root.client.dto;

import java.util.List;

public record RJobFunction(
    List<JobFunctionItem> included,
    List<GroupJobFunction> data
) {
    public record GroupJobFunction(
        Integer id,
        GroupJobFunctionAtt attributes,
        GroupJobFunctionRel relationships
    ) {
        public record GroupJobFunctionAtt(
            String nameVi,
            String nameEn
        ) {
        }
        public record GroupJobFunctionRel(
            JobFunctions jobFunction
        ) {
            public record JobFunctions(
                List<JobFunctionItem> data
            ) {}
        }
    }

    public record JobFunctionItem(
        Integer id,
        JobFunctionItemAtt attributes
    ) {
        public record JobFunctionItemAtt(
            String nameVi,
            String nameEn
        ) {
        }
    }
}
