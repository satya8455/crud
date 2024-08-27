package com.ncm.crud.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ncm.crud.dto.UserVo;
import com.ncm.crud.entity.Account;
import com.ncm.crud.password.AESExample;
import com.ncm.crud.serviceimpl.AccountServiceImpl;
@Controller
public class AccountController {


	private final AccountServiceImpl createService;
	
	
	public AccountController(AccountServiceImpl createService) {
		this.createService = createService;
	}


	@PostMapping("/create")
	public String stuRegister(@ModelAttribute UserVo user ) {
		
		try {
			System.out.println(user);
			Account createuser=new Account();
			AESExample aseexample=new AESExample();
			//aseexample.encrypt(user.setPassword(null));
			createuser=	createService.save1(user);
			     
			if(createuser!=null)
			{
				 return "redirect:/login";
			}
			else {
				return "redirect:/createaccount";
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return "redirect:/login";
		
		
		 
	}
	
	
	//for my login page
		@GetMapping("/login")
		public String login(Model model) {
			System.out.println(model);
			return "login";
		}
		
		@PostMapping("/logincheck")
	    public String userlogin(@ModelAttribute Account user, Model model) {
	        try {
	            System.out.println(user);

	            
	            boolean isAuthenticated = createService.authenticate(user);

	            if (isAuthenticated) {
	                return "dashboard"; 
	            } 
	            else {
	            	System.out.println("Gives error");
	                model.addAttribute("error", "Invalid credentials. Please try again.");
	                return "login"; 
	            }
	        } 
	        catch (Exception e) {
	            
	            e.printStackTrace();
	            model.addAttribute("error", "An error occurred. Please try again later.");
	            return "login";
	        }
		}
		@GetMapping("/index")
		public String index(Model model) {
			System.out.println(model);
			return "index";
		}
}

		
		
	
	
	
	
	

