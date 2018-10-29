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
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}


	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView createLoginNewUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the username provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("signup");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
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
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("email", "Welcome " + user.getEmail());
	    modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
	    
	    List<MongoInfo> info = mdao.SelectAllGender();
	    
	    List<Map<String, Object>> list = dao.getPersonInfoList(info);
		m.addAttribute("personInfo", list);
	    modelAndView.setViewName("personList");
	    return modelAndView;
	}

	@RequestMapping(value = "/createPerson", method = RequestMethod.GET)
	public ModelAndView createPerson(Model model, PersonInfo personInfo) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getEmail());
		
		MyUploadForm myUploadForm = new MyUploadForm();
		model.addAttribute("myUploadForm", myUploadForm);

		modelAndView.setViewName("createPerson");
		return modelAndView;
	}

}
