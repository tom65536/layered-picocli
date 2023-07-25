/**
 * Derive configuration property names from {@link ArgSpec}.
 */

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

import java.util.Stack;
import java.util.function.UnaryOperator;

import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
// import picocli.CommandLine.Model.PositionalParamSpec;

 /**
  * Transform argument specs to configuration property
  * names.
  */
 public class ArgNameDecoder {
   /**
    * Eagerly instantiated default instance.
    */
   private static final ArgNameDecoder
      DEFAULT_ARG_NAME_DECODER = new ArgNameDecoder();
   /**
    * The field transformer function used by this
    * instance.
    */
   private final UnaryOperator<String> keyTransform;

   /**
    * Return an instance of the default implementation.
    *
    * @return the default instance
    */
   public static ArgNameDecoder getDefault() {
      return DEFAULT_ARG_NAME_DECODER;
   }

   /**
    * Initialize an argument name transformer with
    * a transformation function.
    *
    * @param aKeyTransformer gets applied to each key
    */
   public ArgNameDecoder(
     final UnaryOperator<String> aKeyTransformer
   ) {
      this.keyTransform = aKeyTransformer;
   }

   /**
    * Hide the default constructor.
    *
    * Use {@link #getDefault} instead.
    */
   private ArgNameDecoder() {
      this(null);
   }

   /**
    * Strip a non-identifier prefix from a string.
    *
    * Implementation taken from picocli-examples.
    *
    * @param prefixed the possibly prefixed string
    * @return a string where all prefixed non-identifier
    *    characters are stripped
    */
   public static String stripPrefix(final String prefixed) {
      for (int i = 0; i < prefixed.length(); ++i) {
         if (Character.isJavaIdentifierPart(prefixed.charAt(i))) {
            return prefixed.substring(i);
         }
      }
      return "";
   }

   /**
    * Compute the qualified name of a argument or setting.
    *
    * @param argSpec the argument specification
    * @return  the parts of the qualified name.
    */
   public String[] toStrings(final ArgSpec argSpec) {
      if (argSpec.isOption()) {
         final OptionSpec opt = (OptionSpec) argSpec;
         final String key = opt.descriptionKey();
         return getKeySegments(key, opt.command());
      } else {
         throw new AssertionError("not implemwnted");
      }
   }

   /**
    * Resolve command into sequence of idenrifiers.
    *
    * @param key the name of the parameter
    * @param commandSpec the (sub-)command of this setting
    * @return the parts of the qualified name.
    */
   private String[] getKeySegments(
      final String key,
      final CommandSpec commandSpec) {
      final Stack<String> parts = new Stack<String>();
      parts.push((keyTransform == null) ? key : keyTransform.apply(key));

      for (CommandSpec cs = commandSpec; cs != null; cs = cs.parent()) {
         parts.push(
            (keyTransform == null)
            ? cs.name()
            : keyTransform.apply(cs.name())
         );
      }

      String[] result = new String[parts.size()];
      for (int i = 0; i < result.length; ++i) {
         result[i] = parts.pop();
      }
      return result;
   }
 }
