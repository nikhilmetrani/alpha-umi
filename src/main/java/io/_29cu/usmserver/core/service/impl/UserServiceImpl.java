/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.core.service.impl;

import io._29cu.usmserver.core.model.entities.Authority;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AuthorityName;
import io._29cu.usmserver.core.repositories.AuthorityRepository;
import io._29cu.usmserver.core.repositories.UserRepository;
import io._29cu.usmserver.core.service.SecurityContextService;
import io._29cu.usmserver.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private SecurityContextService securityContextService;

	@Override
	public User createUser(User user) {
		User newUser = user;
		try {
			// Grant User role by default.
			List<Authority> authorities = new ArrayList<>();
			authorities.add(authorityRepository.findByName(AuthorityName.ROLE_CONSUMER));
			authorities.add(authorityRepository.findByName(AuthorityName.ROLE_DEVELOPER));
			newUser.setAuthorities(authorities);

			return userRepository.save(newUser);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public User findAuthenticatedUser() {
		final User currentUser = securityContextService.getLoggedInUser();
		return userRepository.findOne(currentUser.getId());
	}

	@Override
	public User findUser(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public Boolean blockUser(User user) {
		user.setEnabled(false);
		user = userRepository.save(user);
		return user != null && !user.getEnabled();
	}

	@Override
	public Boolean updateUser(User user) {
		user = userRepository.save(user);
		return user != null;
	}
}
