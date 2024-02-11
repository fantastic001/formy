package org.psw_isa.psw_isa_backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
/**
 * This entity represents single answer to single form item in form submitted by user. 
 * Answers are always saved as encoded String value. 
 * 
 * In order to get exact type of item, WidgetDiscovery has to be used.
 * After that, decodeAnswer(String answer) method should be used which will 
 * populate fields in particular widget. 
 * 
 */
@Entity
public class FormItemAnswer {
    

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	

    @ManyToOne
    @NotNull
    FormItem item; 

    
    String answer; 

    @ManyToOne
    @NotNull
    FormSubmission submission;

    public FormItemAnswer(@NotNull FormItem item, String answer, @NotNull FormSubmission submission) {
        this.item = item;
        this.answer = answer;
        this.submission = submission;
    }

    public FormItem getItem() {
        return item;
    }

    public void setItem(FormItem item) {
        this.item = item;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public FormSubmission getSubmission() {
        return submission;
    }

    public void setSubmission(FormSubmission submission) {
        this.submission = submission;
    }

    
    
}
