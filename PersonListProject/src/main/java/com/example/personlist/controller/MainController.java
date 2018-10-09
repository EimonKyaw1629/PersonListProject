package com.example.personlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	
	@RequestMapping(value={ "/", "/personList" },method = RequestMethod.GET)
	public String showPersonInfo(Model m)
	{
		List<PersonInfo> list = dao.getPersonInfo();
		m.addAttribute("personInfo",list);
		return "personList";
	}
	
	
	@GetMapping("/create")
	public String create() {
		
		return "form";
	}
	
	
	@RequestMapping(value="/delete/pid={pid}")
	public String deletePersonInfo(@PathVariable int pid, Model m)
	{
		dao.deleteInfo(pid);
		return "redirect:/personList";
	}
	
	@RequestMapping(value="/insert",method = RequestMethod.POST)
	public String insertPersonInfo(@RequestParam String FullName, String FirstName, String LastName, String ClassName, String Grade, String Address1, String Address2)
	{
		PersonInfo personinfo = new PersonInfo(FullName,FirstName,LastName,ClassName,Grade);
		AddressInfo addressinfo = new AddressInfo(Address1, Address2);
		
		dao.insertInfo(personinfo, addressinfo);
		return "redirect:/personList";
	}
	
	@RequestMapping(value="/edit/pid={pid}")
	public String editPersonInfo(@PathVariable int pid, Model m)
	{
		PersonInfo info = dao.findPersonInfo(pid);
		
		m.addAttribute("person",info);
		return "editPerson";
	}
	

}
