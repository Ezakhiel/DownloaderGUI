package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import com.github.axet.vget.AppManagedDownload;
import com.github.axet.vget.info.VideoInfo;
import com.github.axet.vget.vhs.YoutubeInfo;
import com.github.axet.vget.vhs.YoutubeInfo.StreamInfo;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;

public class GuiControls implements Initializable{

	private String[] dlDir;
	private String fullJPath;
	private PrintStream out;
	private Converter conv;
	public String filename;
	public StreamInfo ext;
	//private 
	File savePath; 
	File saveFile;
	
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
		conv = new Converter();
		out = System.out;
		fullJPath = new String();
		//fullJPath = "C:\\Users\\"+System.getProperty("user.name")+"\\download2";
		//dlDir[1] ="/Users/"+System.getProperty("user.name")+"/download2";
		try {
			 savePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			 saveFile = new File(savePath+"\\save.txt");
			if(!saveFile.exists()) {
				fullJPath = savePath.getAbsolutePath();
				dlDir[1]=fullJPath;
				FileOutputStream fos = new FileOutputStream(fullJPath+"\\save.txt"); 
				PrintStream out = new PrintStream(fos);
				out.println(fullJPath);
			} else{
				BufferedReader br = new BufferedReader(new FileReader(saveFile));
				try {
					fullJPath = br.readLine();
					dlDir[1]=fullJPath;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		dirText.setText(fullJPath);
		txt.setText("");
	}
	
	@FXML
	public void chooseDir(){
		Stage stage = new Stage();
		 final DirectoryChooser directoryChooser =
	                new DirectoryChooser();
	            final File selectedDirectory =
	                    directoryChooser.showDialog(stage);
	            if (selectedDirectory != null) {
	            	fullJPath = new String(selectedDirectory.getAbsolutePath());
	                dlDir[1]=fullJPath;
	                dirText.setText(fullJPath);
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(saveFile);
						PrintStream out = new PrintStream(fos);
						out.println(fullJPath);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

	            }
	}
	
	public void download(){
		if (urlTextField.getText() != ""){
			dlDir[0]=urlTextField.getText();
		}
		prBar.setProgress(0);
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//out = new PrintStream(baos);
		//Downloadvid dlv = new Downloadvid(dlDir, out, prBar,filename);
		//(new Thread(dlv)).start();
		AppManagedDownload amd = new AppManagedDownload(dlDir[0],new File(dlDir[1]), out, prBar,filename);
		AppManagedDownload.main(amd);
		/*Thread t1 = new Thread(new Runnable() {
		     public void run() {
		    	 while(prBar.getProgress() != 1){}
		    	 
		     }
		});  
		t1.start();*/


        while(prBar.getProgress() != 1){}
		if(audioCheck.isSelected()){
			VideoInfo i1 = amd.info;
	        YoutubeInfo i = (YoutubeInfo) i1;
			
			filename=amd.info.getTitle();

            String target= fullJPath+"\\"+filename+"."+i.getVideoQuality().c;
            System.out.println(target);
            prBar.setProgress(0);
			conv.convert(target);
			
		}
		//ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
		//System.out.println(in.toString());
		
	}

}
