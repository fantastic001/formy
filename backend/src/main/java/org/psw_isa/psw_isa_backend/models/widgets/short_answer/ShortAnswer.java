package org.psw_isa.psw_isa_backend.models.widgets.short_answer;

import javax.persistence.Entity;

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

    @Override
    public void decodeAnswer(String answer) {
        this.answer = answer;
        
    }

    @Override
    public String getTypeName() {
        return "short_answer";
    }
    
}
