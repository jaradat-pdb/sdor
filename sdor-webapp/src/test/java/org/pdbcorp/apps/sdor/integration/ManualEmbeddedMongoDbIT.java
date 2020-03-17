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
import com.mongodb.client.MongoClients;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

class ManualEmbeddedMongoDbIT {
	private MongodExecutable mongodExecutable;
	private MongoTemplate mongoTemplate;

	@BeforeEach
	void setup() throws Exception {
		String ip = "localhost";
		int port = 38864;

		IMongodConfig mongodConfig = new MongodConfigBuilder()
			.version(Version.Main.PRODUCTION)
			.net(new Net(ip, port, Network.localhostIsIPv6()))
			.build();

		MongodStarter mongodStarter = MongodStarter.getDefaultInstance();
		mongodExecutable = mongodStarter.prepare(mongodConfig);
		mongodExecutable.start();
		mongoTemplate = new MongoTemplate(MongoClients.create(), "test");
	}

	@DisplayName("Given object When save object using MongoDB template Then object can be found")
	@Test
	void test() throws Exception {
		//given
		DBObject objectToSave = BasicDBObjectBuilder.start().add("key", "value").get();

		//when
		mongoTemplate.save(objectToSave, "collection");

		//then
		assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key").containsOnly("value");
	}

	@AfterEach
	void clean() {
		mongodExecutable.stop();
	}
}