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
package fr.ymanvieu.samples.spring.websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html#websocket-server-runtime-configuration
 * </br>
 * https://github.com/salmar/spring-websocket-chat
 */
public class SpringWebsocketClient {

	static Logger log = LoggerFactory.getLogger(SpringWebsocketClient.class);
	static String URL = "wss://echo.websocket.org";

	public static void main(String[] args) throws Exception {

		TextWebSocketHandler eh = new TextWebSocketHandler() {
			@Override
			protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
				log.info("Receiving from server: {}", message.getPayload());
				System.exit(0);
			}
		};

		WebSocketClient wsc = new StandardWebSocketClient();

		WebSocketSession wss = wsc.doHandshake(eh, URL).get();

		log.info("Sending message 'hello' to server");

		wss.sendMessage(new TextMessage("hello"));

		// wait to avoid application exit 
		synchronized (Thread.currentThread()) {
			Thread.currentThread().wait();
		}
	}
}