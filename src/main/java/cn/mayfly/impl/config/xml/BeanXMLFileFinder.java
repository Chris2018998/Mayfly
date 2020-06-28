/*
 * Copyright Chris2018998
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.mayfly.impl.config.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import cn.mayfly.impl.exception.BeanConfiguerationFileException;
import cn.mayfly.impl.util.StringUtil;
import cn.mayfly.impl.util.Symbols;

/**
 * File Util
 *
 * @author Chris.Liao
 * @version 1.0
 */
public class BeanXMLFileFinder {

	/**
	 * 默认装载的XML配置文件
	 */
	private static final String IOC_FILE_NAME = "ioc.xml";

	/**
	 * 当前类的classloader
	 */
	private static ClassLoader SYS_CLASS_LOADER = BeanXMLFileFinder.class.getClassLoader();

	/**
	 * 默认文件的定位
	 */
	public static URL findFileURL(String fileName) {
		URL fileURL = null;
		if (!StringUtil.isNull(fileName)) {
			fileName = fileName.trim();
			String fileNameLowerCase= fileName.toLowerCase();
			
			if (fileNameLowerCase.startsWith(BeanXMLConstants.FILE_CP)) {
				fileName = fileName.substring(BeanXMLConstants.FILE_CP.length());
				fileURL = SYS_CLASS_LOADER.getResource(fileName);
			} else if (fileNameLowerCase.startsWith(BeanXMLConstants.FILE_CLASSPATH)) {
				fileName = fileName.substring(BeanXMLConstants.FILE_CLASSPATH.length());
				fileURL = SYS_CLASS_LOADER.getResource(fileName);
			} else {
				File file = new File(fileName);
				if (file.exists() && file.isFile()) {
					try {
						fileURL = file.toURI().toURL();
					} catch (Throwable e) {}
				}
				if(fileURL==null)
				   fileURL = SYS_CLASS_LOADER.getResource(fileName);
			 }
		}
		return fileURL;
	}

	/**
	 * 默认文件的定位
	 */
	public static URL getDefaultIocFileURL() throws IOException {
		URL fileURL = SYS_CLASS_LOADER.getResource(IOC_FILE_NAME);
		if (fileURL == null)
			throw new IOException("Default ioc File[" + IOC_FILE_NAME + "] not found in classpath:" + IOC_FILE_NAME);
		else
			return fileURL;
	}

	public static URL[] findFilesURL(String[] fileNames) throws IOException {
		List<URL> URLList = new ArrayList<URL>();
		if (fileNames != null) {
			for (int i = 0, n = fileNames.length; i < n; i++) {
				if (!StringUtil.isNull(fileNames[i])) {
					URL fileURL = findFileURL(fileNames[i]);
					if (fileURL == null)
						throw new FileNotFoundException("File not found:" + fileNames[i]);
					URLList.add(fileURL);
				}
			}
		}
		return URLList.toArray(new URL[URLList.size()]);
	}

	public static URL[] findFilesURL(File[] files) throws IOException {
		List<URL> URLList = new ArrayList<URL>();
		if (files != null) {
			for (int i = 0, n = files.length; i < n; i++) {
				if (files[i] != null) {
					if (files[i].isFile()) {
						try {
							URLList.add(files[i].toURI().toURL());
						} catch (Throwable e) {
							throw new IOException(e.getMessage(), e);
						}
					} else {
						throw new IOException("File is not a valid file:" + files[i]);
					}
				}
			}
		}
		return URLList.toArray(new URL[URLList.size()]);
	}

	public static URL[] findFilesURL(URI[] fileURIs) throws IOException {
		List<URL> URLList = new ArrayList<URL>();
		if (fileURIs != null) {
			for (int i = 0, n = fileURIs.length; i < n; i++) {
				if (fileURIs[i] != null) {
					try {
						URLList.add(fileURIs[i].toURL());
					} catch (Throwable e) {
						throw new IOException(e.getMessage(), e);
					}
				}
			}
		}
		return URLList.toArray(new URL[URLList.size()]);
	}

	/**
	 * 验证XML文件
	 */
	public static void validateXMLFile(URL url) throws BeanConfiguerationFileException {
		if (url == null)
			throw new BeanConfiguerationFileException("File URL can't be null");
		if (!url.getFile().toLowerCase().endsWith(BeanXMLConstants.XML_FILE_EXTEND))
			throw new BeanConfiguerationFileException("File[" + url + "]is not a valid XML file");
	}

	/**
	 * 验证顶级节点
	 */
	public static void validateXMLRoot(Element rootElement, String rootName, String filename)
			throws BeanConfiguerationFileException {
		if (rootElement == null)
			throw new BeanConfiguerationFileException("Missed root node in file:" + filename);
		if (!rootElement.getName().equalsIgnoreCase(rootName))
			throw new BeanConfiguerationFileException("Missed root node<" + rootName + "> in file:" + filename);
	}

	/**
	 * 获得文件的路径
	 */
	public static String getFilePath(String fileName) {
		int point = fileName.lastIndexOf(Symbols.RIGHT_FILE_SEP_CHAR);
		if (point == -1)
			point = fileName.lastIndexOf(Symbols.LEFT_FILE_SEP_CHAR);
		String folder = fileName;
		if (point > 0) {
			folder = folder.substring(0, point);
		}

		if (folder.startsWith(Symbols.RIGHT_FILE_SEP_CHAR)) {
			folder = folder.substring(1);
		}
		return folder;
	}
}
