/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreboard3;

import javafx.stage.Screen;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author cody
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField t2NameField;

    @FXML
    private Label t2NameLabel;

    @FXML
    private Button t2ScoreUp;

    @FXML
    private Label t2ScoreLabel;

    @FXML
    private Button t1ScoreUp;

    @FXML
    private Label t1NameLabel;

    @FXML
    private Label t1ScoreLabel;

    @FXML
    private TextField t1NameField;

    @FXML
    private TextField t2ScoreField;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField t1ScoreField;

    @FXML
    private Button t2ScoreDown;

    @FXML
    private Rectangle background;

    @FXML
    private Label timerLabel;

    @FXML
    private Label timerLabelNumbers;

    @FXML
    private TextField timerField;

    @FXML
    private Button timerStart;

    @FXML
    private Button fullscreen;
    
    @FXML
    private CheckBox timerSound;
    
    @FXML
    private CheckBox timerFlash;
    
    @FXML
    private AnchorPane anchor;
    
    
            
    int t1Score = 0;
    int t2Score = 0;
    String t1Name = "Team 1";
    String t2Name = "Team 2";

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void t1Up(ActionEvent event) {
        t1Score++;
        updateScreen();
    }

    @FXML
    private void t2Up(ActionEvent event) {
        t2Score++;
        updateScreen();
    }

    @FXML
    private void t1Down(ActionEvent event) {
        t1Score--;
        updateScreen();
    }

    @FXML
    private void t2Down(ActionEvent event) {
        t2Score--;
        updateScreen();
    }

    @FXML
    private void t1NameChange(ActionEvent event) {
        t1Name = t1NameField.getText() + ":";
        updateScreen();
    }

    @FXML
    private void t2NameChange(ActionEvent event) {
        t2Name = t2NameField.getText() + ":";
        updateScreen();
    }

    @FXML
    private void t1ScoreChange(ActionEvent event) {
        t1Score = Integer.parseInt(t1ScoreField.getText());
        updateScreen();
    }

    @FXML
    private void t2ScoreChange(ActionEvent event) {
        t2Score = Integer.parseInt(t2ScoreField.getText());
        updateScreen();
    }

    @FXML
    private void colorChange(ActionEvent event) {
        Color color;
        if(colorPicker.getValue().getBrightness()<.7)
            color = Color.WHITE;
        else
            color = Color.BLACK;
        
        t1ScoreLabel.setTextFill(color);
        t2ScoreLabel.setTextFill(color);
        t1NameLabel.setTextFill(color);
        t2NameLabel.setTextFill(color);
        timerLabelNumbers.setTextFill(color);
        
        
        background.setFill(colorPicker.getValue());
        scoreboard.setColor(colorPicker.getValue());
        scoreboard.setFontColor(color);

    }

    @FXML
    private void resetScore(ActionEvent event) {
        t1Score = 0;
        t2Score = 0;
        updateScreen();
    }
    int timer;
    boolean isTiming = false;

    private void timer() {
        if (timer > 0) {
            timer--;
            isTiming = true;
            timerLabelNumbers.setText(Integer.toString(timer));
            scoreboard.setTimer(timer);

        }
        if (timer == 0) {
            timerLabelNumbers.setText("0");
            scoreboard.setTimer(timer);
            isTiming = false;
            if (timerFlash.isSelected()){
            scoreboard.setColor(Color.RED);
            }
            timer--;
            //Play warming sound
            scoreboard.hideTimer();
            if (timerSound.isSelected()) {
                try {
                    // get the sound file as a resource out of my jar file;
                    // the sound file must be in the same directory as this class file.
                    // the input stream portion of this recipe comes from a javaworld.com article.
                    InputStream inputStream = getClass().getResourceAsStream("alarm.wav");
                    AudioStream audioStream = new AudioStream(inputStream);
                    AudioPlayer.player.start(audioStream);
                } catch (Exception e) {
                }
            }

        } else if (timer < 0) {
            scoreboard.setColor(colorPicker.getValue());
            scoreboard.setShowtimer(false);
            timerLabelNumbers.setText("");
            timerStart.setText("Start");
            timerStart.setStyle("-fx-background-color:GREEN");
        }
    }

    Timeline timeline;
    int totalTime;

    @FXML
    private void startTimer() {
        if (!isTiming) {
            timer = Integer.parseInt(timerField.getText());
            timerLabelNumbers.setText(Integer.toString(timer));
            totalTime = timer;
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    ae -> timer()));
            timeline.setCycleCount(Integer.parseInt(timerField.getText()) + 1);
            timerStart.setStyle("-fx-background-color:RED");
            scoreboard.setTimer(timer);
            timerStart.setText("Reset");
            scoreboard.setShowtimer(true);
            isTiming = true;
            timeline.play();
        } else if (isTiming) {
            timerStart.setStyle("-fx-background-color:GREEN");
            timeline.stop();
            timerStart.setText("Start");
            scoreboard.setShowtimer(false);
            timerLabelNumbers.setText("");
            isTiming = false;

        }

    }

    
    
    ScoreboardWindowController scoreboard;

    
    double[] oldScreen = new double[4];
    boolean isFull = false;
    @FXML
    private void fullsreen() {
        if (isFull) {
            stage.setWidth(oldScreen[0]);
            stage.setHeight(oldScreen[1]);
            stage.setX(oldScreen[2]);
            stage.setY(oldScreen[3]);
            isFull = false;
            stage.setFullScreen(false);

        } else {
            int maxScreen = Screen.getScreens().size() - 1;
            System.out.println(Screen.getScreens().get(maxScreen).getVisualBounds().getMinX());
            System.out.println(Screen.getScreens().get(maxScreen).getVisualBounds().getMinY());
            oldScreen[0] = stage.getWidth();
            oldScreen[1] = stage.getHeight();
            oldScreen[2] = stage.getX();
            oldScreen[3] = stage.getY();
            stage.setX(Screen.getScreens().get(maxScreen).getVisualBounds().getMinX());
            stage.setY(Screen.getScreens().get(maxScreen).getVisualBounds().getMinY());
            stage.setWidth(Screen.getScreens().get(maxScreen).getBounds().getWidth());
            stage.setHeight(Screen.getScreens().get(maxScreen).getBounds().getHeight());
            isFull = true;
        }
    }
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ScoreboardWindow.fxml"));
            //Load scoreboard
            Parent root = (Parent) loader.load();
            stage = new Stage();
            scoreboard = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Scores");
            stage.show();
            t1Name = "Team 1:";
            t2Name = "Team 2:";
            t1NameField.setText("Team 1");
            t2NameField.setText("Team 2");
            updateScreen();
            colorPicker.setValue(Color.valueOf("#996699"));
            background.setFill(colorPicker.getValue());
            scoreboard.setColor(colorPicker.getValue());
            scoreboard.setShowtimer(false);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Failed to initlialize second window");
        }
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });
        
    }

    
    
    private void updateScreen() {
        t1NameLabel.setText(t1Name);
        t2NameLabel.setText(t2Name);
        t1ScoreLabel.setText(Integer.toString(t1Score));
        t2ScoreLabel.setText(Integer.toString(t2Score));
        t1ScoreField.setText(Integer.toString(t1Score));
        t2ScoreField.setText(Integer.toString(t2Score));
        scoreboard.setScores(t1Score, t2Score);
        scoreboard.setTeamNames(t1Name, t2Name);
    }

}
