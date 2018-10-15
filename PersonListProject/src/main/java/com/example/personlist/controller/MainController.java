package com.example.personlist.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@RequestMapping(value = "/main")
	String main() {
		return "main";
	}

	@RequestMapping(value = "/form")
	String form() {
		return "form";
	}

	@RequestMapping(value = {  "/","/personList" }, method = RequestMethod.GET)//
	public String showPersonInfo(Model m) {
		List<PersonInfo> list = dao.getPersonInfo();
		List<AddressInfo> ainfo = dao.getAddressInfo();
		PersonInfo newinfo = null;
		if (ainfo != null) {
			List<PersonInfo> newPList= new ArrayList<PersonInfo>();
			for(PersonInfo pinfo :list)
			{
				pinfo.alist=new ArrayList<AddressInfo>();
				for (AddressInfo addressInfo : ainfo) {
					System.out.println(addressInfo.getAddressID());
					System.out.println(addressInfo.getAddress());
					if(addressInfo.PersonID ==pinfo.PersonID)
					{
						AddressInfo adr = new AddressInfo();
						adr.setAddressID(addressInfo.getAddressID());
						adr.setAddress(addressInfo.getAddress());
						pinfo.alist.add(adr);
						
					}
					
					
				}
				newinfo = new PersonInfo(pinfo.getPersonID(),
						pinfo.getFullName(),
						pinfo.getFirstName(),
						pinfo.getLastName(),
						pinfo.getClassName(),
						pinfo.getGrade(),
						pinfo.alist);
				newPList.add(newinfo);
			
			}
			
					
			m.addAttribute("personInfo", newPList);
			
			return "personList";
			
		}
		else
		{
		m.addAttribute("personInfo", list);
		}
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
				AddressInfo adr = new AddressInfo();
				adr.setAddressID(addressInfo.getAddressID());
				adr.setAddress(addressInfo.getAddress());
				info.alist.add(adr);
			}
			PersonInfo newinfo = new PersonInfo(info.getPersonID(), info.getFullName(), info.getFirstName(),
					info.getLastName(), info.getClassName(), info.getGrade(), info.alist);
			m.addAttribute("person", newinfo);

			return "editPerson";

		} else {

			m.addAttribute("person", info);
		}

		return "editPerson";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String geteditPersonInfo(@RequestParam(value = "pid") String pid, Model m,
			@RequestParam(value = "fu") String fullname, @RequestParam(value = "fs") String firstname,
			@RequestParam(value = "ls") String lastname, @RequestParam(value = "cs") String classname,
			@RequestParam(value = "g") String grade,@RequestParam(value = "aid")String[] aid,@RequestParam(value = "a")String[] ar) {
			
		System.out.println(ar[0]);
		PersonInfo personinfo = new PersonInfo(Integer.valueOf(pid), fullname, firstname, lastname, classname, grade);

		List<AddressInfo> alist = new ArrayList<AddressInfo>();
		System.out.println(aid.length);
			for(int j =0;j<ar.length;j++)
			{
				AddressInfo ainfo= new AddressInfo();
				ainfo.setPersonID(Integer.valueOf(pid));
				int f = aid.length;
				if(f>j)
				{
					ainfo.setAddressID(Integer.parseInt(aid[j]));
				}
				
				ainfo.Address = ar[j];
				alist.add(ainfo);
			}
			
		dao.editPersonInfo(personinfo, alist);
		
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
