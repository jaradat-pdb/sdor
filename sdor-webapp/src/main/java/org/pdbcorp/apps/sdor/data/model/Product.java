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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jaradat-pdb
 *
 */
@Document(collection = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
	@Transient
	public static final String SEQUENCE_NAME = "products_sequence";

	@Id
	private long id;
	private String name;
	private String desc;
	private BigDecimal price;
	private String imageUrl;
	private List<Category> categories = new ArrayList<>();

	public Product(String name, String desc, BigDecimal price, String imageUrl) {
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.imageUrl = imageUrl;
	}
}