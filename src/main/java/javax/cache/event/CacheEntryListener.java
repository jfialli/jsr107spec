/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

import java.util.EventListener;

/**
 * A tagging interface for cache entry listeners.
 * <p/>
 * Sub-interfaces exist for the various cache events allowing a listener to be
 * created which implements only those listeners it is interested in.
 * <p/>
 * Listeners should be implemented with care. In particular it is important to
 * consider their impact on performance and latency.
 * <p/>
 * Listeners:
 * <ul>
 * <li>are fired after the entry is mutated in the cache</li>
 * <li>if synchronous are fired, for a given key, in the order in which events
 * occur</li>
 * <li>block the calling thread until the listener returns,
 * where the listener was registered as synchronous</li>
 * <li>which are asynchronous iterate through multiple events with an undefined
 * ordering, except that events on the same key are in the the order in which the
 * events occur.</li>
 * </ul>
 * Listeners follow the observer pattern. An exception thrown by a
 * listener does not cause the cache operation to fail.
 * <p/>
 * <p/>
 * A listener which mutates a cache on the CacheManager may cause a deadlock.
 * Detection and response to deadlocks is implementation specific.
 * <p/>
 * A listener on a transactional cache is executed orthogonally to the transaction.
 * If synchronous it is executed after the mutation and not after the transaction
 * commits, and if asynchronous the timing is undefined. A listener which throws
 * an exception will not affect the transaction. A transaction which is rolled back
 * will not unfire a listener.
 *
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @see CacheEntryCreatedListener
 * @see CacheEntryUpdatedListener
 * @see CacheEntryRemovedListener
 * @see CacheEntryExpiredListener
 * @see EventType
 * @since 1.0
 */
public interface CacheEntryListener<K, V> extends EventListener {

}
