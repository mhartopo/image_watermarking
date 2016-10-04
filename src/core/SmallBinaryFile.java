package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SmallBinaryFile {
  
	public byte[] readByte(String fileName) throws IOException {
		  Path path = Paths.get(fileName);
		  byte[] bytes = Files.readAllBytes(path);
		  return bytes;
	}
	public String read(String fileName) throws IOException {
	  Path path = Paths.get(fileName);
	  byte[] bytes = Files.readAllBytes(path);
	  String str = new String(bytes, "ISO-8859-1");
		  return str;
	  }
	  
	  public void writeByte(byte[] bytes, String fileName) throws IOException {
	    Path path = Paths.get(fileName);
	    Files.write(path, bytes); //creates, overwrites
	  }
	  public void write(String msg, String fileName) throws IOException {
		  byte[] b = msg.getBytes("ISO-8859-1");
		  writeByte(b,fileName);
	  }
	  public void log(Object aMsg){
	    System.out.println(String.valueOf(aMsg));
	  }
}  

