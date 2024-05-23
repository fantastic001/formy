package org.psw_isa.psw_isa_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.psw_isa.psw_isa_backend.models.Form;
import org.psw_isa.psw_isa_backend.models.FormItem;
import org.psw_isa.psw_isa_backend.models.User;
import org.psw_isa.psw_isa_backend.repository.FormItemRepository;
import org.psw_isa.psw_isa_backend.repository.FormRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.psw_isa.psw_isa_backend.Logger;
import org.psw_isa.psw_isa_backend.Widget;
import org.psw_isa.psw_isa_backend.WidgetDiscovery;
import org.psw_isa.psw_isa_backend.dtos.FormDTO;
import org.psw_isa.psw_isa_backend.dtos.ItemDTO;

@Service
public class FormService {

	@Autowired
	FormRepository formRepository;

	@Autowired
	MeService meService; 

	@Autowired
	WidgetDiscovery widgetDiscovery;

	@Autowired
	FormItemRepository formItemRepository;

	public List<Form> findAll() {
		return formRepository.findAll();
	}


	public Form findOneByid(Long id) {
		return formRepository.findOneByid(id);
	}

	public Form save(FormDTO formDTO) {
		Logger.getInstance().debug("FormService.save");
		User myself = meService.greeting();
		Logger.getInstance().debug("FormService.save myself: " + myself.getEmail());
		return formRepository.save(new Form(
			LocalDateTime.now(), 
			formDTO.getSubmissionExpiryTime(),
			myself, 
			formDTO.getName(), 
			formDTO.getDescription()
		));
	}


	public Form update(Long id, FormDTO form) {
		Optional<Form> myformResult = formRepository.findById(id);
		if (! myformResult.isPresent())  {
			return null; 
		} else {
			Form myform = myformResult.get();
			return formRepository.save(new Form(
				myform.getCreateTime(), 
				form.getSubmissionExpiryTime(), 
				myform.getAuthor(), 
				form.getName(), 
				form.getDescription()
			));
		}
		
	}

	public String getCsv(Long id) {
		Form myformResult = formRepository.findOneByid(id);
		if (myformResult == null)  {
			return null; 
		} else {
			Form myform = myformResult;
			return myform.getCsv();
		}
	}

	public void delete(Long id) {
		formRepository.deleteById(id);
	}


	public FormItem addFormItem(Long id, String name, String description, String type, HashMap<String, String> data) {
		Form myformResult = formRepository.findOneByid(id);
		if (myformResult == null)  {
			return null;
		} else {
			Logger.getInstance().debug("FormService.addFormItem");
			FormItem formItem = widgetDiscovery.createItem(myformResult, name, description, type, data);
			return formItem;
		}
	}


    public List<ItemDTO> getFormItems(Long id) {
        Form myformResult = formRepository.findOneByid(id);
		if (myformResult == null)  {
			return null;
		} else {
			Logger.getInstance().debug("FormService.getFormItems");
			ArrayList<FormItem> formItems = new ArrayList<FormItem>();
			for (FormItem item : formItemRepository.findAll()) {
				if (item.getForm().getId() == id) {
					formItems.add(item);
				}
			}
			List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
			for (FormItem item : formItems) {
				Widget widget = widgetDiscovery.findWidgetFromFormItem(item);
				if (widget == null) {
					continue;
				}

				itemDTOs.add(new ItemDTO(item.getName(), item.getDescription(), widget.getTypeName(), widget.getData()));
			}
			return itemDTOs;
		}
    }

}
