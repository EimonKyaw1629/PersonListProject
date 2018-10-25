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
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import com.example.personlist.dao.PersonInfoDAO;
import com.example.personlist.model.AddressInfo;
import com.example.personlist.model.MongoInfo;
import com.example.personlist.model.MyUploadForm;
import com.example.personlist.model.PersonInfo;
import com.example.personlist.repository.MongoInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class MainController {
	
	@Autowired
	private PersonInfoDAO dao;
	
	@Autowired
	private MongoInfoRepository repository;
	
	@RequestMapping(value = "/main")
	String main() {
		return "main";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form(Model model) {
		 
		MyUploadForm myUploadForm = new MyUploadForm();
		model.addAttribute("myUploadForm", myUploadForm);
		 
		return "form";
	}
	
	

	@RequestMapping(value = {  "/","/personList" }, method = RequestMethod.GET)//
	public String showPersonInfo(Model m) throws JsonProcessingException {
		List<Map<String, Object>> list = dao.getPersonInfoList();
		
		m.addAttribute("personInfo", list);
	
		return "personList";
	}
	
	@RequestMapping(value = "/delete/pid={pid}")
	public String deletePersonInfo(@PathVariable int pid, Model m) {
		repository.deleteById(pid);
		dao.deleteInfo(pid);
		
		return "redirect:/personList";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertPersonInfo(PersonInfo personinfo, @RequestParam String addText,
	         @RequestParam(value = "files",required = false)MultipartFile[] files, @ModelAttribute MongoInfo mongoInfo, BindingResult bindingResult, 
	         HttpServletRequest request, Model model) {

		System.out.println(mongoInfo);
		
		List<String> addrlist = null;
		addrlist = Arrays.asList(addText.split(","));
		int pid=dao.insertInfo(personinfo, addrlist);
		
		mongoInfo.setId(pid);
		System.out.println(mongoInfo);
		
		repository.save(mongoInfo);
		
		return this.doUpload(request, model, files,pid);
	}
	
	private static final String filePath = "C:\\99_TMPFiles\\images\\";
	
	private String doUpload(HttpServletRequest request, Model model, //
			MultipartFile[] myUploadForm,int pid) {
		
		
	      // Root Directory.
	      String uploadRootPath = request.getServletContext().getRealPath("upload");
	      
	      System.out.println("uploadRootPath=" + uploadRootPath);
	 
	      File uploadRootDir = new File(filePath);
	      // Create directory if it not exists.
	      if (!uploadRootDir.exists()) {
	         uploadRootDir.mkdirs();
	      }
	      MultipartFile[] fileDatas = myUploadForm;
	      //
	      
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
	               
	               dao.insertUpload(uploadRootPath, name, serverFile,pid);
	               
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
	
	@RequestMapping(value = "/edit/pid={pid}",produces = MediaType.IMAGE_JPEG_VALUE)
    public String editPersonInfo(@PathVariable int pid, Model m,HttpServletResponse response) throws IOException {

        PersonInfo info = dao.findPersonInfo(pid);
        List<AddressInfo> ainfo = dao.findAddressInfo(pid);
        List<MyUploadForm> upfile= dao.findFileList(pid);
        Optional<MongoInfo> mongoInfo = repository.findById(pid);
        
        if(info!=null)
        {
        if (ainfo != null) {

            info.alist = new ArrayList<AddressInfo>();
            for (AddressInfo addressInfo : ainfo) {
                AddressInfo adr = new AddressInfo();
                adr.setAddressID(addressInfo.getAddressID());
                adr.setAddress(addressInfo.getAddress());
                info.alist.add(adr);
            }
            

        }
        if(!upfile.isEmpty())
        {
            info.fileString=new ArrayList<MyUploadForm>();
            for(MyUploadForm form :upfile)
            {
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
            mon.setAge(mongoInfo.get().getAge());
            mon.setGender(mongoInfo.get().getGender());
            info.mongoList.add(mon);   
        }
        
        PersonInfo newinfo = new PersonInfo(info.getPersonID(), info.getFullName(), info.getFirstName(),
                info.getLastName(), info.getClassName(), info.getGrade(), info.alist, info.fileString, info.mongoList);
        
        m.addAttribute("person", newinfo);//myUploadForm
        m.addAttribute("upload", upfile);
        //m.addAttribute("imageUrlList", imageUrlList);

        return "editPerson";
    }else {

            m.addAttribute("person", info);
        }
        
        return "editPerson";
    }

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String geteditPersonInfo(@RequestParam(value = "pid") String pid, Model m,
			@RequestParam(value = "fu") String fullname, @RequestParam(value = "fs") String firstname,
			@RequestParam(value = "ls") String lastname, @RequestParam(value = "cs") String classname,
			@RequestParam(value = "g") String grade,@RequestParam(value = "aid",required = false)String[] aid,@RequestParam(value = "a",required = false)String[] ar,
			@RequestParam(value = "files") MultipartFile[] uploadingFiles ,HttpServletRequest request,@RequestParam(value = "image",required = false)int[] img,
			@RequestParam(value = "uploadRootPath",required = false)String[] uploadRootPath ,@RequestParam(value = "serverFile",required = false)String[] serverFile,
			@RequestParam(value = "name",required = false)String[] name,@RequestParam(value = "age") int age, @RequestParam(value = "gender") String gender) {
			

		PersonInfo personinfo = new PersonInfo(Integer.valueOf(pid), fullname, firstname, lastname, classname, grade);
		List<MyUploadForm>uplist= new ArrayList<MyUploadForm>();
		List<AddressInfo> alist = new ArrayList<AddressInfo>();
		MongoInfo mongoInfo = new MongoInfo();
		
		if(ar != null)
		{
			for(int j =0;j<ar.length;j++)
			{
				AddressInfo ainfo= new AddressInfo();
				ainfo.setPersonID(Integer.valueOf(pid));
				if(aid!=null)
				{
				int f = aid.length;
				if(f>j && f>0)
				{
					ainfo.setAddressID(Integer.parseInt(aid[j]));
				}
				}
				
				ainfo.Address = ar[j];
				alist.add(ainfo);
			}
		}
		
		if(img!=null)
		{
			for(int k=0;k<img.length;k++)
			{
				MyUploadForm frm = new MyUploadForm();
				frm.setFileID(img[k]);
				frm.setUploadRootPath(uploadRootPath[k]);
				frm.setServerFile(serverFile[k]);
				if(name!=null)
				{
					frm.setName(name[k]);
				}
				
				uplist.add(frm);
				//personinfo.fileString.add(frm);
			}
			
		}
		dao.editPersonInfo(personinfo, alist,uplist);
		mongoInfo.setId(Integer.valueOf(pid));
		mongoInfo.setAge(age);
		mongoInfo.setGender(gender);
		repository.save(mongoInfo);
		
		return this.doUpload(request, m, uploadingFiles,personinfo.getPersonID());
	}
	
	
	@RequestMapping(value = "/searchInfo", method = RequestMethod.POST)
	public String searchPersonInfo( @RequestParam(value = "fullname") String firstname,@RequestParam(value = "classname") String classname,Model m) {//@ModelAttribute("person") PersonInfo info, BindingResult result, Model m) {
		System.out.println("latest" + firstname);
		System.out.println("latest" + classname);
		List<PersonInfo> pinfo = dao.getSearchPersonInfo(firstname,classname);
		
		m.addAttribute("personInfo", pinfo);
		return "personList";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/photo/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] testphoto(@PathVariable String name) throws IOException {

		File imgfile = new File("C:\\99_TMPFiles\\images\\" + name);

	    return Files.readAllBytes(imgfile.toPath());
	}

}
