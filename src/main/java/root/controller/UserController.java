package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.UserDTO;
import root.dto.search.SearchJobDTO;
import root.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserDTO userDTO) {
        userService.create(userDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserDTO>builder()
            .status(200).msg("OK")
            .data(userService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<UserDTO>> search(@RequestBody SearchJobDTO searchDTO) {
        return ResponseDTO.<PageDTO<UserDTO>>builder().status(200).msg("OK")
            .data(userService.searchService(searchDTO))
            .build();
    }
}
