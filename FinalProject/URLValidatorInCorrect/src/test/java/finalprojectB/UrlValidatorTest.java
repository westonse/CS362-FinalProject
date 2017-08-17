/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package finalprojectB;

import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }

   
   /*
    This function manually tests each of the 5 possible parts of a URL that would be passed to the isValid() funciton. First, there are valid URLs tested and then there are invalid URLS tested. Each of the URLs are only slightly changed to test the specific parts of the URL.
    */
   public void testManualTest()
   {
       System.out.println("BEGIN MANUAL TEST\n");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);



       

   }
   
   //valid URL partition
   public void testYourFirstPartition()
   {
       System.out.println("BEGIN VALID PARTITION TEST\n");
       UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       //assertions with valid urls
       assertTrue("TESTING VALID SCHEME",urlVal.isValid("h3t://www.google.com/"));
       assertTrue("TESTING VALID AUTHORITY",urlVal.isValid("http://amazon.com"));
       
       assertTrue("TESTING VALID PORT",urlVal.isValid("http://www.google.com:80")); // change port --> bug found at line 157/158 with PORT_REGEX variable
       assertTrue("TESTING VALID PATH",urlVal.isValid("http://www.google.com:80/test1"));
       assertTrue("TESTING VALID PATH OPTIONS",urlVal.isValid("http://www.google.com:80/test1/test1"));
       assertTrue("TESTING VALID QUERY",urlVal.isValid("http://www.google.com:80/test1/test1?action=view")); // change query --> bug found at line 445
   }
   //invalid URL partition
   public void testYourSecondPartition(){
       
       System.out.println("BEGIN INVALID PARTITION TEST\n");
       UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       //assertions with invalid urls
       assertFalse("TESTING INVALID SCHEME",urlVal.isValid("3ht://www.google.com/"));
       assertFalse("TESTING INVALID AUTHORITY",urlVal.isValid("http://aaa."));
       
       assertFalse("TESTING INVALID PORT",urlVal.isValid("http://www.google.com:1111212121")); // change to invalid port --> bug found at line 157/158 with PORT_REGEX variable
       assertFalse("TESTING INVALID PATH",urlVal.isValid("http://www.google.com:80/.."));
       assertFalse("TESTING INVALID PATH OPTIONS",urlVal.isValid("http://www.google.com:80/../../file"));
       assertFalse("TESTING INVALID QUERY",urlVal.isValid("http://www.google.com:80/test1/test1?action=vieww"));
       
   }
   
   
   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {
		   
	   }
   }
   
   public void testAnyOtherUnitTest()
   {
	   
   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
   

}
