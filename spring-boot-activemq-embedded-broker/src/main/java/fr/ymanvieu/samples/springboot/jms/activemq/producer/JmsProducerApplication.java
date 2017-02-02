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
package fr.ymanvieu.samples.springboot.jms.activemq.producer;

import java.util.Date;

import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import fr.ymanvieu.samples.springboot.jms.activemq.shared.ClockMessage;

@SpringBootApplication
@EnableScheduling
public class JmsProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsProducerApplication.class, args);
	}

	@Autowired
	private JmsTemplate jms;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public BrokerService broker(@Value("${spring.activemq.broker-url}") String brokerUrl) throws Exception {
		BrokerService broker = new BrokerService();
		broker.addConnector(brokerUrl);
		broker.setPersistent(false);
		return broker;
	}

	@Scheduled(fixedDelay = 5000)
	public void clock() {
		ClockMessage o = new ClockMessage(new Date());
		jms.convertAndSend("current-time", o);
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}