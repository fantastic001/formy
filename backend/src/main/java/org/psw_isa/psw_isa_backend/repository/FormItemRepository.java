package org.psw_isa.psw_isa_backend.repository;





import org.psw_isa.psw_isa_backend.models.FormItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormItemRepository extends JpaRepository<FormItem, Long> {

	public FormItem findOneById(Long id);

	public FormItem save(FormItem item);
	
}
