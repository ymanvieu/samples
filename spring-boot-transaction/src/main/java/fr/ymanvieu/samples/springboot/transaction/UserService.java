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
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService thisProxiedService;

	@Transactional
	public void update(long id, String name) {

		UserEntity user = userRepo.findById(id).orElseThrow();

		user.setName(name);

		// No need to call userRepo.save() because 'user' object is a managed
		// entity and a transaction is in progress from the start of this method
		// to the return, meaning all changes to the entity here will be commit at the return of this method.
		
		// It only applied if a transcation is in progress, otherwise nothing is persisted.
	}

	public void updateWithProxiedUpdateMethodCalled(long id, String name) {
		thisProxiedService.update(id, name);
	}

	public void updateWithInnerUpdateMethodCalled(long id, String name) {
		update(id, name);
	}
}
