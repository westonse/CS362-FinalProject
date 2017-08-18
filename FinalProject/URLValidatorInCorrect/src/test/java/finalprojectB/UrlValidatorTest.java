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
    This function manually tests each of the 5 possible parts of a URL (Scheme,authority,port,path/pathoptions, and query) that would be passed to the isValid() funciton. First, there are valid URLs tested and then there are invalid URLS tested. Each of the URLs are only slightly changed to test the specific parts of the URL.
    */
   public void testManualTest()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       
       System.out.println("BEGIN MANUAL VALID URL TEST:\n");
       
       //assertions with valid urls
       assertTrue("ERROR: h3t://www.google.com/ is VALID, but returned FALSE",urlVal.isValid("h3t://www.google.com/"));
       assertTrue("ERROR: http://amazon.com is VALID, but returned FALSE",urlVal.isValid("http://amazon.com"));
       
       assertTrue("ERROR: http://www.google.com:80 is VALID, but returned FALSE",urlVal.isValid("http://www.google.com:80"));
       assertTrue("ERROR: http://www.google.com:80/test1 is VALID, but returned FALSE",urlVal.isValid("http://www.google.com:80/test1"));
       assertTrue("ERROR: http://www.google.com:80/test1/test1 is VALID, but returned FALSE",urlVal.isValid("http://www.google.com:80/test1/test1"));
       assertTrue("ERROR: http://www.google.com:80/test1/test1?action=view is VALID, but returned FALSE",urlVal.isValid("http://www.google.com:80/test1/test1?action=view")); // change query --> bug found at line 445
       
       System.out.println("BEGIN MANUAL INVALID URL TEST\n");
       
       //assertions with invalid urls
       assertFalse("ERROR: 3ht://www.google.com/ is INVALID, but returned TRUE",urlVal.isValid("3ht://www.google.com/"));
       assertFalse("ERROR: http://aaa. is INVALID, but returned TRUE",urlVal.isValid("http://aaa."));
       
       assertFalse("ERROR: http://www.google.com:1111212121 is INVALID, but returned TRUE",urlVal.isValid("http://www.google.com:1111212121")); // change to invalid port --> bug found at line 157/158 with PORT_REGEX variable
       assertFalse("TERROR: http://www.google.com:80/.. is INVALID, but returned TRUE",urlVal.isValid("http://www.google.com:80/.."));
       assertFalse("ERROR: http://www.google.com:80/../../file is INVALID, but returned TRUE",urlVal.isValid("http://www.google.com:80/../../file"));
       assertFalse("ERROR: http://www.google.com:80/test1/test1?action=vieww is INVALID, but returned TRUE",urlVal.isValid("http://www.google.com:80/test1/test1?action=vieww"));
       

   }
   
   /*PORT partition test
    This test loops through all possible valid port numbers that can be part of a URL and tests that the isValid() method returns true with each iteration. Furthermore, this method also tests a sample of invalid port numbers to ensure the isValid() method returns false for each invalid port number.
    */
   public void testYourFirstPartition()
   {
       System.out.println("BEGIN PORT NUMBER PARTITION TEST:\n");
       UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       String testString = "";
       boolean result = false;

       //list of different variations of invalid port numbers in result pair format
       ResultPair[] invalidPortNumbers = {
           new ResultPair("19010w211", false),
           new ResultPair("-1234", false),
           new ResultPair("-12a34", false),
           new ResultPair("234d1g", false),
           new ResultPair("190w211", false),
           new ResultPair("-123q4", false),
           new ResultPair("-134", false),
           new ResultPair("2341g", false),
           new ResultPair("aaa", false),
           new ResultPair("badport", false),
           new ResultPair("!!!!", false),
           new ResultPair("@@@", false),
           new ResultPair("90128!", false),
           new ResultPair("$$-1", false),
           new ResultPair("a))", false),
           new ResultPair("/..?", false),
       };
       
       //loop through list of invalid port numbers created above
       for (int i = 0; i < invalidPortNumbers.length; i++) {
           ResultPair invalidPair = invalidPortNumbers[i];
           System.out.println(String.format("%-100s", testString = "http://www.google.com:" + invalidPair.item) + "Expected: "+ invalidPair.valid + "\tActual: " + (result = urlVal.isValid(testString)));
           assertEquals("http://www.google.com" + invalidPair.item, invalidPair.valid, result);
       }
       
       //Loop through valid port number 0 --> 65535
       for (int j = 0; j <= 65535; j++) {
           ResultPair validPair = new ResultPair(new Integer(j).toString(), true);
           System.out.println(String.format("%-100s", testString = "http://www.google.com:" + validPair.item) + "Expected: "+ validPair.valid + "\tActual: " + (result = urlVal.isValid(testString)));
           assertEquals("http://www.google.com:" + validPair.item, validPair.valid, result);
       }
       

   }
   /*UNIT test
    This test loops through the
    */
   public void testYourSecondPartition(){
       
       UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       String testString = "";
       boolean result = false;
       
       System.out.println("BEGIN QUERY PARTITION TEST:\n");
       
       //list of invalid query formats
       ResultPair[] invalidQueries = {
           new ResultPair("@!$% &%^&(*^&*(", false),
           new ResultPair("bad==2= ", false),
           new ResultPair("nonewline\ncharacters", false),
           new ResultPair("(", false),
           new ResultPair("(mixedchar@chters!!!^", false),
       };
       
       //list of valid query formats
       ResultPair[] validQueries = {
           new ResultPair("action=view", true),
           new ResultPair("action=edit&mode=up", true),
           new ResultPair("", true)
       };

       
       for (int j = 0; j < validQueries.length; j++) {
           ResultPair validPair = validQueries[j];
           System.out.println(String.format("%-100s", testString = "http://www.google.com/path?" + validPair.item) + "Expected: "+ validPair.valid + "\tActual: " + (result = urlVal.isValid(testString)));
           assertEquals("http://www.google.com/path?" + validPair.item, validPair.valid, result);
       }
       
       
       for (int i = 0; i < invalidQueries.length; i++) {
           ResultPair invalidPair = invalidQueries[i];
           System.out.println(String.format("%-100s", testString = "http://www.google.com/path?" + invalidPair.item) + "Expected: "+ invalidPair.valid + "\tActual: " + (result = urlVal.isValid(testString)));
           assertEquals("http://www.google.com/path?" + invalidPair.item, invalidPair.valid, result);
       }
       
   }
   
    /*UNIT test
     This test loops through the test arrays constructed in the apache code given to us in part A of the project and creates valid as well as invalid URLs by combining the arrays part by part similar to what is done in the apache testIsValid() funciton. This function specifically loops thorugh the scheme, authority, path, and path option parts of the URL because the port and query parts of the URL have been examined thoroughly in the partition tests. 
     */
   
   public void testIsValid()
   {
       UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       String thisString = "";
       boolean result = false;
       boolean expect = true;
       System.out.println("\nUnit test:");
       ResultPair[] testUrlScheme = {new ResultPair("http://", true),
           new ResultPair("ftp://", true),
           new ResultPair("h3t://", true),
           new ResultPair("3ht://", false),
           new ResultPair("http:/", false),
           new ResultPair("http:", false),
           new ResultPair("http/", false),
           new ResultPair("://", false),
           new ResultPair("", true)};
       
       ResultPair[] testUrlAuthority = {new ResultPair("www.google.com", true),
           new ResultPair("go.com", true),
           new ResultPair("go.au", true),
           new ResultPair("0.0.0.0", true),
           new ResultPair("255.255.255.255", true),
           new ResultPair("256.256.256.256", false),
           new ResultPair("255.com", true),
           new ResultPair("1.2.3.4.5", false),
           new ResultPair("1.2.3.4.", false),
           new ResultPair("1.2.3", false),
           new ResultPair(".1.2.3.4", false),
           new ResultPair("go.a", false),
           new ResultPair("go.a1a", false),
           new ResultPair("go.cc", true),
           new ResultPair("go.1aa", false),
           new ResultPair("aaa.", false),
           new ResultPair(".aaa", false),
           new ResultPair("aaa", false),
           new ResultPair("", false)
       };

       ResultPair[] testPath = {new ResultPair("/test1", true),
           new ResultPair("/t123", true),
           new ResultPair("/$23", true),
           new ResultPair("/..", false),
           new ResultPair("/../", false),
           new ResultPair("/test1/", true),
           new ResultPair("", true),
           new ResultPair("/test1/file", true),
           new ResultPair("/..//file", false),
           new ResultPair("/test1//file", false)
       };
       //Test allow2slash, noFragment
       ResultPair[] testUrlPathOptions = {new ResultPair("/test1", true),
           new ResultPair("/t123", true),
           new ResultPair("/$23", true),
           new ResultPair("/..", false),
           new ResultPair("/../", false),
           new ResultPair("/test1/", true),
           new ResultPair("/#", false),
           new ResultPair("", true),
           new ResultPair("/test1/file", true),
           new ResultPair("/t123/file", true),
           new ResultPair("/$23/file", true),
           new ResultPair("/../file", false),
           new ResultPair("/..//file", false),
           new ResultPair("/test1//file", true),
           new ResultPair("/#/file", false)
       };
       boolean expected = true;
       //test URL test arrays provided in part A of project. This excludes the port and the query parts of the URL path because we have examoned those in our partition tests.
       for (int i = 0; i < testUrlScheme.length; i++) {
           for (int j = 0; j < testUrlAuthority.length; j++) {
                    for (int m = 0; m < testPath.length; m++) {
                       for (int n = 0; n < testUrlPathOptions.length; n++) {


                           ResultPair schemePair = testUrlScheme[i];
                           ResultPair authorityPair = testUrlAuthority[j];
                           ResultPair pathPair = testPath[m];
                           ResultPair pathOptionPair = testUrlPathOptions[n];

                   
                           thisString = schemePair.item;
                           thisString += authorityPair.item;
                           thisString += pathPair.item;
                           thisString += pathOptionPair.item;

                           expected = (schemePair.valid && authorityPair.valid && pathPair.valid && pathOptionPair.valid);
                   
                           //if(!(result = urlVal.isValid(thisString)))
                            System.out.println(String.format("%-100s", thisString) + "Expected: " + expected + "\tActual: " + result);
                       
                           assertEquals(thisString, expected, result);
                       }
                   }
           }
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
