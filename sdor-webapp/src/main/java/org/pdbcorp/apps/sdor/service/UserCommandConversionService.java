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

import org.pdbcorp.apps.sdor.command.UserCommand;
import org.pdbcorp.apps.sdor.data.model.User;
import org.springframework.stereotype.Service;

/**
 * @author jaradat-pdb
 *
 */
@Service
public class UserCommandConversionService {

	public User convert(UserCommand userCommand) {
		User user = new User();
		user.setEmail(userCommand.getEmail());
		user.setFirstName(userCommand.getFirstName());
		user.setLastName(userCommand.getLastName());
		user.setPassword(userCommand.getPassword());
		return user;
	}
}
