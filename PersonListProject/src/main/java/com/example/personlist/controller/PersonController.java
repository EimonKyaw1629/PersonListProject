package com.example.personlist.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//github.com/EimonKyaw1629/PersonListProject.git
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.personlist.dao.MongoInfoDAO;
import com.example.personlist.dao.PersonInfoDAO;
import com.example.personlist.dao.UserService;
import com.example.personlist.model.AddressInfo;
import com.example.personlist.model.MongoInfo;
import com.example.personlist.model.MyUploadForm;
import com.example.personlist.model.PersonInfo;
import com.example.personlist.model.User;

@Controller
public class PersonController {

	@Autowired
	private MongoInfoDAO mdao;
	
	@Autowired
	private PersonInfoDAO dao;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/delete/pid={pid}")
	public String deletePersonInfo(@PathVariable int pid, Model m) {
		dao.deletePersonInfo(pid);
		mdao.mongoDelete(pid);
		
		return "redirect:/personList";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insertPersonInfo(@Valid PersonInfo personinfo,BindingResult bindingResult , @ModelAttribute MongoInfo mongoInfo, BindingResult mongoResult, @RequestParam String addText,
             @RequestParam(value = "files",required = false)MultipartFile[] files,
             HttpServletRequest request, Model model) {
		
		if (bindingResult.hasErrors()) {
            return "createPerson"; //this.CustomView(model, "createPerson");
        }

		List<String> addrlist = null;
		addrlist = Arrays.asList(addText.split(","));
		int pid = dao.insertPersonInfo(personinfo, addrlist);
		
		mongoInfo.setId(pid);
		mdao.mongoInsert(mongoInfo);
		doUpload(request, model, files, pid);
		
		return doUpload(request, model, files, pid); //this.CustomView(model, "personList");
    }

	private static final String filePath = "C:\\99_TMPFiles\\images\\";
	private String doUpload(HttpServletRequest request, Model model, 
			MultipartFile[] myUploadForm, int pid) {

		
		// Root Directory.
		String uploadRootPath = request.getServletContext().getRealPath("upload");

		System.out.println("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(filePath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		MultipartFile[] fileDatas = myUploadForm;

		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();

		for (MultipartFile fileData : fileDatas) {

			// Client File Name
			String name = fileData.getOriginalFilename();
			System.out.println("Client File Name = " + name);

			if (name != null && name.length() > 0) {
				try {
					// Create the file at server
					name = UUID.randomUUID() + name;
					File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);
					System.out.println("Write file: " + serverFile);

					dao.insertUploadFile(uploadRootPath, name, serverFile, pid);

				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
				}
			}
		}
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return "redirect:/personList";
	}

	/*
	 * 編集表示
	 */	
	@RequestMapping(value = "/edit/pid={pid}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ModelAndView editPageAppearPersonInfo(@PathVariable int pid, Model m, HttpServletResponse response)
			throws IOException {

		  ModelAndView modelAndView = this.getLoginInfo("personList");
		PersonInfo info = dao.findPersonInfo(pid);
		List<AddressInfo> ainfo = dao.findAddressInfoByPersonID(pid);
		List<MyUploadForm> upfile = dao.findFileListByPersonID(pid);
		
		MongoInfo mongoInfo = mdao.mongoFindbyPersonID(pid);

		if (info != null) {
			if (ainfo != null) {

				info.alist = new ArrayList<AddressInfo>();
				for (AddressInfo addressInfo : ainfo) {
					AddressInfo adr = new AddressInfo();
					adr.setAddressID(addressInfo.getAddressID());
					adr.setAddress(addressInfo.getAddress());
					info.alist.add(adr);
				}
			}
			if (!upfile.isEmpty()) {
				info.fileString = new ArrayList<MyUploadForm>();
				for (MyUploadForm form : upfile) {
					MyUploadForm upload = new MyUploadForm();
					upload.setFileID(form.getFileID());
					upload.setPersonID(form.getPersonID());
					upload.setName(form.getName());
					upload.setUploadRootPath(form.getUploadRootPath());
					upload.setServerFile(form.getServerFile());
					info.fileString.add(upload);
				}
			}

			if (mongoInfo != null) {

				info.mongoList = new ArrayList<MongoInfo>();
				MongoInfo mon = new MongoInfo();
				mon.setAge(mongoInfo.getAge());
				mon.setGender(mongoInfo.getGender());
				mon.setJob(mongoInfo.getJob());
				info.mongoList.add(mon);
			}

			PersonInfo newinfo = new PersonInfo(info.getPersonID(), info.getFullName(), info.getFirstName(),
					info.getLastName(), info.getClassName(), info.getGrade(), info.alist, info.fileString,
					info.mongoList);

			m.addAttribute("person", newinfo);
			m.addAttribute("upload", upfile);
			modelAndView.setViewName("editPerson");

		} else {

			m.addAttribute("person", info);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPersonInfo(@ModelAttribute("PersonInfo") PersonInfo info,@RequestParam(value = "age") int age,
			@RequestParam(value = "gender") String gender, @RequestParam(value = "job") String job,
			@RequestParam(value = "files") MultipartFile[] uploadingFiles, HttpServletRequest request,
			@RequestParam(value = "image", required = false) int[] img,@RequestParam(value = "aid", required = false) String[] aid,
			@RequestParam(value = "a", required = false) String[] ar,
			@RequestParam(value = "uploadRootPath", required = false) String[] uploadRootPath,@RequestParam(value = "name", required = false) String[] name,
			@RequestParam(value = "serverFile", required = false) String[] serverFile,Model m) {
		
		PersonInfo personinfo = new PersonInfo(info.getPersonID(),info.getFullName(),info.getFirstName(),info.getLastName(),info.getClassName(),info.getGrade());
		List<MyUploadForm> uplist = new ArrayList<MyUploadForm>();
		List<AddressInfo> alist = new ArrayList<AddressInfo>();
		MongoInfo mongoInfo = new MongoInfo(info.getPersonID(),gender,age,job);

		if (ar != null) {
			for (int j = 0; j < ar.length; j++) {
				AddressInfo ainfo = new AddressInfo();
				ainfo.setPersonID(info.getPersonID());
				if (aid != null) {
					int f = aid.length;
					if (f > j && f > 0) {
						ainfo.setAddressID(Integer.parseInt(aid[j]));
					}
				}

				ainfo.Address = ar[j];
				alist.add(ainfo);
			}
		}
		if (img != null) {
			for (int k = 0; k < img.length; k++) {
				MyUploadForm frm = new MyUploadForm();
				frm.setFileID(img[k]);
				frm.setUploadRootPath(uploadRootPath[k]);
				frm.setServerFile(serverFile[k]);
				if (name != null) {
					frm.setName(name[k]);
				}
				uplist.add(frm);
			}

		}
		dao.editPersonInfo(personinfo, alist, uplist);
		
		mdao.mongoUpdata(mongoInfo);
		return this.doUpload(request, m, uploadingFiles, personinfo.getPersonID());
	}

	@RequestMapping(value = "/searchInfo", method = RequestMethod.POST)
	public ModelAndView searchPersonInfo(@ModelAttribute("searchPerson")PersonInfo info,Model m) {
	
		
	    List<Map<String, Object>> pinfo = dao.getSearchPersonInfo(info);
		m.addAttribute("personInfo", pinfo);
		 ModelAndView modelAndView  = this.getLoginInfo("personList");
	
		return modelAndView;
	}

	@RequestMapping(value = "/searchGenderJob", method = RequestMethod.POST)
	public ModelAndView searchPersonGenderJobInfo(@ModelAttribute("mongo")MongoInfo minfo,Model m) {
		
		  
	   List<MongoInfo> info = mdao.mongoFindGenderJob(minfo);
	   
	   if(info != null)
	   {
		   List<Map<String, Object>> pinfo = dao.getSearchGenderInfo(info);
			m.addAttribute("personInfo", pinfo);
	   }
	   else
	   {
		   List<Map<String, Object>> pinfo = new ArrayList<Map<String, Object>>();
			m.addAttribute("personInfo", pinfo);
	   }
	   ModelAndView modelAndView = this.getLoginInfo("personList");
	
		 return modelAndView;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/photo/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] uploadPhoto(@PathVariable String name) throws IOException {

		File imgfile = new File("C:\\99_TMPFiles\\images\\" + name);

		return Files.readAllBytes(imgfile.toPath());
	}

	public ModelAndView getLoginInfo(String vname)
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    modelAndView.addObject("currentUser", user);
	    modelAndView.addObject("email", "Welcome " + user.getEmail());
	    modelAndView.setViewName(vname);
	    return modelAndView;
	}
}
