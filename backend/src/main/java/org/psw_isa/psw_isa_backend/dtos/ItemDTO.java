package org.psw_isa.psw_isa_backend.dtos;

import java.util.HashMap;

public class ItemDTO {
    private String name; 
    private String description;
    private String type;
    private HashMap<String, String> data;

    public ItemDTO() {
    }
    
    public ItemDTO(String name, String description, String type, HashMap<String, String> data) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ItemDTO [data=" + data + ", description=" + description + ", name=" + name + ", type=" + type + "]";
    }
}
