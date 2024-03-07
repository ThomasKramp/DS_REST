package org.example.rest.contollers;

import org.example.rest.BankService;
import org.example.rest.resources.Account;
import org.example.rest.resources.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAll() {
        List<User> getResource = BankService.getInstance().getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(getResource);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") long id) {
        User getResource = BankService.getInstance().getUser(id);
        if (getResource != null)    return ResponseEntity.status(HttpStatus.OK).body(getResource);
        else                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @GetMapping("/user/{id}/balance") // /account?id=...
    public ResponseEntity<Integer> getBalanceById(@PathVariable("id") long id) {
        if (BankService.getInstance().getUser(id) != null) {
            Account getResource = BankService.getInstance().getUserAccount(id);
            if (getResource != null)    return ResponseEntity.status(HttpStatus.OK).body(getResource.getBalance());
            else                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((int) id);
    }

    @PostMapping("/user")
    public ResponseEntity<User> insert(@RequestBody User user) {
        if (user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        User createdResource = BankService.getInstance().addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }

    @PutMapping("/user/{id}") // /account/{id}?name=...
    public ResponseEntity<String> changeName(@PathVariable int id, @RequestParam(value = "name", defaultValue = "") String name) {
        if (!name.isEmpty())            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        User changedResource = BankService.getInstance().editUserName(id, name);
        if (changedResource == null)    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else                            return ResponseEntity.status(HttpStatus.OK).body(changedResource.getName());
    }
    @PutMapping("/user/{id}/add") // /account/{id}/add?value=...
    public ResponseEntity<Integer> addToBalance(@PathVariable int id, @RequestParam(value = "value", defaultValue = "0") int value) {
        if (value < 0)                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        User checkResource = BankService.getInstance().getUser(id);
        if (checkResource == null)      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Account changedResource = BankService.getInstance().getUserAccount(id);
        if (changedResource == null)    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        changedResource.setBalance(changedResource.getBalance() + value);
        return ResponseEntity.status(HttpStatus.OK).body(changedResource.getBalance());
    }
    @PutMapping("/user/{id}/remove") // /account/{id}/remove?value=...
    public ResponseEntity<Integer> removeFromBalance(@PathVariable int id, @RequestParam(value = "value", defaultValue = "0") int value) {
        if (value < 0)                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        User checkResource = BankService.getInstance().getUser(id);
        if (checkResource == null)      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Account changedResource = BankService.getInstance().getUserAccount(id);
        if (changedResource == null)    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        changedResource.setBalance(changedResource.getBalance() - value);
        return ResponseEntity.status(HttpStatus.OK).body(changedResource.getBalance());
    }
}
