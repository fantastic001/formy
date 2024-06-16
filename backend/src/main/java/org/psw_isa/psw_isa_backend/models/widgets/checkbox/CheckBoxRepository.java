package org.psw_isa.psw_isa_backend.models.widgets.checkbox;





import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckBoxRepository extends JpaRepository<CheckBox, Long> {

	public CheckBox findOneById(Long id);

	public CheckBox save(CheckBox answer);
	
}
