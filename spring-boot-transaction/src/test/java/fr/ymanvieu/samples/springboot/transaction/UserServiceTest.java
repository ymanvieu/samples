/**
 * Copyright (C) 2016 Yoann Manvieu
 *
 * This software is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ymanvieu.samples.springboot.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import fr.ymanvieu.samples.springboot.transaction.UserRepository;
import fr.ymanvieu.samples.springboot.transaction.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@Sql("/insert_user.sql")
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	private long id = 0;
	private String name;

	/**
	 * Name is updated because the annotated method update() is called from a
	 * method outside of the object.
	 */
	@Test
	public void testUpdate() {
		name = "update";

		userService.update(id, name);

		assertThat(userRepo.findOne(id)).hasFieldOrPropertyWithValue("name", name);
	}

	/**
	 * Name is updated because the annotated method update() is called from a
	 * method that used the proxied version of itself, going through the Spring
	 * proxy wrapping.
	 */
	@Test
	public void testUpdateWithProxiedUpdateMethodCalled() {
		name = "updateWithProxiedUpdateMethodCalled";

		userService.updateWithProxiedUpdateMethodCalled(id, name);

		assertThat(userRepo.findOne(id)).hasFieldOrPropertyWithValue("name", name);
	}

	/**
	 * Name is not updated because the annotated method update() is called from
	 * a method of the very same object, bypassing the Spring proxy wrapping.
	 */
	@Test
	public void testUpdateWithInnerUpdateMethodCalled() {
		String oldValue = "user";
		name = "updateWithInnerUpdateMethodCalled";

		userService.updateWithInnerUpdateMethodCalled(id, name);

		assertThat(userRepo.findOne(id)).hasFieldOrPropertyWithValue("name", oldValue);
	}
}