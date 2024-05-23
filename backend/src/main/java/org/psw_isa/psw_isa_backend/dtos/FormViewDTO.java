package org.psw_isa.psw_isa_backend.dtos;

import java.util.ArrayList;

public class FormViewDTO {

    private String name; 
    private String description;
    private Long id;
    private ArrayList<ItemDTO> items;

    public FormViewDTO(String name, String description, Long id, ArrayList<ItemDTO> items) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.items = items;
    }

    public FormViewDTO() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<ItemDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDTO> items) {
        this.items = items;
    }

    
}
