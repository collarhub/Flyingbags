package net.flyingbags.flyingapps.model;

import java.io.Serializable;

public class Admin implements Serializable {
    private String name;

    public Admin(){

    }

    public Admin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
