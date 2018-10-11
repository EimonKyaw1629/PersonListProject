package com.example.personlist.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.personlist.dao.PersonInfoDAO;
import com.example.personlist.model.AddressInfo;
import com.example.personlist.model.PersonInfo;

@Controller
public class MainController {

	@Autowired
	private PersonInfoDAO dao;

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
	public String insertPersonInfo(@RequestParam String FullName, String FirstName, String LastName, String ClassName,
			String Grade, String Address1) {
		PersonInfo personinfo = new PersonInfo(FullName, FirstName, LastName, ClassName, Grade);
		//AddressInfo addressinfo = new AddressInfo(Address1);

		//dao.insertInfo(personinfo, addressinfo);
		return "redirect:/personList";
	}

	@RequestMapping(value = "/edit/pid={pid}")
	public String editPersonInfo(@PathVariable int pid, Model m) {
		
		PersonInfo info= dao.findPersonInfo(pid);
		AddressInfo ainfo = dao.findAddressInfo(pid);
		if (ainfo != null) {
			
			System.out.println(ainfo.PersonID);
			System.out.println(ainfo.Address);
			PersonInfo newinfo = new PersonInfo(info.getPersonID(),
					info.getFullName(),
					info.getFirstName(),
					info.getLastName(),
					info.getClassName(),
					info.getGrade(),
					ainfo.getAddressID(),
					ainfo.getAddress());
			m.addAttribute("person", newinfo);
			
			return "editPerson";
			
		}
		else
		{
			
			m.addAttribute("person", info);
		}
		
		return "editPerson";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String geteditPersonInfo(@RequestParam(value = "pid") String pid, Model m,
			@RequestParam(value = "fu") String fullname, @RequestParam(value = "fs") String firstname,
			@RequestParam(value = "ls") String lastname, @RequestParam(value = "cs") String classname,
			@RequestParam(value = "g") String grade, @RequestParam(value = "a1") String a1) {
		PersonInfo personinfo = new PersonInfo(Integer.valueOf(pid), fullname, firstname, lastname, classname, grade);

		AddressInfo addressinfo = new AddressInfo(Integer.valueOf(pid),a1);
		dao.editPersonInfo(personinfo, addressinfo);
		
		return "redirect:/personList";
	}
	@RequestMapping(value="/searchInfo",method = RequestMethod.POST)
	//public String searchPersonInfo(Model m,@RequestParam("fullname") String fullname,@RequestParam("classname") String classname)
	public String searchPersonInfo(@Valid @ModelAttribute("person")PersonInfo info, 
		      BindingResult result, Model m)
	{
		System.out.println("latest"+info.getFullName());
		System.out.println("latest"+info.getClassName());
		List<PersonInfo> pinfo = dao.getSearchPersonInfo(info.getFullName(),info.getClassName());//fullname, classname);
		m.addAttribute("personInfo",pinfo);
		return "personList";
	}
	

}
