package org.example.rest;

import org.example.rest.resources.Account;
import org.example.rest.resources.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankService {
    // https://refactoring.guru/design-patterns/singleton

    private static BankService instance;
    public static BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
            // Default users
            instance.addUser(new User(0, "Tom"));
            instance.addUser(new User(1, "Jeffrey"));
            instance.addUser(new User(2, "Kevin"));
            // Default accounts
            instance.addAccount(new Account(0, List.of(0L, 1L), 13));
            instance.addAccount(new Account(1, List.of(2L), 37));
        }
        return instance;
    }

    // -------------------------- Data --------------------------
    List<User> users = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();

    // -------------------------- User --------------------------
    public List<User> getUsers() { return users; }
    public User getUser(long id) {
        Optional<User> item = users.stream().filter(user -> user.getId() == id).findFirst();
        return item.orElse(null);
    }
    public Account getUserAccount(long userId) {
        Optional<Account> item = accounts.stream().filter(account -> account.getUsers().contains(userId)).findFirst();
        return item.orElse(null);
    }

    public User addUser(User user) { this.users.add(user); return user; }

    public User editUserName(long id, String name) {
        Optional<User> item = users.stream().filter(user -> user.getId() == id).findFirst();
        if (item.isEmpty()) return null;
        else {
            User user = item.get();
            user.setName(name);
            return user;
        }
    }

    // -------------------------- Account --------------------------
    public List<Account> getAccounts() { return accounts; }
    public Account getAccount(long id) {
        Optional<Account> item = accounts.stream().filter(account -> account.getId() == id).findFirst();
        return item.orElse(null);
    }
    public Account addAccount(Account account) { this.accounts.add(account); return account; }
    public Long addUserToAccount(long id, long userId) {
        Optional<Account> item = accounts.stream().filter(account -> account.getId() == id).findFirst();
        if (item.isPresent()) {
            List<Long> users = item.get().getUsers();
            if (!users.contains(userId)) {
                users.add(userId);
                return userId;
            }
        }
        return null;
    }
    public Long removeUserFromAccount(long id, long userId) {
        Optional<Account> item = accounts.stream().filter(account -> account.getId() == id).findFirst();
        if (item.isPresent()) {
            List<Long> users = item.get().getUsers();
            if (users.contains(userId)) {
                users.remove(userId);
                return userId;
            }
        }
        return null;
    }
}
