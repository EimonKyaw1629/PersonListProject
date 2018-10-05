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
	
	@RequestMapping(value="/searchInfo",method = RequestMethod.POST) 
	public String searchPersonInfo(Model m,@RequestParam("fullname") String fullname,@RequestParam("classname") String classname)
	{
		List<PersonInfo> pinfo = dao.getSearchPersonInfo(fullname, classname);
		m.addAttribute("personInfo",pinfo);
		return "personList";
	}
	
	@GetMapping("/create")
	public String create() {
		
		return "form";
	}
	
	@RequestMapping(value="/delete/pid={pid}")
	public String deletePersonInfo(@PathVariable int pid, Model m)
	{
		System.out.println(pid);
		dao.deleteInfo(pid);
		return "redirect:/personList";
	}
	
	@RequestMapping(value="/insert",method = RequestMethod.POST)
	public String insertPersonInfo(@RequestParam String FullName, String FirstName, String LastName, String ClassName, String Grade)
	{
		PersonInfo info = new PersonInfo(FullName,FirstName,LastName,ClassName,Grade);
		System.out.println(info);
		
		dao.insertInfo(info);
		return "redirect:/personList";
	}
	
}
