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
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanException;
import cn.mayfly.element.annotation.Component;
import cn.mayfly.impl.util.ClassUtil;

/**
 * 注解扫描并装载
 * 
 * @author Liao
 */

public class BeanClassScanner {

	/**
	 * 类的Importer
	 */
	private BeanClassImporter beanClassImporter = new BeanClassImporter();

	/**
	 * 扫描注解，并将注解类蹈入容器中
	 */
	public void scan(String packageName, BeanContainer container) throws BeanException {
		try {
			boolean recursive = true;
			packageName = packageName.replace(BeanXMLConstants.Symbols_CHAR_DOT, BeanXMLConstants.Symbols_CHAR_RIGHT_SEP_CHAR);
			Enumeration dirs = BeanClassScanner.class.getClassLoader().getResources(packageName);
			while (dirs.hasMoreElements()) {
				URL url = (URL) dirs.nextElement();
				String protocol = url.getProtocol();
				if (BeanXMLConstants.FILE_Protocol.equalsIgnoreCase(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), BeanXMLConstants.FILE_UTF8_Encoding);
					importBeanInfoFromFolder(packageName, filePath, recursive, container);
				} else if (BeanXMLConstants.FILE_JAR_Protocol.equalsIgnoreCase(protocol)) {
					JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
					Enumeration<JarEntry> entries = jar.entries();
					while (entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						if (name.charAt(0) == BeanXMLConstants.Symbols_CHAR_RIGHT_SEP_CHAR)
							name = name.substring(1);

						if (name.startsWith(packageName)) {
							int idx = name.lastIndexOf(BeanXMLConstants.Symbols_CHAR_RIGHT_SEP_CHAR);
							if (idx != -1)
								packageName = name.substring(0, idx).replace(BeanXMLConstants.Symbols_CHAR_RIGHT_SEP_CHAR, BeanXMLConstants.Symbols_CHAR_DOT);

							if ((idx != -1) || recursive) {
								if (name.endsWith(BeanXMLConstants.FILE_CLASS_EXTEND_NAME) && !entry.isDirectory()) {
									String className = name.substring(packageName.length() + 1, name.length() - 6);
									Class clazz = ClassUtil.loadClass(new StringBuilder(packageName).append(BeanXMLConstants.Symbols_DOT).append(className).toString());
									if (clazz.isAnnotationPresent(Component.class)) {
										beanClassImporter.register(clazz, container);
									}
								}
							}
						}
					}
				}
			}

		} catch (BeanException e) {
			throw e;
		} catch (Exception e) {
			throw new BeanException("Failed to load annotation from package[" + packageName + "]", e);
		}
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 */
	private void importBeanInfoFromFolder(String packageName, String packagePath, final boolean recursive, BeanContainer container) throws BeanException {
		try {
			File dir = new File(packagePath);
			if (!dir.exists() || !dir.isDirectory())
				return;

			File[] dirfiles = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
				}
			});

			for (int i = 0, len = dirfiles.length; i < len; i++) {
				File file = dirfiles[i];
				if (file.isDirectory()) {
					importBeanInfoFromFolder(new StringBuilder(packageName).append(BeanXMLConstants.Symbols_DOT).append(file.getName()).toString(), file.getAbsolutePath(), recursive, container);
				} else {
					String className = file.getName().substring(0, file.getName().length() - 6);
					Class clazz = ClassUtil.loadClass(new StringBuilder(packageName.replace(BeanXMLConstants.Symbols_CHAR_RIGHT_SEP_CHAR, BeanXMLConstants.Symbols_CHAR_DOT)).append(BeanXMLConstants.Symbols_DOT).append(className).toString());
					if (clazz.isAnnotationPresent(Component.class)) {
						beanClassImporter.register(clazz, container);
					}
				}
			}
		} catch (BeanException e) {
			throw e;
		} catch (Exception e) {
			throw new BeanException("Failed to load annotation from package[" + packageName + "]", e);
		}
	}
}
