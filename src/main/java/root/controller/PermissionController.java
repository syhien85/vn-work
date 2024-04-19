package root.controller;

import root.dto.PermissionDTO;
import root.dto.ResponseDTO;
import root.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid PermissionDTO permissionDTO) {
        permissionService.create(permissionDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PostMapping("/create-all")
    public ResponseDTO<Void> createAll(@RequestBody @Valid List<PermissionDTO> permissionDTOs) {
        permissionService.createAll(permissionDTOs);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody PermissionDTO permissionDTO) {
        permissionService.update(permissionDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Integer id) {
        permissionService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<PermissionDTO> getById(@RequestParam("id") Integer id) {
        return ResponseDTO.<PermissionDTO>builder()
            .status(200).msg("OK")
            .data(permissionService.getById(id))
            .build();
    }

    @PostMapping("/list")
    public ResponseDTO<List<PermissionDTO>> list() {
        return ResponseDTO.<List<PermissionDTO>>builder().status(200).msg("OK")
            .data(permissionService.listService())
            .build();
    }
}
