/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import java.net.URI;
import java.util.Properties;

/**
 * Provides mechanisms to create, request and later manage the life-cycle of
 * configured {@link CacheManager}s, identified by {@link URI}s and scoped by
 * {@link ClassLoader}s.
 * <p/>
 * The meaning and semantics of the {@link URI} used to identify a
 * {@link CacheManager} is implementation dependent.  For applications to remain
 * implementation independent, they should avoid attempting to create {@link URI}s
 * and instead use that which is returned by {@link #getDefaultURI()}.
 *
 * @author Brian Oliver
 */
public interface CachingProvider {

  /**
   * Requests a {@link CacheManager} configured according to the implementation
   * specific {@link URI} be made available that uses the provided
   * {@link ClassLoader} for loading underlying classes.
   * <p/>
   * Multiple calls to this method with the same {@link URI} and
   * {@link ClassLoader} must return the same {@link CacheManager} instance,
   * except if a previously returned {@link CacheManager} has been closed.
   *
   * @param uri         an implementation specific URI for the
   *                    {@link CacheManager} (null means use
   *                    {@link #getDefaultURI()})
   * @param classLoader the {@link ClassLoader}  to use for the
   *                    {@link CacheManager} (null means use
   *                    {@link #getDefaultClassLoader()})
   * @param properties  the {@link Properties} for the {@link CachingProvider}
   *                    to create the {@link CacheManager} (null means no
   *                    implementation specific Properties are required)
   * @throws javax.cache.CacheException when a {@link CacheManager} for the
   *                                    specified arguments could not be produced
   */
  CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties);

  /**
   * Obtains the default {@link ClassLoader} that will be used by the
   * {@link CachingProvider}.
   *
   * @return the default {@link ClassLoader} used by the {@link CachingProvider}
   */
  ClassLoader getDefaultClassLoader();

  /**
   * Obtains the default {@link URI} for the {@link CachingProvider}.
   * <p/>
   * Use this method to obtain a suitable {@link URI} for the
   * {@link CachingProvider}.
   *
   * @return the default {@link URI} for the {@link CachingProvider}
   */
  URI getDefaultURI();

  /**
   * Obtains the default {@link Properties} for the {@link CachingProvider}.
   * <p/>
   * Use this method to obtain suitable {@link Properties} for the
   * {@link CachingProvider}.
   *
   * @return the default {@link Properties} for the {@link CachingProvider}
   */
  Properties getDefaultProperties();

  /**
   * Requests a {@link CacheManager} configured according to the implementation
   * specific {@link URI} that uses the provided {@link ClassLoader} for loading
   * underlying classes.
   * <p/>
   * Multiple calls to this method with the same {@link URI} and
   * {@link ClassLoader} must return the same {@link CacheManager} instance,
   * accept if a previously returned {@link CacheManager} has been closed.
   *
   * @param uri         an implementation specific {@link URI} for the
   *                    {@link CacheManager} (null means
   *                    use {@link #getDefaultURI()})
   * @param classLoader the {@link ClassLoader}  to use for the
   *                    {@link CacheManager} (null means
   *                    use {@link #getDefaultClassLoader()})
   * @throws javax.cache.CacheException when a {@link CacheManager} for the
   *                                    specified arguments could not be produced
   */
  CacheManager getCacheManager(URI uri, ClassLoader classLoader);

  /**
   * Requests a {@link CacheManager} configured according to the
   * {@link #getDefaultURI()} and {@link #getDefaultProperties()} be made
   * available that using the {@link #getDefaultClassLoader()} for loading
   * underlying classes.
   * <p/>
   * Multiple calls to this method must> return the same {@link CacheManager}
   * instance, accept if a previously returned {@link CacheManager} has been
   * closed.
   */
  CacheManager getCacheManager();

  /**
   * Closes all of the {@link CacheManager} instances and associated resources
   * created and maintained by the {@link CachingProvider} across all
   * {@link ClassLoader}s.
   * <p/>
   * After closing the {@link CachingProvider} will still be operational.  It
   * may still be used for acquiring {@link CacheManager} instances, though
   * those will now be new.
   */
  void close();

  /**
   * Closes all {@link CacheManager} instances and associated resources created
   * by the {@link CachingProvider} using the specified {@link ClassLoader}.
   * <p/>
   * After closing the {@link CachingProvider} will still be operational.  It
   * may still be used for acquiring {@link CacheManager} instances, though
   * those will now be new for the specified {@link ClassLoader} .
   *
   * @param classLoader the {@link ClassLoader}  to release
   */
  void close(ClassLoader classLoader);

  /**
   * Closes all {@link CacheManager} instances and associated resources created
   * by the {@link CachingProvider} for the specified {@link URI} and
   * {@link ClassLoader} .
   *
   * @param uri         the {@link URI} to release
   * @param classLoader the {@link ClassLoader}  to release
   */
  void close(URI uri, ClassLoader classLoader);

  /**
   * Determines whether an optional feature is supported by the
   * {@link CachingProvider}.
   *
   * @param optionalFeature the feature to check for
   * @return true if the feature is supported
   */
  boolean isSupported(OptionalFeature optionalFeature);
}
