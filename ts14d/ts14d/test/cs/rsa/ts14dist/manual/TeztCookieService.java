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
 
package cs.rsa.ts14dist.manual;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;

import cs.rsa.ts14dist.common.Constants;
import cs.rsa.ts14dist.cookie.*;

/** MANUAL test used in TDD of the implementation of
 * CookieService with real binding to the DigitalOcean
 * instanse - you have to visually inspect that
 * something is coming back from the Cookie REST service.
 * 
 * And - the rest service must be running :)
 *  
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class TeztCookieService {
  
  private CookieService service;

  @Before
  public void setup() {
    service = new StandardCookieService(Constants.DIGITALOCEAN_INSTANCE_IP, Constants.COOKIE_REST_PORT);
  
  }
  
  @Test
  public void shouldGetCookieFromService() throws IOException {
    String cookie = service.getNextCookie();
    
    assertNotNull(cookie);
    
    System.out.println(cookie);
  }
}
