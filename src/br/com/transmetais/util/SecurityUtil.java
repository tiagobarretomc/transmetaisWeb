package br.com.transmetais.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
	
	/*
	 * Criptografa o termo recebido por parametro para o padr�o SH-2
	 */
	public static String encriptyWord(String word){
		String retorno = null;
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(word.getBytes("UTF-8"));
			
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			retorno = hexString.toString();
			
			return retorno;
			
		} catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return retorno;
	}
	
	public static String createDefaultPassword(){
		String[] carct ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h",
				"i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C",
				"D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X",
				"Y","Z"};

		String senha="";


		for (int x=0; x<10; x++){
			int j = (int) (Math.random()*carct.length);
			senha += carct[j];

		} 
		return senha;
	}

}
