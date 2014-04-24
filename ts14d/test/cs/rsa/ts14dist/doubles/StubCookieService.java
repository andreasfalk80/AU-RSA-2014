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

package cs.rsa.ts14dist.doubles;

import cs.rsa.ts14dist.cookie.CookieService;

/** Stub implementation of the fortune cookie service,
 * returns an incrementally numbered but otherwise fixed
 * string suitable for testing - and NO distributed call
 * is made.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class StubCookieService implements CookieService {

  private static int count = 0;
  @Override
  public String getNextCookie() {
    return "Cookie no. "+count++;
  }

}
