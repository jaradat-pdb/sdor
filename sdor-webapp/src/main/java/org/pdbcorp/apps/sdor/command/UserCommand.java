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
package org.pdbcorp.apps.sdor.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author jaradat-pdb
 *
 */
@Data
public class UserCommand {
	@NotEmpty
	@Size(min = 1, max = 100, message = "First name must be between 1 to 100 characters long")
	private String firstName;

	@NotEmpty
	@Size(min = 1, max = 100, message = "Last name must be between 1 to 100 characters long")
	private String lastName;

	@NotEmpty
	@Email(message = "Email should be in a valid format")
	private String email;

	@NotEmpty
	@Size(min = 8, max = 50, message = "Password must be between 8 to 50 characters long")
	private String password;
}
