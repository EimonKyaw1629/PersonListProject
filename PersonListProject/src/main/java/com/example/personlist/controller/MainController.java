package com.example.personlist.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.example.personlist.dao.PersonInfoDAO;
import com.example.personlist.model.AddressInfo;
import com.example.personlist.model.PersonInfo;

@Controller
public class MainController {
	
	@Autowired
	private PersonInfoDAO dao;

	@RequestMapping(value = "/main")
	String main() {
		return "main";
	}
	
	@RequestMapping(value = "/form")
	String form() {
		return "form";
	}

	@RequestMapping(value = { "/", "/personList" }, method = RequestMethod.GET)
	public String showPersonInfo(Model m) {
		List<PersonInfo> list = dao.getPersonInfo();
		m.addAttribute("personInfo", list);

		return "personList";
	}

	@RequestMapping(value = "/delete/pid={pid}")
	public String deletePersonInfo(@PathVariable int pid, Model m) {
		dao.deleteInfo(pid);
		return "redirect:/personList";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertPersonInfo(PersonInfo personinfo, @RequestParam String addText) {
		List<String> addrlist = null;
        addrlist = Arrays.asList(addText.split(","));
		dao.insertInfo(personinfo, addrlist);
		return "redirect:/personList";
	}

	@RequestMapping(value = "/edit/pid={pid}")
	public String editPersonInfo(@PathVariable int pid, Model m) {

		PersonInfo info = dao.findPersonInfo(pid);
		List<AddressInfo> ainfo = dao.findAddressInfo(pid);
		if (ainfo != null) {

			info.alist = new ArrayList<AddressInfo>();
			for (AddressInfo addressInfo : ainfo) {
				System.out.println(addressInfo.getAddressID());
				System.out.println(addressInfo.getAddress());
				AddressInfo adr = new AddressInfo();
				adr.setAddressID(addressInfo.getAddressID());
				adr.setAddress(addressInfo.getAddress());
				info.alist.add(adr);
			}
			PersonInfo newinfo = new PersonInfo(info.getPersonID(), info.getFullName(), info.getFirstName(),
					info.getLastName(), info.getClassName(), info.getGrade(), info.alist);
			// \ainfo.getAddressID(),
			// ainfo.getAddress());
			m.addAttribute("person", newinfo);

			return "editPerson";

		} else {

			m.addAttribute("person", info);
		}

		return "editPerson";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String geteditPersonInfo(PersonInfo personinfo, Model m) {
		
		dao.editPersonInfo(personinfo);

		List<PersonInfo> list = dao.getPersonInfo();
		m.addAttribute("personInfo", list);
		return "redirect:/personList";
	}

	@RequestMapping(value = "/searchInfo", method = RequestMethod.POST)
	public String searchPersonInfo(@Valid @ModelAttribute("person") PersonInfo info, BindingResult result, Model m) {
		System.out.println("latest" + info.getFullName());
		System.out.println("latest" + info.getClassName());
		List<PersonInfo> pinfo = dao.getSearchPersonInfo(info.getFullName(), info.getClassName());// fullname,
																									// classname);
		m.addAttribute("personInfo", pinfo);
		return "personList";
	}

}
