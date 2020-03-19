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
package org.pdbcorp.apps.sdor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pdbcorp.apps.sdor.data.model.Role;
import org.pdbcorp.apps.sdor.data.model.User;
import org.pdbcorp.apps.sdor.data.repository.RoleRepository;
import org.pdbcorp.apps.sdor.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jaradat-pdb
 *
 */
@Slf4j
@Service
public class SdorWebAppUserDetailsService implements UserDetailsService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public SdorWebAppUserDetailsService(
			UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		if(user.getRoles() == null || user.getRoles().isEmpty()) {
			user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("USER"))));
		}
		user = userRepository.save(user);
		if(log.isTraceEnabled()) {
			log.trace("Successfully saved: {}", user);
		} else if(log.isDebugEnabled()) {
			log.debug("Successfully saved user: {} [{}]", user.getEmail(), user.getId());
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = findUserByEmail(email);
		if(user != null) {
			return buildUserForAuthentication(user, getUserAuthority(user.getRoles()));
		}
		else {
			throw new UsernameNotFoundException("Provided email does not exist in the system");
		}
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new ArrayList<>(authorities);
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
}
