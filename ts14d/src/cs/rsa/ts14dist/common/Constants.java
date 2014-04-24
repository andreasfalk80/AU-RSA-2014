/*
 * Copyright 2014 Henrik Baerbak Christensen, Aarhus University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
 
package cs.rsa.ts14dist.common; 
 
/** Collection of constants used in various parts of the
 * TS14-D system.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class Constants { 
   
  // List of keys used in the JSON request/reply objects 
  public static final String COMMAND_KEY = "method"; 
  public static final String USER_KEY = "user"; 
  public static final String PARAMETER_KEY = "parameter"; 
   
  // List of actual requests, is the value of key COMMAND_KEY 
  public static final String ADDLINE_REQUEST = "addline"; 
  public static final String GETREPORT_REQUEST = "getReport"; 
  public static final String GETCONTENTS_REQUEST = "getContents"; 
 
  // List of the strings used to define type of reports handled
  public static final String WEEKOVERVIEW_REPORT = "week"; 
  public static final String CATEGORYOVERVIEW_REPORT = "cate"; 
  public static final String TRANSPORTMODE_REPORT = "tran"; 
 
  // Key of the return value in a reply object  
  public static final String RETURNVALUE_KEY = "reply"; 
   
  // IP and port of the fortune cookie REST service on the
  // cloud instance
  public static final String DIGITALOCEAN_INSTANCE_IP = "146.185.162.241";
  public static final String COOKIE_REST_PORT = "8182"; 
 
} 
