/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.expiry;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import java.io.Serializable;

/**
 * An {@link javax.cache.expiry.ExpiryPolicy} that defines the expiry {@link Duration}
 * of a Cache Entry based on the last time it was accessed. Accessed
 * does not include a cache update.
 *
 * @param <K> the type of cache keys
 * @param <V> the type of cache values
 */
public final class AccessedExpiryPolicy<K, V> implements ExpiryPolicy<K, V>, Serializable {


  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201305101601L;

  /**
   * The {@link Duration} a Cache Entry should be available before it expires.
   */
  private Duration expiryDuration;

  /**
   * Constructs an {@link AccessedExpiryPolicy} {@link javax.cache.expiry.ExpiryPolicy}.
   *
   * @param expiryDuration the {@link Duration} a Cache Entry should exist be
   *                       before it expires after being accessed
   */
  public AccessedExpiryPolicy(Duration expiryDuration) {
    this.expiryDuration = expiryDuration;
  }

  /**
   * Obtains a {@link javax.cache.configuration.Factory} for an Accessed {@link javax.cache.expiry.ExpiryPolicy}.
   *
   * @return a {@link javax.cache.configuration.Factory} for an Accessed {@link javax.cache.expiry.ExpiryPolicy}.
   */
  public static <K, V> Factory<ExpiryPolicy<? super K, ? super V>> factoryOf(Duration duration) {
    return new FactoryBuilder.SingletonFactory<ExpiryPolicy<? super K, ? super V>>(new AccessedExpiryPolicy<K, V>(duration));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForCreatedEntry(Cache.Entry<? extends K, ? extends V> entry) {
    //for newly created entries we use the specified expiry duration
    return expiryDuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForAccessedEntry(Cache.Entry<? extends K, ? extends V> entry) {
    //when a cache entry is accessed, we return the specified expiry duration,
    //ignoring the current expiry duration
    return expiryDuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForModifiedEntry(Cache.Entry<? extends K, ? extends V> entry) {
    //modifying a cache entry has no affect on the current expiry duration
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((expiryDuration == null) ? 0 : expiryDuration.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof AccessedExpiryPolicy)) {
      return false;
    }
    AccessedExpiryPolicy<?, ?> other = (AccessedExpiryPolicy<?, ?>) obj;
    if (expiryDuration == null) {
      if (other.expiryDuration != null) {
        return false;
      }
    } else if (!expiryDuration.equals(other.expiryDuration)) {
      return false;
    }
    return true;
  }
}
