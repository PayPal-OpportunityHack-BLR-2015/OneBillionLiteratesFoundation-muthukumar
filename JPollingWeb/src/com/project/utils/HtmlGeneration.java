package com.project.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;



public class HtmlGeneration {

	public static void main(String args[])
	{
		try {
			FileInputStream fstream = new FileInputStream("H:/Design/WebContent/Temp.txt");
			File ostream = new File("H:/Design/WebContent/Layout.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuffer strbuff=new StringBuffer();
			String strLine;
			//int row = 0, col = 0;
			//StringTokenizer stringTokenizer;
			while ((strLine = br.readLine()) != null) {
				//System.out.println(strLine);
				strLine=strLine.replace('"', '\'');
				//System.out.println(strLine);
				strbuff.append("xml.append(\"");
				strbuff.append(strLine);
				strbuff.append("\");");
				strbuff.append(System.getProperty("line.separator"));
			}
			setContents(ostream,strbuff.toString());
		}
			catch(Exception e)
			{
				
			}
	}

	static public void setContents(File aFile, String aContents)
			throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}
		if (!aFile.exists()) {
			aFile=new File(aFile.getAbsolutePath());
			throw new FileNotFoundException("File does not exist: " + aFile);
		}
		if (!aFile.isFile()) {
			throw new IllegalArgumentException("Should not be a directory: "
					+ aFile);
		}
		if (!aFile.canWrite()) {
			throw new IllegalArgumentException("File cannot be written: "
					+ aFile);
		}

		// use buffering
		Writer output = new BufferedWriter(new FileWriter(aFile));
		try {
			// FileWriter always assumes default encoding is OK!
			output.write(aContents);
		} finally {
			output.close();
		}
}

}
