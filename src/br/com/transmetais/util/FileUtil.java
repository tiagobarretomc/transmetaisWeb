package br.com.transmetais.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.goshme.util.PropertiesFileLoader;

public class FileUtil {
	
	public static final String FOLDER_FILES_UPLOAD;
	
	
	
	static{
		PropertiesFileLoader loader = new PropertiesFileLoader("transmetaisWeb");
		
		FOLDER_FILES_UPLOAD = loader.getString("FOLDER_FILES_UPLOAD").trim();
		
	}
	
	public static void addFile(String filePath, String fileName, InputStream isFile){
		String path = FileUtil.FOLDER_FILES_UPLOAD;
		
		try {
			path = path + "/" + filePath + "/";
			 if (!(new File(path)).exists()){
				 (new File(path)).mkdirs();
			 }
			 FileUtil.writeUploadedResource(fileName, isFile, path);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeUploadedResource(String fileName, InputStream is, String targetPath) throws FileNotFoundException, IOException {
        OutputStream os = new FileOutputStream(targetPath + fileName );

    byte[] buffer = new byte[4096];
    long count = 0;
    int n = 0;
    while (-1 != (n = is.read(buffer))) {
        os.write(buffer, 0, n);
        count += n;
    }

        os.flush();
        os.close();
        is.close();
    }

}
