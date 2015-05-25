package application;

import java.io.File;
import java.io.IOException;

public class Converter {

	public Converter() {
		// TODO Auto-generated constructor stub
	}
	
	private void exec(String cmd,String target) {
	    try {
	        Process p = Runtime.getRuntime().exec(cmd);
	        p.waitFor();
	        delete(target);
	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	
	public void convert(String target){
		String command="ffmpeg.exe -i \""+target+"\" \""+target.substring(0, target.length() - 3)+"mp3\"";
		exec(command,target);
		
		
	}
	
	public void delete(String target){
		try{
			 
    		File file = new File(target);
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
 
    	}catch(Exception e){
 
    		e.printStackTrace();
 
    	}
	}

}
