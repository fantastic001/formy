package org.psw_isa.psw_isa_backend.dtos;

import java.util.HashMap;

public class ItemDTO {
    private Long id; 
    private String name; 
    private String description;
    private String type;
    private HashMap<String, String> data;

    public ItemDTO() {
    }
    
    public ItemDTO(Long id, String name, String description, String type, HashMap<String, String> data) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
