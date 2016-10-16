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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService thisProxiedService;

	@Transactional
	public UserEntity update(long id, String name) {

		UserEntity user = userRepo.findOne(id);

		user.setName(name);

		// no need to call userRepo.save() because user object is a managed
		// entity and a transaction is in progress from the start of this method
		// to the return meaning all changes to the entity here will be commit
		// at the return of this method.

		return user;
	}

	public UserEntity updateWithProxiedUpdateMethodCalled(long id, String name) {
		return thisProxiedService.update(id, name);
	}

	public UserEntity updateWithInnerUpdateMethodCalled(long id, String name) {
		return update(id, name);
	}
}
