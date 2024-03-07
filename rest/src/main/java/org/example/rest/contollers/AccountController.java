package org.example.rest.contollers;

import java.util.List;
import org.example.rest.BankService;
import org.example.rest.resources.Account;
import org.example.rest.resources.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAll() {
        List<Account> getResource = BankService.getInstance().getAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(getResource);
    }
    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getById(@PathVariable("id") long id) {
        Account getResource = BankService.getInstance().getAccount(id);
        if (getResource != null)    return ResponseEntity.status(HttpStatus.OK).body(getResource);
        else                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/account")
    public ResponseEntity<Account> insert(@RequestBody Account account) {
        if (account == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Account createdResource = BankService.getInstance().addAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }

    @PutMapping("/account/{id}/add") // /account/{id}?user=...
    public ResponseEntity<Long> addUserToAccount(@PathVariable long id, @RequestParam(value = "user", defaultValue = "0") long userId) {
        Account changedResource = BankService.getInstance().getUserAccount(id);
        if (changedResource == null)    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
        User checkResource = BankService.getInstance().getUser(id);
        if (checkResource == null)      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userId);
        Long newUser = BankService.getInstance().addUserToAccount(id, userId);
        if (newUser == null)            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userId);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @PutMapping("/account/{id}/remove") // /account/{id}?user=...
    public ResponseEntity<Long> removeUserFromAccount(@PathVariable long id, @RequestParam(value = "user", defaultValue = "0") long userId) {
        Account changedResource = BankService.getInstance().getUserAccount(id);
        if (changedResource == null)    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
        User checkResource = BankService.getInstance().getUser(id);
        if (checkResource == null)      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userId);
        Long newUser = BankService.getInstance().removeUserFromAccount(id, userId);
        if (newUser == null)            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userId);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
}
