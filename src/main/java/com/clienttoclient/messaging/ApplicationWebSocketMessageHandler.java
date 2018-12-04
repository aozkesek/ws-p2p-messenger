package com.clienttoclient.messaging;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.System.out;

public class ApplicationWebSocketMessageHandler extends TextWebSocketHandler {

    private ConcurrentMap<String, WebSocketSession> wsClients
            = new ConcurrentHashMap();

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {

        super.afterConnectionEstablished(session);


        // save session into the collection
        // normally put remoteaddress as a key
        // but if there is more than one connection from the same ip,
        // then sessionid is usefull
        wsClients.put(session.getId(), session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status)
            throws Exception {

        super.afterConnectionClosed(session, status);

        // remove session from the collection
        wsClients.remove(session.getId());

    }

    @Override
    public void handleMessage(final WebSocketSession session,
                              WebSocketMessage message)
            throws Exception {

        super.handleMessage(session, message);

        final String payload = message.getPayload().toString();


        out.println("<" + payload + ">");

        wsClients
                .forEach((sId, wsS) -> {
                    if (session.getId().equals(sId))
                        return;

                    out.println(session.getId() + " << " + message.getPayload());

                    try {
                        String recvText = payload;
                        String sendText = recvText;
                        wsS.sendMessage(new TextMessage(sendText.getBytes()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });


    }
}
