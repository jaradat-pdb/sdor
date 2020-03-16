/* Copyright 2020 PDB Corp.
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ErrorController {

	@GetMapping("/error")
	public String getError() {
		log.error("Returning runtime error page to requestor, refer to logs for more info");
		return "error";
	}

	@GetMapping("/403")
	public String getAccessDeniedError() {
		log.error("Returning access denied error page to requestor, refer to logs for more info");
		return "403";
	}

	@GetMapping("/404")
	public String getApplicationError() {
		log.error("Returning application error page to requestor, refer to logs for more info");
		return "404";
	}

	@GetMapping("/500")
	public String getInternalServerError() {
		log.error("Returning internal server error page to requestor, refer to logs for more info");
		return "500";
	}
}