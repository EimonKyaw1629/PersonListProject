package com.example.personlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String searchPersonInfo(Model m,PersonInfo info)
	{
		PersonInfo pinfo = dao.findPersonInfo(info.getPersonID());
		m.addAttribute("personInfo",pinfo);
		return "redirect:/";
	}
}
