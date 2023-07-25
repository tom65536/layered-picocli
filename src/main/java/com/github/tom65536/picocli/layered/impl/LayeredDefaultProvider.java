package com.github.tom65536.picocli.layered.impl;

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

import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.IDefaultValueProvider;

/**
 * Look up defaults in a chain of default providers.
 */
public class LayeredDefaultProvider implements IDefaultValueProvider {
    /**
     * List of default providers, searched from beginning
     * up to the first entry which does not return a null
     * value.
     */
    private final IDefaultValueProvider[] providers;

    /**
     * Initialize a new instance.
     *
     * @param aProviders array of default value providers,
     *                  will be searched from the beginning to the first
     *                  provider returning a non-null result.
     *                  The array is copied (shallow).
     */
    public LayeredDefaultProvider(final IDefaultValueProvider[] aProviders) {
        this.providers = java.util.Arrays.copyOf(aProviders, aProviders.length);
    }

    /**
     * Returns the default value for an option or positional parameter or
     * {@code null}.
     * The returned value is converted to the type of the option/positional
     * parameter
     * via the same type converter used when populating this option/positional
     * parameter from a command line argument.
     *
     * @param argSpec the option or positional parameter, never {@code null}
     * @return the default value for the option or positional parameter, or
     *         {@code null} if
     *         this provider has no default value for the specified option or
     *         positional parameter
     * @throws Exception when there was a problem obtaining the default value
     */
    public String defaultValue(final ArgSpec argSpec) throws Exception {
        for (final IDefaultValueProvider provider : providers) {
            final String result = provider.defaultValue(argSpec);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
