package com.example.assignment3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {

    private static final File userFile = new File("user.dat");

    @Override
    public void start(Stage primaryStage) {
        try {
            if (!userFile.exists()) {
                userFile.createNewFile();
                writeDefaultUsers();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(15, 15, 15, 15));

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.TOP_CENTER);

        Image image = new Image("C:\\Users\\Microsoft\\Desktop\\images.jpg");  // Update the image path
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(200);
        vbox.getChildren().add(imageView);

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        usernameField.setPrefWidth(150);
        passwordField.setPrefWidth(150);

        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);


        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);
        gridPane.add(errorLabel, 1, 4);

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            if (validateUser(usernameField.getText(), passwordField.getText())) {
                Label welcomeLabel = new Label("Welcome, " + usernameField.getText() + "!");
                welcomeLabel.setFont(new Font("Arial", 18));
                HBox welcomeBox = new HBox(welcomeLabel);
                welcomeBox.setAlignment(Pos.CENTER);
                Scene welcomeScene = new Scene(welcomeBox, 300, 200);
                primaryStage.setScene(welcomeScene);
            } else {
                // Show error message below the login button
                errorLabel.setText("Invalid username or password!");
                errorLabel.setVisible(true);  // Make the error message visible
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> primaryStage.close());

        HBox buttonBox = new HBox(10, loginButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        gridPane.add(buttonBox, 1, 3);

        vbox.getChildren().add(gridPane);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean validateUser(String username, String password) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(userFile))) {
            String userDetails;
            while ((userDetails = bufferedReader.readLine()) != null) {
                String[] user = userDetails.split(" ");
                if (user[0].equals(username) && user[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void writeDefaultUsers() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(userFile))) {
            bufferedWriter.write("subhan 123");
            bufferedWriter.newLine();
            bufferedWriter.write("batman 123");
            bufferedWriter.newLine();
            bufferedWriter.write("superman 123");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
