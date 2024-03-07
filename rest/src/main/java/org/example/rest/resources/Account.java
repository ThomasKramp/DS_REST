package org.example.rest.resources;

import java.util.List;

public class Account {
    private long id;
    private List<Long> users;
    private int balance;
    public Account(long id, List<Long> users, int balance) {
        setId(id);
        setUsers(users);
        setBalance(balance);
    }

    public long getId() {return this.id;}
    private void setId(long id) {this.id = id;}
    public List<Long> getUsers() {return this.users;}
    private void setUsers(List<Long> users) {this.users = users;}
    public int getBalance() {return this.balance;}
    public void setBalance(int balance) {this.balance = balance;}
}
