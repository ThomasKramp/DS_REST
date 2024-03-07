package org.example.rest.resources;

public class User {
    private long id;
    private String name;

    public User(long id, String name) {
        setId(id);
        setName(name);
    }

    public long getId() {return this.id;}
    private void setId(long id) {this.id = id;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
}
