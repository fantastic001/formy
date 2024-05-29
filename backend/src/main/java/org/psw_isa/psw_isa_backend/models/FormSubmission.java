package org.psw_isa.psw_isa_backend.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class FormSubmission {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
    
    @ManyToOne
    @NotNull
    Form form; 

    @ManyToOne
    User user; 

    @OneToMany
    Collection<FormItemAnswer> answers;

    public FormSubmission() {
    }

    public FormSubmission(Form form, User user) {
        this.form = form;
        this.user = user;
    }

    public String getCsv() {
        String csv = "";
        for (FormItemAnswer answer : answers) {
            csv += answer.getAnswer() + ",";
        }
        csv += user.getEmail() + "\n";
        return csv;
    }

    // setters and getters
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<FormItemAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<FormItemAnswer> answers) {
        this.answers = answers;
    }

    

}
