package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import youtubemanager.Downloadvid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class GuiControls implements Initializable{

	private String[] dlDir;
	private int counter;
	private PrintStream out;
	//private 
	
	@FXML
	public Text txt;
	
	@FXML
	private TextField urlTextField;
	
	@FXML
	private CheckBox audioCheck;
	
	@FXML
	public ProgressBar prBar;
	
	@FXML
	private Button downloadButton;
	
	@FXML
	private Button dirButton;
	
	@FXML
	private Text dirText;
	
	public GuiControls() {
		// TODO Auto-generated constructor stub
		dlDir = new String[2];
		out = System.out;
		dlDir[1] ="/Users/"+System.getProperty("user.name")+"/Downloads";
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		dirText.setText(dlDir[1]);
		
	}
	
	@FXML
	public void chooseDir(){
		Stage stage = new Stage();
		 final DirectoryChooser directoryChooser =
	                new DirectoryChooser();
	            final File selectedDirectory =
	                    directoryChooser.showDialog(stage);
	            if (selectedDirectory != null) {
	                //selectedDirectory.getAbsolutePath();
	                dlDir[1]=selectedDirectory.getAbsolutePath().substring(2);
	                dirText.setText(dlDir[1]);
	            }
	}
	
	public void download(){
		if (urlTextField.getText() != ""){
			dlDir[0]=urlTextField.getText();
		}
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//out = new PrintStream(baos);
		(new Thread(new Downloadvid(dlDir, out, prBar,txt))).start();
		//ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
		//System.out.println(in.toString());
		
	}

}
