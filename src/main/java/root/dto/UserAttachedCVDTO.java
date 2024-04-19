package root.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import root.entity.Resume;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAttachedCVDTO {
    private Long id;

    private boolean isAttached;
    private String attachedUrl;
    @JsonIgnore
    private MultipartFile file;

    @JsonBackReference
    private ResumeDTO resume;
}
