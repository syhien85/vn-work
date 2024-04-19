package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;
import root.dto.*;
import root.service.AccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @CacheEvict(cacheNames = "user-search", allEntries = true)
    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid AccountDTO accountDTO) {
        accountService.create(accountDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = "account-search", allEntries = true),
        @CacheEvict(cacheNames = "account", key = "#accountDTO.id") // khi update delete cache
    })
    @PutMapping
    public ResponseDTO<Void> update(@RequestBody AccountDTO accountDTO) {
        accountService.update(accountDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @CacheEvict(cacheNames = "account", key = "#id")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        accountService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @Cacheable(cacheNames = "account", key = "#id", unless = "#result == null")
    @GetMapping
    public ResponseDTO<AccountDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<AccountDTO>builder()
            .status(200).msg("OK")
            .data(accountService.getById(id))
            .build();
    }

    @Cacheable(cacheNames = "account-search")
    @PostMapping("/search")
    public ResponseDTO<PageDTO<AccountDTO>> search(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.<PageDTO<AccountDTO>>builder().status(200).msg("OK")
            .data(accountService.searchService(searchDTO))
            .build();
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = "account-search", allEntries = true),
        @CacheEvict(cacheNames = "account", key = "#accountDTO.id") // khi update delete cache
    })
    @PutMapping("/update-password")
    public ResponseDTO<Void> updatePassword(@RequestBody AccountDTO accountDTO) {
        accountService.updatePassword(accountDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = "account-search", allEntries = true),
        @CacheEvict(cacheNames = "account-forgot-password", key = "#usernameOrEmail") // khi update delete cache
    })
    @PostMapping("/forgot-password")
    public ResponseDTO<Void> forgotPassword(@RequestParam("usernameOrEmail") String usernameOrEmail) {
        accountService.forgotPassword(usernameOrEmail);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }
}
