package br.com.transmetais.util;

import java.io.File;

import com.goshme.util.PropertiesFileLoader;

public class TransmetaisWeb
{
	
	
//	INIT_PROXY                   false
//
//	DOMAIN						 http://localhost:8080/autosbr/
//
//
//	PATH_STORE_IMAGE             C:/apps/autosbr/conf/imagesMd5/
//	PATH_ANUNCIOS	             C:/apps/autosbr/conf/anuncios/
//	PATH_ANUNCIOS_TMP            C:/apps/autosbr/conf/anunciosTmp/
//	PATH_CONFIG					 C:/apps/autosbr/conf/config/
//
//	PATH_CONFIG_FONTES			 C:/apps/autosbr/conf/fontes/
//	PATH_REGEX_CONFIG	
	
	
	public static final String PATH_STORE_IMAGE;
	public static final String PATH_STORE_IMAGE_THUMBS;
	public static final String PATH_STORE_IMAGE_ORIGINAL;
	public static final String PATH_STORE_IMAGE_240_320;
	public static final String PATH_STORE_IMAGE_160_120;
	
	public static final String PATH_ANUNCIOS_TMP;
	public static final String PATH_ANUNCIOS;
	
	public static final String PATH_CONFIG;
	
	
	public static final String DOMAIN;
	
	public static final String FILE_STOP_WORDS;
	public static final String FILE_CODE_FONTE;
	public static final String FILE_CODE_ANUNCIO;
	public static final String FILE_CODE_IMAGEM;
	public static final String PATH_CONFIG_FONTES;
	public static final String PATH_REGEX_CONFIG;
	public static final boolean INIT_PROXY;
	public static final String FOLDER_TMP_IMAGE;
	public static final String FOLDER_FILES_UPLOAD;
	public static final String PATH_INDEX;
	public static final String EMAIL_CONTATO;
	public static final String TEMPLATES_FOLDER;
	public static String[] PATH_STATIC_IDX = null;
	
	
	//URL_DOMAIN					autosbr.com.br
	public static String URL_DOMAIN;
	
	
	static{
		PropertiesFileLoader loader = new PropertiesFileLoader("transmetaisWeb");
		
		PATH_STORE_IMAGE = loader.getString("PATH_STORE_IMAGE");
		
		PATH_STORE_IMAGE_THUMBS   = PATH_STORE_IMAGE +"thumbs/";
		PATH_STORE_IMAGE_ORIGINAL = PATH_STORE_IMAGE+"original/";
		PATH_STORE_IMAGE_240_320  = PATH_STORE_IMAGE+"image240_320/";
		PATH_STORE_IMAGE_160_120     = PATH_STORE_IMAGE+"image160_120/";
		
		PATH_INDEX = loader.getString("PATH_INDEX");
		
		
		try {
			URL_DOMAIN = loader.getString("URL_DOMAIN");	
		} catch (Exception e) {
			URL_DOMAIN = "autosbr.com";
		}
		
		
		new File(PATH_STORE_IMAGE_THUMBS).mkdirs();
		new File(PATH_STORE_IMAGE_ORIGINAL).mkdirs();
		new File(PATH_STORE_IMAGE_240_320).mkdirs();
		new File(PATH_STORE_IMAGE_160_120).mkdirs();
		new File(PATH_INDEX).mkdirs();
		

		PATH_CONFIG   = loader.getString("PATH_CONFIG");
		PATH_ANUNCIOS     = loader.getString("PATH_ANUNCIOS");
		PATH_ANUNCIOS_TMP = loader.getString("PATH_ANUNCIOS_TMP");
		PATH_CONFIG_FONTES = loader.getString("PATH_CONFIG_FONTES");
		PATH_REGEX_CONFIG = loader.getString("PATH_REGEX_CONFIG");
		FOLDER_TMP_IMAGE = loader.getString("FOLDER_TMP_IMAGE").trim();
		FOLDER_FILES_UPLOAD = loader.getString("FOLDER_FILES_UPLOAD").trim();
		EMAIL_CONTATO = loader.getString("EMAIL_CONTATO");
		TEMPLATES_FOLDER = loader.getString("TEMPLATES_FOLDER");
		
		//System.out.println("FOLDER_TMP_IMAGE: " + FOLDER_TMP_IMAGE);
		
		DOMAIN = loader.getString("DOMAIN");
		
		INIT_PROXY = Boolean.valueOf(loader.getString("INIT_PROXY"));
		
		
		FILE_STOP_WORDS   = PATH_CONFIG + "STOP_WORDS.txt";
		FILE_CODE_FONTE   = PATH_CONFIG + "codeFonte.txt";
		FILE_CODE_ANUNCIO = PATH_CONFIG + "codeAnuncio.txt";
		FILE_CODE_IMAGEM  = PATH_CONFIG + "codeImage.txt";
		
		
		try {
			PATH_STATIC_IDX = loader.getString("PATH_STATIC_IDX").split(";");
		} catch (Exception e) {
			PATH_STATIC_IDX = new String[0];
		}
		
		
	}
	
	public static void main(String[] args)
	{
		System.out.println("ss");
		//System.out.println(PATH_INDEX);
		
	}
}

