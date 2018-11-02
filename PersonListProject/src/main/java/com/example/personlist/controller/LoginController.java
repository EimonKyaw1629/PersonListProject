package com.example.personlist.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.personlist.dao.MongoInfoDAO;
import com.example.personlist.dao.PersonInfoDAO;
import com.example.personlist.dao.UserService;
import com.example.personlist.model.MongoInfo;
import com.example.personlist.model.MyUploadForm;
import com.example.personlist.model.PersonInfo;
import com.example.personlist.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private MongoInfoDAO mdao;
	@Autowired
	private PersonInfoDAO dao;
	@Autowired
	private PersonController pcontrol;
	
		
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView createLoginNewUser(@Valid @ModelAttribute User user, BindingResult bindingResult) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the username provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("signUp");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("signUp");
		return modelAndView;
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@RequestMapping(value = "/personList", method = RequestMethod.GET)
	public ModelAndView getListUser(Model m) throws JsonProcessingException{
	   
	    
	    List<MongoInfo> info = mdao.SelectAllGender();
	    
	    List<Map<String, Object>> list = dao.getPersonInfoList(info);
		m.addAttribute("personInfo", list);
		 ModelAndView modelAndView = pcontrol.getLoginInfo("personList");
	  
	    return modelAndView;
	}

	@RequestMapping(value = "/createPerson", method = RequestMethod.GET)
	public ModelAndView createPerson(Model model, PersonInfo personInfo) {
		
		
		MyUploadForm myUploadForm = new MyUploadForm();
		model.addAttribute("myUploadForm", myUploadForm);
		 ModelAndView modelAndView = pcontrol.getLoginInfo("createPerson");
		
		return modelAndView;
	}

}
