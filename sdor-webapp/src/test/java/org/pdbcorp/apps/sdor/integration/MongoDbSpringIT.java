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
package org.pdbcorp.apps.sdor.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pdbcorp.apps.sdor.SdorWebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ContextConfiguration(classes = SdorWebApplication.class)
@DataMongoTest
@DirtiesContext
@ExtendWith(SpringExtension.class)
class MongoDbSpringIT {
	@DisplayName("given object to save when save object using MongoDB template then object is saved")
	@Test
	void validTest(@Autowired MongoTemplate mongoTemplate) {
		//given
		DBObject objectToSave = BasicDBObjectBuilder.start().add("key", "value").get();

		//when
		mongoTemplate.save(objectToSave, "collection");

		//then
		assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key").containsOnly("value");
	}
}