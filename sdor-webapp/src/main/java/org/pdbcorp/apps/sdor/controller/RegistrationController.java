/*
 * Copyright 2020 PDB Corp.
 *
 * Proprietary Software built off of open-source software?
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pdbcorp.apps.sdor.controller;

import javax.validation.Valid;

import org.pdbcorp.apps.sdor.command.LoginCommand;
import org.pdbcorp.apps.sdor.command.UserCommand;
import org.pdbcorp.apps.sdor.data.model.User;
import org.pdbcorp.apps.sdor.service.SdorWebAppUserDetailsService;
import org.pdbcorp.apps.sdor.service.UserCommandConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jaradat-pdb
 *
 */
@Controller
public class RegistrationController {
	@Autowired
	private UserCommandConversionService userCommandConversionService;
	@Autowired
	private SdorWebAppUserDetailsService sdorWebApplicationUserDetailsService;

	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("userCommand", new UserCommand());
		return "register";
	}

	@PostMapping("/register")
	public ModelAndView registerNewUser(@Valid UserCommand userToRegister, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		User userExists = sdorWebApplicationUserDetailsService.findUserByEmail(userToRegister.getEmail());
		if(userExists != null) {
			bindingResult.rejectValue("email", "error.user", "The provided email is already registered in the system");
		}
		
		if(bindingResult.hasErrors()) {
			modelAndView.setViewName("register");
		}
		else {
			User userToSave = userCommandConversionService.convert(userToRegister);
			sdorWebApplicationUserDetailsService.saveUser(userToSave);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("loginCommand", new LoginCommand());
			modelAndView.setViewName("login");
		}
		
		return modelAndView;
	}
}
