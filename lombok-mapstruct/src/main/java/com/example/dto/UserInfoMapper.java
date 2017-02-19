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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.example.domain.Account;

@Mapper
public interface UserInfoMapper {

	UserInfoMapper MAPPER = Mappers.getMapper(UserInfoMapper.class);

	// if you use java 1.8, use can use 'mapstruct-jdk8' dependency instead of 'mapstruct' to get rid of the @mappings (repeatable annotations)
	@Mappings({
	@Mapping(target = "name", source = "owner.firstname"),
	@Mapping(target = "familyName", source = "owner.surname"),
	@Mapping(target = "baseCurrency", expression = "java(java.util.Currency.getInstance(account.getOwner().getCountry()).getCurrencyCode())"),
	@Mapping(target = "balance", expression = "java(java.text.NumberFormat.getCurrencyInstance(account.getOwner().getCountry()).format(account.getBalance()))")
	})
	UserInfo toUserInfo(Account account);
}