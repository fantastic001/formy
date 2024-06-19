package org.psw_isa.psw_isa_backend.models.widgets.checkbox;

import java.util.HashMap;

import javax.persistence.Entity;

import org.psw_isa.psw_isa_backend.ApplicationContextProvider;
import org.psw_isa.psw_isa_backend.FormyConfiguration;
import org.psw_isa.psw_isa_backend.Logger;
import org.psw_isa.psw_isa_backend.Widget;
import org.psw_isa.psw_isa_backend.models.FormItem;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * This is type of field representing check box.
 */
@Entity 
public class CheckBox implements Widget {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @ManyToOne
    @NotNull
    private FormItem item; 

    private boolean answer;

    

    public CheckBox() {
    }

    public CheckBox(FormItem item) {
        this.item = item;
    }

    @Override
    public void decodeAnswer(String answer) {
        if (answer == null) {
            this.answer = false;
            return;
        }
        if (answer == "") {
            this.answer = false;
            return;
        }
        this.answer = answer == "true" ? true : false;
        
    }

    @Override
    public String getTypeName() {
        return "checkbox";
    }

    @Override
    public void save(ApplicationContextProvider provider) {
        CheckBoxRepository repo = (CheckBoxRepository) 
            provider
            .getApplicationContext()
            .getBean(CheckBoxRepository.class);
        
        Logger.getInstance().debug("Saving checkbox answer");
        repo.save(this);
        
    }

    @Override
    public Widget populateFromFormItem(ApplicationContextProvider provider,FormItem item) {
        CheckBoxRepository repo = (CheckBoxRepository) 
            provider
            .getApplicationContext()
            .getBean(CheckBoxRepository.class);
        
        for (CheckBox entity : repo.findAll()) {
            if (entity.item.getId() == item.getId()) {
                this.answer = entity.answer;
                this.id = entity.id;
                this.item = entity.item;
                return this;
            }
        }
        return null;
    }

    @Override
    public void populateFromData(ApplicationContextProvider provider, HashMap<String, String> data) {

    }

    @Override
    public void setItem(FormItem item) {
        this.item = item;
    }

    @Override
    public FormItem getItem() {
        return item;
    }

    @Override
    public void setAnswer(String answer) {
        this.decodeAnswer(answer);
    }

    @Override
    public String getAnswer() {
        return this.answer ? "true" : "false";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        return data;
    }

    @Override
    public void delete(ApplicationContextProvider provider, FormItem item) {
        CheckBoxRepository repo = (CheckBoxRepository) 
            provider
            .getApplicationContext()
            .getBean(CheckBoxRepository.class);
        
        // get all short answers 
        for (CheckBox entity : repo.findAll()) {
            if (entity.getItem().getId() == item.getId()) {
                repo.delete(entity);
            }
        }
    }
}
