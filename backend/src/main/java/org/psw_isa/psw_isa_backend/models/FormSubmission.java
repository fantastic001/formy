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


}
