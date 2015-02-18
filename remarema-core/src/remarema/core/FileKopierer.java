package remarema.core;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileKopierer {

    private BufferedReader file;
	public File[] listFiles(String path, boolean printIt) {
        File user_dir = new File(path);

        File[] files = user_dir.listFiles();

        if (printIt) {
            for (int i = 0; i < files.length; i++) {
                System.out.println("File: "+ files[i].toString());
            }
        }
        return files;
    }
    
    

    public String readTextFile(String path) throws FileNotFoundException {
        String details, line;
        File f = new File(path);

        file = new BufferedReader(new FileReader(f));
        try {
            details = "";
            while ((line = file.readLine()) != null) {
                details += line + "\n";
            }
            return details;
        } catch (IOException e) {
            System.out.println("could not read line...");
            return null;
        }
    }

    public void checkFileForNewVersion(File[] asset_files, File[] svn_files) throws FileNotFoundException{

        /*
        + asset_files sind die dateien an denen ich gerade arbeite
        +svn_files sind die Spiegel Dateien
        */
        
        /*
        Hier wird jetzt jede Datei ausgelesen und mit der dazugehörigen 
        Spiegeldatei verglichen
        */
        
        //prüfe ob die Anzahl der dateien übereinstimmt
        
        for(int i = 0;i>asset_files.length;i++){
            
            // aktuelle dateien nehmen
            File asset_file = asset_files[i];
            File svn_file = svn_files[i];
            
            //speicherung deren Pfade
            String asset_file_path = asset_file.getPath();
            String svn_file_path = svn_file.getPath();
            
            // dateien aus lesen
            String asset_content = readTextFile(asset_file_path);
            String svn_content = readTextFile(svn_file_path);
            
            //die dateien vergleichen
            if(!asset_content.equals(svn_content)){
               
                // die Spiegel Datei beschreibbar machen
                svn_file.canWrite();
                // den Text der aktuellen datei in die 
                //Spiegel Datei senden
                
                writeToFile(svn_file,asset_content);
            }            
        }
    }
    private void writeToFile(File file, String newContent) {

        FileWriter w;
        try {
            w = new FileWriter(file);
            w.write(newContent);
            w.close();
        } catch (IOException ex) {
            System.err.println("Write Details Error"+ ex);
        }
    }
   
    
}


