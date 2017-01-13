/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreboard3;

import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author cody
 */
public class ScoreboardWindowController implements Initializable {
    
@FXML
private Label t1ScoreProp;
@FXML
private Label t2ScoreProp;
@FXML
private Label t1NameProp;
@FXML
private Label t2NameProp;
@FXML
private Label timerNumbersProp;
@FXML
private AnchorPane background;
@FXML
private JFXSpinner spinner;

private final StringProperty t1Score = new SimpleStringProperty();
private final StringProperty t2Score = new SimpleStringProperty();
private final StringProperty t1Name = new SimpleStringProperty();
private final StringProperty t2Name = new SimpleStringProperty();
private final StringProperty timerWords = new SimpleStringProperty();
private final StringProperty timerNumbers = new SimpleStringProperty();
    /**
     * Initializes the controller class.
     */
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        t1ScoreProp.textProperty().bind(t1Score);
        t2ScoreProp.textProperty().bind(t2Score);
        t1NameProp.textProperty().bind(t1Name);
        t2NameProp.textProperty().bind(t2Name);
        timerNumbersProp.textProperty().bind(timerNumbers);
        
        
        
        
    }    
    
    public void setScores(int t1, int t2){
        t1Score.setValue(Integer.toString(t1));
        t2Score.setValue(Integer.toString(t2));
    }
    
    public void setTeamNames(String t1, String t2){
        t1Name.setValue(t1);
        t2Name.setValue(t2);
    }
    
    public void setShowtimer(boolean show){
            timerNumbersProp.setVisible(show);
            spinner.setVisible(show);
    }
    public void hideTimer(){
        spinner.setVisible(false);
    }
    public void setTimer(int time){
        timerNumbers.setValue(Integer.toString(time));
    }

    
    public void setColor(Color backGround) {
        String webColor = String.format( "#%02X%02X%02X",
            (int)( backGround.getRed() * 255 ),
            (int)( backGround.getGreen() * 255 ),
            (int)( backGround.getBlue() * 255 ) );
        background.setStyle("-fx-background-color:" + webColor);
        
    }
    public void setFontColor(Color font){
        t1ScoreProp.setTextFill(font);
        t2ScoreProp.setTextFill(font);
        t1NameProp.setTextFill(font);
        t2NameProp.setTextFill(font);
        timerNumbersProp.setTextFill(font);
    }
    
   
}
