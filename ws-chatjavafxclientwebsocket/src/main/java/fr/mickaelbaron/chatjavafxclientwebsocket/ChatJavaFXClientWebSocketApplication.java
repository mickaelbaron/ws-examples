package fr.mickaelbaron.chatjavafxclientwebsocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.tyrus.client.ClientManager;

import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatJavaFXClientWebSocketApplication extends Application {

	private Session currentSession;
	
	private TextArea messagesValue;
	
	@Override
	public void start(Stage stage) {
		BorderPane border = new BorderPane();
		border.setPadding(new Insets(10, 10, 10, 10));
		VBox leftVBox = new VBox();
		leftVBox.setPadding(new Insets(0, 10, 0, 0));
		leftVBox.setSpacing(10);
		border.setLeft(leftVBox);

		Label locationLabel = new Label("Location");
		leftVBox.getChildren().add(locationLabel);
		final TextField locationValue = new TextField();
		locationValue.setPrefWidth(350);
		locationValue.setText("ws://localhost:8026/chatwebsocket/chat/java/duke");
		leftVBox.getChildren().add(locationValue);

		HBox controlPanel = new HBox();
		leftVBox.getChildren().add(controlPanel);
		Button connectButton = new Button("Connect");
		connectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				connect(locationValue.getText());
			}
		});
		controlPanel.getChildren().add(connectButton);
		Button disconnectButton = new Button("Disconnect");
		disconnectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				disconnect();
			}
		});
		controlPanel.getChildren().add(disconnectButton);

		Label messageLabel = new Label("Message");
		leftVBox.getChildren().add(messageLabel);
		final TextField messageValue = new TextField();
		messageValue.setText("My First Message from JavaFX application");
		leftVBox.getChildren().add(messageValue);

		Button sendButton = new Button("Send");
		sendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				send(messageValue.getText());
			}
		});
		leftVBox.getChildren().add(sendButton);

		VBox centerBox = new VBox();
		Label messagesLabel = new Label("Messages");
		centerBox.getChildren().add(messagesLabel);
		border.setCenter(centerBox);
		messagesValue = new TextArea();
		centerBox.getChildren().add(messagesValue);
		Button clearButton = new Button("Clear");
		clearButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clearMessages();
			}
		});
		centerBox.getChildren().add(clearButton);
		centerBox.setSpacing(10);

		Scene scene = new Scene(new StackPane(border), 840, 270);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Chat JavaFX Client WebSocket");
	}

	public static void main(String[] args) {
		launch();
	}

	private void connect(String wsUri) {
		final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
		ClientManager client = ClientManager.createClient();
		
		try {
			currentSession = client.connectToServer(new Endpoint() {
				@Override
				public void onOpen(Session session, EndpointConfig config) {
					writeMessage("Connect to WSEndpoint.");
					
					session.addMessageHandler(new MessageHandler.Whole<String>() {
						@Override
						public void onMessage(String message) {
							writeMessage(message);
						}
					});
				}
			}, cec, new URI(wsUri));
		} catch (DeploymentException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void disconnect() {
		try {
			currentSession.close();
			writeMessage("Disconnect from WSEndpoint.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void send(String value) {
		try {
			currentSession.getBasicRemote().sendText(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void clearMessages() {
		messagesValue.clear();
	}
	
	private void writeMessage(String p) {
		messagesValue.appendText(p + "\n");
	}
}