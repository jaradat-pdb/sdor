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
package org.pdbcorp.apps.sdor.data.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jaradat-pdb
 *
 */
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private String id;

	private String firstName;

	private String lastName;

	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String email;

	private String password;

	private boolean enabled;

	@DBRef
	private Set<Role> roles;

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public User(String firstName, String lastName, String email, String password, boolean enabled, Set<Role> roles) {
		this(firstName, lastName, email, password);
		this.enabled = enabled;
		this.roles = roles;
	}
}
