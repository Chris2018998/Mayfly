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
package cn.mayfly.impl.config.xml.element;

/**
 * Store some constants node name
 *
 * @author Chris Liao
 * @version 1.0
 */
public class BeanEelementXMLTags {
	
  /**
   * bean id
   */
  public final String Id = "id";

  /**
   * bean class
   */
  public final String Class = "class";

  /**
   * Inteceptor element below bean node
   */
  public final String Singleton ="singleton";

  /**
   * Inteceptor element below bean node
   */
  public final String Init_method ="init-method";

  /**
   * Inteceptor element below bean node
   */
  public final String Destroy_method ="destroy-method";

  /**
   * Inteceptor element below bean node
   */
  public final String Factory_Bean ="factory-bean";

  /**
   * Inteceptor element below bean node
   */
  public final String Factory_Method="factory-method";
  
  /**
   * Inteceptor element below bean node
   */
  public final String Pool_Size="pool-size";
  
  /**
   * super-refID element below bean node
   */
  public final String Parent="parent";

  /**
   * Constructor element below bean node
   */
  public final String Constructor ="constructor";

  /**
   * Constructor element below bean node
   */
  public final String Constructor_arg ="constructor-arg";


  /**
   * Field element below bean node
   */
  public final String Field ="field";
  
  /**
   * Property element below bean node
   */
  public final String Property ="property";


  /**
   * invocation element below bean node
   */
  public final String Invocation ="invocation";

  /**
   * Method invocation parameter values
   */
  public final String Method_Param_Values="method-param-values";

  /**
   * Method invocation parameter value
   */
  public final String Method_Param_Value="method-param-value";

  /**
   * proxy interface classes
   */
  public final String Proxy_Interfaces ="proxy-interfaces";

  
  /**
   * interception
   */
  public final String Interception ="interception";

  /**
   * Inteceptor element below bean node
   */
  public final String Method_Name="method-name";

  /**
   * Inteceptor element below bean node
   */
  public final String Method_Param_Types = "method-param-types";

  /**
   * Inteceptor element below bean node
   */
  public final String Method_Param_Type="method-param-type";

  /**
   * Intercepted Position
   */
  public final String Intercepted_Position ="intercepted-position";

  /**
   * interceptor xml tag
   */
  public final String Interceptor ="interceptor";


  /**
   * transaction element below bean node
   */
  public final String Transaction ="transaction";

}