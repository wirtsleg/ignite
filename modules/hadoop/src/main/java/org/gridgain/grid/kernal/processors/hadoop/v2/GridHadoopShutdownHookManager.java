/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.grid.kernal.processors.hadoop.v2;

import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * Fake manager for shutdown hooks.
 */
public class GridHadoopShutdownHookManager {
    /** */
    private static final GridHadoopShutdownHookManager MGR = new GridHadoopShutdownHookManager();

    /**
     * Return <code>ShutdownHookManager</code> singleton.
     *
     * @return <code>ShutdownHookManager</code> singleton.
     */
    public static GridHadoopShutdownHookManager get() {
        return MGR;
    }

    /** */
    private Set<Runnable> hooks = Collections.synchronizedSet(new HashSet<Runnable>());

    /** */
    private AtomicBoolean shutdownInProgress = new AtomicBoolean(false);

    /**
     * Singleton.
     */
    private GridHadoopShutdownHookManager() {
        // No-op.
    }

    /**
     * Adds a shutdownHook with a priority, the higher the priority
     * the earlier will run. ShutdownHooks with same priority run
     * in a non-deterministic order.
     *
     * @param shutdownHook shutdownHook <code>Runnable</code>
     * @param priority priority of the shutdownHook.
     */
    public void addShutdownHook(Runnable shutdownHook, int priority) {
        if (shutdownHook == null)
            throw new IllegalArgumentException("shutdownHook cannot be NULL");

        hooks.add(shutdownHook);
    }

    /**
     * Removes a shutdownHook.
     *
     * @param shutdownHook shutdownHook to remove.
     * @return TRUE if the shutdownHook was registered and removed,
     * FALSE otherwise.
     */
    public boolean removeShutdownHook(Runnable shutdownHook) {
        return hooks.remove(shutdownHook);
    }

    /**
     * Indicates if a shutdownHook is registered or not.
     *
     * @param shutdownHook shutdownHook to check if registered.
     * @return TRUE/FALSE depending if the shutdownHook is is registered.
     */
    public boolean hasShutdownHook(Runnable shutdownHook) {
        return hooks.contains(shutdownHook);
    }

    /**
     * Indicates if shutdown is in progress or not.
     *
     * @return TRUE if the shutdown is in progress, otherwise FALSE.
     */
    public boolean isShutdownInProgress() {
        return shutdownInProgress.get();
    }
}
