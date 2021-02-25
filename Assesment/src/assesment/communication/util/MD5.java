package assesment.communication.util;

import java.security.MessageDigest;

public class MD5 {

	/**
	 * Encripta un String con el algoritmo MD5.
	 * 
	 * @return String
	 */
	private String hash(String clear){
		String res=null;
		try{	
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(clear.getBytes());
			int size = b.length;
			StringBuffer h = new StringBuffer(size);
			for (int i = 0; i < size; i++) {
				int u = b[i] & 255; // unsigned conversion
				if (u < 16) {
					h.append("0" + Integer.toHexString(u));
				} else {
					h.append(Integer.toHexString(u));
				}
			}
			res= h.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return res;
	}

	/**
	 * Encripta un String con el algoritmo MD5.
	 * 
	 * @return String
	 */
	public String encriptar(String palabra){
		String pe = "";
		try {
			pe = hash(palabra);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("<strong>Error: Al encriptar el password</strong>");
		}
		return pe;
	}
}