/**
 * Copyright (C) 2017 Yoann Manvieu
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
package com.example.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Test;

import com.example.domain.Account;
import com.example.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInfoMapperTest {

	@Test
	public void testToUserInfo() {
		User user = new User();
		user.setFirstname("Sherlock");
		user.setSurname("Holmes");
		user.setCountry(Locale.UK);

		Account ac = new Account();
		ac.setBalance(BigDecimal.valueOf(500000));
		ac.setOwner(user);

		UserInfo res = UserInfoMapper.MAPPER.toUserInfo(ac);

		log.info("{}", res);

		// avoid assert on toString(), use getters instead
		assertThat(res.toString()).isEqualTo("UserInfo(name=Sherlock, familyName=Holmes, baseCurrency=GBP, balance=£500,000.00)");
	}
}