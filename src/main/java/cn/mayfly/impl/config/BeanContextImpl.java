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
package cn.mayfly.impl.config;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanContext;
import cn.mayfly.BeanContextLoadListener;
import cn.mayfly.BeanException;
import cn.mayfly.impl.MayflyBeanContainer;
import cn.mayfly.impl.config.xml.BeanXMLFileFinder;
import cn.mayfly.impl.config.xml.BeanXMLFileLoader;

/**
 * IOC Bean context
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanContextImpl extends BeanContext {

	/**
	 * file URL
	 */
	private URL[] fileURLs = null;

	/**
	 * iocContainer
	 */
	private BeanContainer iocContainer = new MayflyBeanContainer();

	/**
	 * constructor
	 */
	public BeanContextImpl() throws BeanException {
		try {
			URL url = BeanXMLFileFinder.getDefaultIocFileURL();
			if(url!=null)this.parseURLToIoc(new URL[] { url });
		} catch (IOException e) {
			throw new BeanException(e);
		}
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(String filename) throws BeanException {
		this(new String[] {filename});
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(String[] filenames) throws BeanException {
		try {
			this.parseURLToIoc(BeanXMLFileFinder.findFilesURL(filenames));
		} catch (IOException e) {
			throw new BeanException(e);
		}
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(File file) throws BeanException {
		this(new File[] { file });
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(File[] files) throws BeanException {
		try {
			this.parseURLToIoc(BeanXMLFileFinder.findFilesURL(files));
		} catch (IOException e) {
			throw new BeanException(e);
		}
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(URI fileURI) throws BeanException {
		this(new URI[] { fileURI });
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(URI[] fileURI) throws BeanException {
		try {
			this.parseURLToIoc(BeanXMLFileFinder.findFilesURL(fileURI));
		} catch (IOException e) {
			throw new BeanException(e);
		}
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(URL fileURL) throws BeanException {
		this(new URL[] { fileURL });
	}

	/**
	 * constructor
	 */
	public BeanContextImpl(URL[] fileURLs) throws BeanException {
		this.parseURLToIoc(fileURLs);
	}

	/**
	 * parse file URI and register bean to ioc container
	 */
	private void parseURLToIoc(URL[] fileURLs) throws BeanException {
		this.fileURLs = fileURLs;
		if(fileURLs==null || fileURLs.length == 0)
			throw new BeanException(new IOException("At lease supply one ioc file"));
		new BeanXMLFileLoader().loadIocFiles(iocContainer, fileURLs);
		
		// Run Listener
		List<BeanContextLoadListener> listenerList = iocContainer.getBeansListOfType(BeanContextLoadListener.class);
		Collections.sort(listenerList, new Comparator<BeanContextLoadListener>(){
			public int compare(BeanContextLoadListener o1, BeanContextLoadListener o2) {
				int result = o1.getOrder() - o2.getOrder();
				return (result > 0) ? 1 : ((result == 0) ? 0 : -1);
			}
			public boolean equals(Object obj) {
				return this == obj;
			}
		});

		BeanContextImpl context=this;
		for (int i = 0, n = listenerList.size(); i < n; i++) {
			listenerList.get(i).onContextLoaded(context);
		}
	}

	/**
	 * refresh,reload xml files
	 */
	public void refresh() throws BeanException {
		if (this.iocContainer != null)
			iocContainer.destroy();
		this.iocContainer = new MayflyBeanContainer();
		new BeanXMLFileLoader().loadIocFiles(iocContainer, fileURLs);
	}

	/**
	 * containsID
	 */
	public boolean containsId(Object key) throws BeanException {
		return iocContainer.containsId(key);
	}

	/**
	 * return bean
	 */
	public Object getBean(Object key) throws BeanException {
		return iocContainer.getBean(key);
	}

	/**
	 * return bean
	 */
	public Object getBeanOfType(Class key) throws BeanException {
		return iocContainer.getBeanOfType(key);
	}

	/**
	 * return bean
	 */
	public Map getBeansMapOfType(Class key) throws BeanException {
		return iocContainer.getBeansMapOfType(key);
	}
	
	/**
	 * return bean
	 */
	public List getBeansListOfType(Class key) throws BeanException {
		return iocContainer.getBeansListOfType(key);
	}

	/**
	 * containsID
	 */
	public void destroyContainer() throws BeanException {
		iocContainer.destroy();
	}
}