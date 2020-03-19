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
package org.pdbcorp.apps.sdor.boot;

import java.util.Arrays;
import java.util.HashSet;

import org.pdbcorp.apps.sdor.data.model.Role;
import org.pdbcorp.apps.sdor.data.model.User;
import org.pdbcorp.apps.sdor.data.repository.RoleRepository;
import org.pdbcorp.apps.sdor.service.SdorWebAppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author jaradat-pdb
 *
 */
@Component
public class SdorWebAppBootstrap implements CommandLineRunner {
	private Role roleAdmin = new Role("ADMIN");
	private Role roleRoot = new Role("ROOT");
	private Role roleUser = new Role("USER");
	private RoleRepository roleRepository;
	private SdorWebAppUserDetailsService sdorWebAppUserDetailsService;

	@Autowired
	public SdorWebAppBootstrap(
			RoleRepository roleRepository, SdorWebAppUserDetailsService sdorWebAppUserDetailsService) {
		this.roleRepository = roleRepository;
		this.sdorWebAppUserDetailsService = sdorWebAppUserDetailsService;
	}

	@Override
	public void run(String... args) throws Exception {
		if(roleRepository.findByName(roleAdmin.getName()) == null) {
			roleAdmin = roleRepository.save(roleAdmin);
		}
		if(roleRepository.findByName(roleRoot.getName()) == null) {
			roleRoot = roleRepository.save(roleRoot);
		}
		if(roleRepository.findByName(roleUser.getName()) == null) {
			roleUser = roleRepository.save(roleUser);
		}
		
		if(sdorWebAppUserDetailsService.findUserByEmail("admin@pdbcorp.org") == null) {
			sdorWebAppUserDetailsService.saveUser(
					new User("root", "admin", "admin@pdbcorp.org", "superSecretPassword", true, new HashSet<>(Arrays.asList(roleAdmin, roleRoot, roleUser))));
		}
	}
}
