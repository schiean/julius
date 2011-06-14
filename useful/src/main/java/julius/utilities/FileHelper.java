/**
 * Copyright 2011 AJG van Schie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package julius.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import julius.validation.Assertions;

import org.apache.log4j.Logger;

/**
 * methods for reading and writing files
 */
public final class FileHelper {
	private static final Logger log = Logger.getLogger(FileHelper.class);
	
	private FileHelper(){
		super();
	}
		
	
	/** 
	 * Read a file from filesystem or classpath
	 * @param filename Locations
	 * @return the inputstream for the first file that is not-null 
	 */
	public static InputStream getInputStream(final String filename){
		InputStream inputStream = null;
		try{
			inputStream = new FileHelper().getClass().getClassLoader().getResourceAsStream(filename);
		}catch(Exception e){ 
			/* not interesting */
			log.trace("unable to load from resource stream "+filename);
		}
		try{
			if(inputStream == null){
				inputStream = new FileInputStream(filename);
			}
		}catch(Exception e){ 
			/* not interesting */
			log.trace("unable to load from file input stream "+filename);
		}	
		
		Assertions.argument.assertNotNull(inputStream, "unable to load resource for file: "+filename);
		
		return inputStream;
	}
	
	/**
	 * Read a file (from file system or classpath) and split each line on the ';' character
	 * @param filename
	 * @return
	 */
	public static List<String[]> readCsvFile(final String filename){
		return readSeparatedFile(filename, ";");
	}
	
	/**
	 * Read a file (from file system or classpath) and split each line on the provided character
	 * @param filename
	 * @param separator the column separator (regexp)
	 * @return
	 */
	public static List<String[]> readSeparatedFile(final String filename, final String separator){
		List<String[]> list =  CollectionHelper.createLinkedList();
		for(String line:readFile(filename)){
			list.add(line.split(separator));
		}
		return list;
	}
	
	/**
	 * Reads a file (from file system or classpath) to a single string
	 * @param filename
	 * @return
	 */
	public static String readFileAsString(final String filename){		
		StringBuilder sb = new StringBuilder();
		for(String line:readFile(filename)){
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * reads file  (from file system or classpath) to a list of strings (item per line)
	 * @param filename
	 * @return
	 */
	public static List<String> readFile(final String filename) {
		return readFile(filename, null);
	}

	/**
	 * reads file  (from file system or classpath) to a list of strings (item per line)
	 * @param filename
	 * @param charset (optional)
	 * @return
	 */
	public static List<String> readFile(final String filename, final String charset) {
		List<String> list = CollectionHelper.createLinkedList();
		InputStream in = null;
		BufferedReader br = null;
		try {
			in = getInputStream(filename);
			br = new BufferedReader(createInpuStreamReaderForCharset(in,charset));
			
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				list.add(strLine);
			}
		} catch (FileNotFoundException e) {
			log.error("error ",e);
		} catch (IOException e) {
			log.error("error ",e);
		}
		finally{
			handleCloseReader(br);
			handleCloseStream(in);
		}
        return list;
	}


	
	/**
	 * write contents to filename
	 * @param content
	 * @param filename
	 * @return
	 */
	public static boolean writeFile(final String content, final String filename) {
		return(writeFile(content, filename, null));
	}
	
	/**
	 * writes the contents to the filename
	 * swallows exceptions 
	 * @param content
	 * @param filename
	 * @param charset (optional)
	 * @return true==success, false == error
	 */
	public static boolean writeFile(final String content, final String filename, final String charset) {
		BufferedWriter writer = null;
		boolean success = false;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			OutputStreamWriter osw = createOutputStreamReaderForCharset(fileOutputStream, charset);
			writer = new BufferedWriter(osw);
			writer.write(content);
			writer.close();
			success = true;
		} catch (IOException e) {
			log.error("error ",e);
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					log.error("error ",e);
				}
			}
		}
		return success;
	}


	private static InputStreamReader createInpuStreamReaderForCharset(final InputStream in,final String charset)
			throws UnsupportedEncodingException {
		return (charset != null && charset.length() > 0) ? new InputStreamReader(in, charset) : new InputStreamReader(in);
	}


	private static OutputStreamWriter createOutputStreamReaderForCharset(final FileOutputStream fileOutputStream, final String charset)
			throws UnsupportedEncodingException {
		return (charset != null && charset.length() > 0) ? new OutputStreamWriter(fileOutputStream, charset) :new OutputStreamWriter(fileOutputStream) ;
	}
	

	private static void handleCloseStream(final InputStream in) {
		if(in!=null){
			try {
				in.close();
			} catch (IOException e) {
				log.error("error ",e);
			}
		}
	}

	private static void handleCloseReader(final BufferedReader br) {
		if(br!=null){
			try {
				br.close();
			} catch (IOException e) {
				log.error("error ",e);
			}
		}
	}
	 
	
}