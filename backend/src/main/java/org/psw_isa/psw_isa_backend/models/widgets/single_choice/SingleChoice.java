package org.psw_isa.psw_isa_backend.models.widgets.single_choice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Entity;
import org.psw_isa.psw_isa_backend.ApplicationContextProvider;
import org.psw_isa.psw_isa_backend.FormyConfiguration;
import org.psw_isa.psw_isa_backend.Logger;
import org.psw_isa.psw_isa_backend.Widget;
import org.psw_isa.psw_isa_backend.models.FormItem;
import org.psw_isa.psw_isa_backend.models.widgets.multiple_choice.MultipleChoiceRepository;
import org.psw_isa.psw_isa_backend.models.widgets.single_choice.SingleChoiceRepository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * This is type of field representing single choice.
 */
@Entity 
public class SingleChoice implements Widget {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @ManyToOne
    @NotNull
    private FormItem item; 

    private String selection;

    private ArrayList<String> options;

    public SingleChoice(FormItem item, ArrayList<String> options) {
        this.item = item;
        this.options = options;
    }

    public SingleChoice() {
    }

    @Override
    public void decodeAnswer(String answer) {
        this.selection = answer;
    }

    @Override
    public String getTypeName() {
        return "single_choice";
    }

    @Override
    public void save(ApplicationContextProvider provider) {
        SingleChoiceRepository repo = (SingleChoiceRepository) 
            provider
            .getApplicationContext()
            .getBean(SingleChoiceRepository.class);
        
        repo.save(this);
        
    }

    @Override
    public Widget populateFromFormItem(ApplicationContextProvider provider,FormItem item) {
        SingleChoiceRepository repo = (SingleChoiceRepository) 
            provider
            .getApplicationContext()
            .getBean(SingleChoiceRepository.class);
        
        for (SingleChoice entity : repo.findAll()) {
            if (entity.item.getId() == item.getId()) {
                this.selection = entity.selection;
                this.id = entity.id;
                this.item = entity.item;
                this.options = entity.options;
                return this;
            }
        }
        return null;
    }

    @Override
    public void populateFromData(ApplicationContextProvider provider, HashMap<String, String> data) {
        String[] options = data.get("options").split(",");
        ArrayList<String> optionsList = new ArrayList<>();
        for (String o : options) {
            optionsList.add(o);
        }
        this.options = optionsList;
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
        return this.selection;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        String options = "";
        for (String o : this.options) {
            options += o + ",";
        }
        options = options.substring(0, options.length() - 1);
        data.put("options", options);
        return data;
    }

    @Override
    public void delete(ApplicationContextProvider provider, FormItem item) {
        SingleChoiceRepository repo = (SingleChoiceRepository) 
            provider
            .getApplicationContext()
            .getBean(SingleChoiceRepository.class);
        
        // get all short answers 
        for (SingleChoice entity : repo.findAll()) {
            if (entity.getItem().getId() == item.getId()) {
                repo.delete(entity);
            }
        }
    }
    
}
