package com.app.dictapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.dictapp.dto.DictionaryDto;
import com.app.dictapp.model.Dictionary;
import com.app.dictapp.service.DictionaryRepo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	DictionaryRepo drepo;
	
	@GetMapping("/adminhome")
	public String showAdminHome(HttpSession session, HttpServletResponse response) {
		try {
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				return "admin/adminhome";
			}
			else {
				return "redirect:/adminlogin";
			}
				
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/adminlogin";
	}
	
	@GetMapping("/managewordmeaning")
	public String showManageWordMeaning(HttpSession session, HttpServletResponse response, Model model) {
		try {
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				DictionaryDto dto=new DictionaryDto();
				List<Dictionary> list=drepo.findAll();
				model.addAttribute("dto", dto);
				model.addAttribute("list", list);
				return "admin/managewordmeaning";
			}
			else {
				return "redirect:/adminlogin";
			}
				
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	
	@PostMapping("/managewordmeaning")
	public String addWordMeaning(HttpSession session, HttpServletResponse response, @ModelAttribute DictionaryDto dto, RedirectAttributes attrib) {
		try {
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				Dictionary dt=new Dictionary();
				dt.setWord(dto.getWord());
				dt.setMeaning(dto.getMeaning());
				drepo.save(dt);
				attrib.addFlashAttribute("msg", "Word and meaning are added");
				return "redirect:/admin/managewordmeaning";
			}
			else {
				return "redirect:/adminlogin";
			}
				
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	@GetMapping("/delete")
	public String deleteWordMeaning(HttpSession session, HttpServletResponse response, @RequestParam int id) {
		try {
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				Dictionary d=drepo.findById(id).get();
				drepo.delete(d);
				return "redirect:/admin/managewordmeaning";
			}
			else {
				return "redirect:/adminlogin";
			}
				
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/editwordmeaning")
	public String showEditWordMeaning(HttpSession session, HttpServletResponse response, @RequestParam int id, Model model) {
		try {
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				Dictionary d=drepo.findById(id).get();
				DictionaryDto dto=new DictionaryDto();
				dto.setWord(d.getWord());
				dto.setMeaning(d.getMeaning());
				model.addAttribute("dto", dto);
				model.addAttribute("d", d);
				return "admin/editwordmeaning";
			}
			else {
				return "redirect:/adminlogin";
			}
				
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	@PostMapping("/editwordmeaning")
	public String editWordMeaning(HttpSession session, HttpServletResponse response, @RequestParam int id, @ModelAttribute DictionaryDto dto) {
		try {
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			if (session.getAttribute("adminid") != null) {
				Dictionary d=drepo.findById(id).get();
				d.setWord(dto.getWord());
				d.setMeaning(dto.getMeaning());
				drepo.save(d);
				return "redirect:/admin/managewordmeaning";
			}
			else {
				return "redirect:/adminlogin";
			}
				
		} catch (Exception ex) {
			return "redirect:/adminlogin";
		}
	}
}
