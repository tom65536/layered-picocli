package com.github.tom65536.picocli.layered.impl;

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
     * @param providers array of default value providers,
     *     will be searched from the beginning to the first
     *     provider returning a non-null result.
     *     The array is copied (shallow).
     */
    public LayeredDefaultProvider(IDefaultValueProvider[] providers) {
        this.providers = java.util.Arrays.copyOf(providers, providers.length);
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
    public String defaultValue(ArgSpec argSpec) throws Exception {

    }

}