package root.client.dto;

import java.util.List;

public record RIndustry(
    IndustryData data
) {
    public record IndustryData(
        IndustryDataRelationships relationships
    ) {
        public record IndustryDataRelationships(
            List<CompanyIndustryItem> data
        ) {
            public record CompanyIndustryItem(
                CompanyIndustryItemAtt attributes
            ) {
                public record CompanyIndustryItemAtt(
                    String nameVi,
                    String nameEn
                ) {
                }
            }
        }
    }
}
