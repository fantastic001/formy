package org.psw_isa.psw_isa_backend.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * FormItem represents generic form item in database. Every form item type has its own 
 * additional table and every entry in that table references since entity from FormItem table.
 * 
 * In order to get exact type, WidgetDiscovery has to be used. 
 * 
 */
@Entity
public class FormItem {
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @NotNull
	private Form form; 

    @NotNull
    private String name; 

    private String description;

    @OneToMany
    Collection<FormItemAnswer> answers;

    public Collection<FormItemAnswer> getAnswers() {
        return answers;
    }

    public FormItem() {
    }

    public FormItem(Form form, String name, String description) {
        this.form = form;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
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

    

}
