package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.ResponseDTO;
import root.dto.RoleDTO;
import root.service.RoleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid RoleDTO roleDTO) {
        roleService.create(roleDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody RoleDTO roleDTO) {
        roleService.update(roleDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Integer id) {
        roleService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<RoleDTO> getById(@RequestParam("id") Integer id) {
        return ResponseDTO.<RoleDTO>builder()
            .status(200).msg("OK")
            .data(roleService.getById(id))
            .build();
    }
}
