package org.psw_isa.psw_isa_backend.models.widgets.short_answer;

import java.util.HashMap;

import javax.persistence.Entity;

import org.psw_isa.psw_isa_backend.ApplicationContextProvider;
import org.psw_isa.psw_isa_backend.FormyConfiguration;
import org.psw_isa.psw_isa_backend.Logger;
import org.psw_isa.psw_isa_backend.Widget;
import org.psw_isa.psw_isa_backend.models.FormItem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * This is type of field representing Shortanswer.
 */
@Entity 
public class ShortAnswer implements Widget {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    @ManyToOne
    @NotNull
    private FormItem item; 

    private String answer; 

    

    public ShortAnswer(@NotNull FormItem item, String answer) {
        this.item = item;
        this.answer = answer;
    }

    public ShortAnswer() {
    }

    @Override
    public void decodeAnswer(String answer) {
        this.answer = answer;
        
    }

    @Override
    public String getTypeName() {
        return "short_answer";
    }

    @Override
    public void save(ApplicationContextProvider provider) {
        ShortAnswerRepository repo = (ShortAnswerRepository) 
            provider
            .getApplicationContext()
            .getBean(ShortAnswerRepository.class);
        
        Logger.getInstance().debug("Saving short answer");
        repo.save(this);
        
    }

    @Override
    public Widget populateFromFormItem(ApplicationContextProvider provider,FormItem item) {
        ShortAnswerRepository repo = (ShortAnswerRepository) 
            provider
            .getApplicationContext()
            .getBean(ShortAnswerRepository.class);
        
        for (ShortAnswer entity : repo.findAll()) {
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
        this.answer = answer;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public Long getId() {
        return id;
    }
    
}
