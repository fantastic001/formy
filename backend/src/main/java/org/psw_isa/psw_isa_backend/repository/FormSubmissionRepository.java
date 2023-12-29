package org.psw_isa.psw_isa_backend.repository;


import org.psw_isa.psw_isa_backend.models.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long> {

	public FormSubmission findOneById(Long id);

	public FormSubmission save(FormSubmission submission);
	
}
