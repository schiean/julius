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

import java.io.File;

import julius.validation.Assertions;

import org.apache.log4j.Logger;

/**
 * methods to create or remove directories
 */
public class DirectoryHelper {
	
	private static final Logger log = Logger.getLogger(DirectoryHelper.class);
	
	private DirectoryHelper(){
		super();
	}
	
	/**
	 * creates a directory
	 * continues if it already exists
	 * throws IllegalStateException on failure
	 * @param path
	 */
	public static void createDirectory(final String path){
		File fPath = new File( path );
		if(fPath.exists()){
			log.debug("reusing directory: "+path);
		}else{
			boolean succes = fPath.mkdirs();
			Assertions.state.assertTrue(succes, "unable to create working directory:"+path);
			log.debug("created dir:"+path);							
		}
	}
	
	/**
	 * Removes directory and contents recursively
	 * if the directory does not exists it will continue
	 * @param path
	 * @return true if there was something to delete and it was successful
	 */
	public static boolean removeDirectory(final String path){
		File fPath = new File( path );
		boolean success = true; // start as true, but only one failure will make it false until the end of the method
	    if( fPath.exists() ) {
		    // clean up contents  
	    	success = cleanUpContents(fPath, success);
	    	
		    if(!fPath.delete()){
		    	success = false;
		    }
		}else{
			success = false;
			log.debug("path:"+path+" did not exist");
		}
	    return success;
	}

	/**
	 * Tries to delete the contents of the directory
	 * will never return true if the success param was false 
	 * @param fPath
	 * @param succes (initial state of success)
	 * @return true if success was true and all worked out, false if it was false, or deletion did not succeed
	 */
	private static boolean cleanUpContents(final File fPath, boolean succes) {
		for(File file: fPath.listFiles()) {
			if(file.isDirectory()) {
				if(!removeDirectory(file.getAbsolutePath())){
					succes = false;
		    	}      	 		        	 
		    }
		    else {
		    	if(!file.delete()){
		    		succes = false;		        		
		    	}		           
		    }
		}
		return succes;
	}	
	
	/**
	 * @see julius.utilities.DirectoryHelper#removeDirectory but will throw exception if dir did not exist or could not be removed
	 * @param path
	 */
	public static void removeDirectoryFailOnError(final String path){
		boolean cleaned = removeDirectory(path); 
		Assertions.state.assertTrue(cleaned, "Unable to delete "+path+" (or at least one of the files and subdirs inside) ");		
	}
}
