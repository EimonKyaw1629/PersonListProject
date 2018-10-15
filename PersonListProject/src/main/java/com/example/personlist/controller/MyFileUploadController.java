package com.example.personlist.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.example.personlist.model.MyUploadForm;

@Controller
public class MyFileUploadController {
 
   // GET: Show upload form page.
   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
   public String uploadOneFileHandler(Model model) {
 
      MyUploadForm myUploadForm = new MyUploadForm();
      model.addAttribute("myUploadForm", myUploadForm);
 
      return "uploadOneFile";
   }
 
   /*// POST: Do Upload
   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
   public String uploadOneFileHandlerPOST(HttpServletRequest request, //
         Model model, //
         @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
 
      return this.doUpload(request, model, myUploadForm);
 
   }
 
   private String doUpload(HttpServletRequest request, Model model, //
         MyUploadForm myUploadForm) {
 
      String description = myUploadForm.getDescription();
      System.out.println("Description: " + description);
 
      // Root Directory.
      String uploadRootPath = request.getServletContext().getRealPath("upload");
      System.out.println("uploadRootPath=" + uploadRootPath);
 
      File uploadRootDir = new File(uploadRootPath);
      // Create directory if it not exists.
      if (!uploadRootDir.exists()) {
         uploadRootDir.mkdirs();
      }
      MultipartFile[] fileDatas = myUploadForm.getFileDatas();
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
               File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
 
               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
               stream.write(fileData.getBytes());
               stream.close();
               //
               uploadedFiles.add(serverFile);
               System.out.println("Write file: " + serverFile);
            } catch (Exception e) {
               System.out.println("Error Write file: " + name);
               failedFiles.add(name);
            }
         }
      }
      model.addAttribute("description", description);
      model.addAttribute("uploadedFiles", uploadedFiles);
      model.addAttribute("failedFiles", failedFiles);
      return "uploadResult";
   }*/
 
}