package org.psw_isa.psw_isa_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.psw_isa.psw_isa_backend.models.Form;
import org.psw_isa.psw_isa_backend.models.FormItem;
import org.psw_isa.psw_isa_backend.models.FormItemAnswer;
import org.psw_isa.psw_isa_backend.models.FormSubmission;
import org.psw_isa.psw_isa_backend.models.User;
import org.psw_isa.psw_isa_backend.repository.FormItemAnswerRepository;
import org.psw_isa.psw_isa_backend.repository.FormItemRepository;
import org.psw_isa.psw_isa_backend.repository.FormRepository;
import org.psw_isa.psw_isa_backend.repository.FormSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.psw_isa.psw_isa_backend.FormyConfiguration;
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

	@Autowired
	FormItemAnswerRepository formItemAnswerRepository;

	@Autowired
	FormSubmissionRepository formSubmissionRepository;

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
			if (form.getSubmissionExpiryTime() != null) {
				myform.setSubmissionExpiryTime(form.getSubmissionExpiryTime());
			}
			if (form.getName() != null) {
				myform.setName(form.getName());
			}
			if (form.getDescription() != null) {
				myform.setDescription(form.getDescription());
			}
			formRepository.save(myform);
			return myform;
		}
		
	}

	public String getCsv(Long id) {
		Form myformResult = formRepository.findOneByid(id);
		if (myformResult == null)  {
			return ""; 
		} else {
			Form myform = myformResult;
			// get all submissions for given form 
			List<FormSubmission> formSubmissions = formSubmissionRepository.findAll();
			// filter out submissions for given form
			formSubmissions = formSubmissions.stream().filter(submission -> submission.getForm().getId() == id).collect(Collectors.toList());
			String csv = "";
			for (FormSubmission submission : formSubmissions) {
				// get all answers for given submission 
				List<FormItemAnswer> formItemAnswers = formItemAnswerRepository.findAll();
				// filter out answers for given submission 
				formItemAnswers = formItemAnswers.stream().filter(answer -> answer.getSubmission().getId() == submission.getId()).collect(Collectors.toList());
				for (FormItemAnswer answer : formItemAnswers) {
					csv += answer.getAnswer() + ",";
				}
				// delete last comma 
				csv = csv.substring(0, csv.length() - 1);
				csv += "\n";
			}
			return csv;
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

				itemDTOs.add(new ItemDTO(item.getId(), item.getName(), item.getDescription(), widget.getTypeName(), widget.getData(), item.getOrder()));
			}
			return itemDTOs;
		}
    }


    public Long submit(Long id, HashMap<Long, String> data) {
        Form myformResult = formRepository.findOneByid(id);
		if (myformResult == null)  {
			return null;
		} else {
			Logger.getInstance().debug("FormService.submit");
			// Create form submission 
			FormSubmission formSubmission = new FormSubmission(myformResult, meService.greeting());
			formSubmissionRepository.save(formSubmission);
			for (Long key : data.keySet()) {
				Logger.getInstance().debug("Data key: " + key + " value: " + data.get(key));
				// find approprirate form item with given id 
				FormItem formItem = formItemRepository.findOneById(key);
				if (formItem == null) {
					continue;
				}
				// Create form item answer 
				FormItemAnswer formItemAnswer = new FormItemAnswer(formItem, data.get(key), formSubmission);
				formItemAnswerRepository.save(formItemAnswer);
			}
			return formSubmission.getId();
		}
    }


	public void exchangeFormItems(Long id1, Long id2) {
		FormItem item1 = formItemRepository.findOneById(id1);
		FormItem item2 = formItemRepository.findOneById(id2);
		if (item1 == null || item2 == null) {
			return;
		}
		int order = item1.getOrder();
		item1.setOrder(item2.getOrder());
		item2.setOrder(order);
		formItemRepository.save(item1);
		formItemRepository.save(item2);
	}

	public void deleteFormItem(Long id) {
		FormItem item = formItemRepository.findOneById(id);
		if (item == null) {
			return;
		}
		// find widget from widget discovery 
		Widget widget = widgetDiscovery.findWidgetFromFormItem(item);
		if (widget == null) {
			return;
		}
		widget.delete(FormyConfiguration.contextProvider(), item);
		formItemRepository.delete(item);
	}

	public void moveUpFormItem(Long id) {
		FormItem item = formItemRepository.findOneById(id);
		if (item == null) {
			return;
		}
		int order = item.getOrder();
		List<FormItem> items = formItemRepository.findAll();
		// find item with maximal order such that it is less than order of given item 
		FormItem item2 = null;
		for (FormItem item1 : items) {
			if (item1.getOrder() < order) {
				if (item2 == null || item1.getOrder() > item2.getOrder()) {
					item2 = item1;
				}
			}
		}
		if (item2 == null) {
			return;
		}
		this.exchangeFormItems(item.getId(), item2.getId());
	}

	public void moveDownFormItem(Long id) {
		FormItem item = formItemRepository.findOneById(id);
		if (item == null) {
			return;
		}
		int order = item.getOrder();
		List<FormItem> items = formItemRepository.findAll();
		// find item with minimal order such that it is greater than order of given item 
		FormItem item2 = null;
		for (FormItem item1 : items) {
			if (item1.getOrder() > order) {
				if (item2 == null || item1.getOrder() < item2.getOrder()) {
					item2 = item1;
				}
			}
		}
		if (item2 == null) {
			return;
		}
		this.exchangeFormItems(item.getId(), item2.getId());
	}


	
}
