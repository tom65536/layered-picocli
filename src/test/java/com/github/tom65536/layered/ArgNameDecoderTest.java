
package com.github.tom65536.picocli.layered;

/*-
 * #%L
 * layered-picocli
 * %%
 * Copyright (C) 2023 Thomas Reiter
 * %%
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
 * #L%
 */

import static org.testng.Assert.assertEquals;

import com.github.tom65536.picocli.layered.ArgNameDecoder;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 * Unit tests for {@link ArgNameDecoder}.
 */
public class ArgNameDecoderTest {
   /**
    * Test case for {@link ArgNameDecoder#stripPrefix}.
    *
    * @param prefixed the prefixed argument
    * @param stripped the expected stripped result
    */
   @Test(dataProvider="getPrefixData")
   public void testStripPrefix(
      final String prefixed,
      final String stripped
   ) {
      assertEquals(
            stripped,
            ArgNameDecoder.stripPrefix(prefixed));
   }

   @DataProvider
   public Object[][] getPrefixData() {
      return new String[][]{
         new String[]{"--prefixed", "prefixed"},
	 new String[]{"/foo-bar", "foo-bar"}
      };
   }

   //@Test



   @Command(
      name = "do",
      subcommands = {
         Subcommand1.class,
         Subcommand2.class
      },
      description = "Do something")
   static class ParentCommand implements Runnable {

      @Override
      public void run() { }
    }

   @Command(
      name = "one",
      description = "Subcommand one")
   static class Subcommand1 implements Runnable {

      @Parameters(
         arity = "1..*", 
	 description = "things")
      private String[] things;

      @Override
      public void run() {}
   }

   @Command(
      name = "two",
      description = "Subcommand two")
   static class Subcommand2 implements Runnable {

      @Parameters(description = "number")
        private int count;

      @Override
      public void run() {}
   }

}
