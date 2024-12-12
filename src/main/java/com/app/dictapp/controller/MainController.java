package com.app.dictapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.dictapp.dto.AdminLoginDto;
import com.app.dictapp.dto.DictionaryDto;
import com.app.dictapp.model.AdminLogin;
import com.app.dictapp.model.Dictionary;
import com.app.dictapp.service.AdminLoginRepo;
import com.app.dictapp.service.DictionaryRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired //directly make object means add all dependencies of objects without making it
	AdminLoginRepo alrepo; 
	
	@Autowired
	DictionaryRepo drepo;
	
	@GetMapping("/")
	public String showIndex(Model model) {
		DictionaryDto dto= new DictionaryDto();  //through this we input the word in home page
		model.addAttribute("dto", dto);  //bind the objects in model
		return "index";
	}
	
	@PostMapping("/")
	public String search(@ModelAttribute Dictionary dto, RedirectAttributes attrib) {
		Dictionary d= drepo.getByWord(dto.getWord());
		attrib.addFlashAttribute("d", d);
		return "redirect:/";
	}
	
	
	@GetMapping("/adminlogin")
	public String showaAdminLogin(Model model) {
		AdminLoginDto dto= new AdminLoginDto();
		model.addAttribute("dto", dto);
		return "adminlogin";
	}
	
	@PostMapping("/adminlogin")  //This method is call when the form is submit
	public String validate(@ModelAttribute AdminLoginDto dto, RedirectAttributes attrib, HttpSession session) {
		//to not show the value in url use addAttribute //to maintain session
		try {
			AdminLogin al = alrepo.findById(dto.getAdminid()).get();
				if(al!=null) {
					if(al.getPassword().equals(dto.getPassword()))
					{
						//attrib.addFlashAttribute("msg", "Valid User");
					
						session.setAttribute("adminid", al.getAdminid());
						return "redirect:/admin/adminhome";
					}
					else {
						attrib.addFlashAttribute("msg", "Invalid User");
					}
				}
			}
	
		catch(Exception ex)
		{
			attrib.addFlashAttribute("msg", "Admin does not exist");
		}
	
			 
		return "redirect:/adminlogin";
	}
}
