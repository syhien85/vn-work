package root.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.SearchDTO;
import root.dto.SkillDTO;
import root.service.SkillService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/search")
    public ResponseDTO<PageDTO<SkillDTO>> search(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.<PageDTO<SkillDTO>>builder().status(200).msg("OK")
            .data(skillService.searchService(searchDTO))
            .build();
    }
}
