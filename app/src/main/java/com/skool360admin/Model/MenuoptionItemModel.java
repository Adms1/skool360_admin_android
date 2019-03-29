package com.skool360admin.Model;

/**
 * Created by admsandroid on 11/16/2017.
 */

public class MenuoptionItemModel {
    private String Name;

    public MenuoptionItemModel(){}

    public MenuoptionItemModel(String Name){
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
