package com.example.libaray.common;

/**
 * Created by AllynYonge on 3/18/16.
 */

import android.support.v4.util.SimpleArrayMap;

/**
 * 该工具类的作用是从缓存中获取一个已经用使用过的类。使用该对象你首先要传入一个创建对象的工厂
 * 它是线程安全的
 */
public class ObjectPool {

    static final int POOL_INITIAL_CAPACITY = 4;

    static final class DefaultClass {}

    static final Class<?> DEFAULT_TYPE = DefaultClass.class;

    final SimpleArrayMap<Class<?>, Object[]> mPool;
    Object[] mInuse;
    Factory mFactory;

    /**
     * Create empty thread-safe object pool. Override create(int)} to create new objects
     */
    public ObjectPool() {
        this(null);
    }

    /**
     * Create empty thread-safe object pool w
     *
     * @param factory Factory
     */
    public ObjectPool(Factory factory) {
        mFactory = factory;
        mPool = new SimpleArrayMap<>(POOL_INITIAL_CAPACITY);
        mInuse = new Object[POOL_INITIAL_CAPACITY];
    }

    /**
     * 从池子里面获取一个对象，若池子里没有就新创建一个
     * @param type Class字节码
     * @return 你需要的对象
     */
    @SuppressWarnings("unchecked")
    public <T> T acquire(Class<T> type) {
        synchronized (mPool) {
            Object[] pool = mPool.get(type);
            if (pool == null) {
                mPool.put(type, pool = new Object[POOL_INITIAL_CAPACITY]);
            }
            Object object = null;
            int size = pool.length;
            for (int i = 0; i < size; i++) {
                if (pool[i] != null) {
                    object = pool[i];
                    pool[i] = null;
                    break;
                }
            }
            if (object == null && (object = create(type)) == null) {
                throw new NullPointerException("Create has to return non-null object!");
            }
            size = mInuse.length;
            for (int i = 0; i < size; i++) {
                if (mInuse[i] == null) {
                    return (T) (mInuse[i] = object);
                }
            }
            mInuse = grow(mInuse, idealObjectArraySize(size * 2));
            return (T) (mInuse[size] = object);
        }
    }

    int inuse() {
        int size = 0;
        for (Object object : mInuse) {
            if (object != null) size++;
        }
        return size;
    }

    int sizeDefault() {
        return size(DEFAULT_TYPE);
    }

    int size(Class<?> type) {
        int size = 0;
        Object[] pool = mPool.get(type);
        if (pool != null) {
            for (Object object : pool) {
                if (object != null) size++;
            }
        }
        return size;
    }

    /**
     * 清除某个类型的所有对象
     * @param type Type of object set
     */
    public void clear(Class<?> type) {
        synchronized (mPool) {
            Object[] pool = mPool.get(type);
            if (pool != null) clear(pool);
        }
    }

    /**
     * 清空对象池中的所有数据
     */
    public void clear() {
        synchronized (mPool) {
            int size = mPool.size();
            for (int i = 0; i < size; i++) {
                Object[] pool = mPool.valueAt(i);
                if (pool != null) clear(pool);
            }
        }
    }

    /**
     * 不需要创建字节码对象就可以获取
     *
     * @return Object from set type
     */
    @SuppressWarnings("unchecked")
    public <T> T acquire() {
        return (T) acquire(DEFAULT_TYPE);
    }

    /**
     * 把该对象放入到对象池里面
     * @param object Object to release back to pool
     */
    public void release(Object object) {
        synchronized (mPool) {
            int index = indexOf(mInuse, object);
            if (object != null && index >= 0) {
                mInuse[index] = null;
                Class<?> type = object.getClass();
                if (!mPool.containsKey(type)) type = DEFAULT_TYPE;
                Object[] pool = mPool.get(type);
                int size = pool.length;
                for (int i = 0; i < size; i++) {
                    //若存储每个类型的pool已经满了，就扩容
                    if (pool[i] == null) {
                        pool[i] = object;
                        return;
                    }
                }
                //扩容
                pool = grow(pool, idealObjectArraySize(size * 2));
                pool[size] = object;
                mPool.put(type, pool);
            }
        }
    }

    /**
     * Create new object for type set
     *
     * @param type Type of object set
     * @return Non-null object
     */
    protected Object create(Class<?> type) {
        return mFactory == null ? null : mFactory.create(type);
    }

    /**
     * Factory to create objects for pool
     */
    public interface Factory {
        /**
         * Create new object for type set
         *
         * @param type Type of object set
         * @return Non-null object
         */
        Object create(Class<?> type);
    }

    static int indexOf(Object[] array, Object object) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            if (array[i] == object) return i;
        }
        return -1;
    }

    static void clear(Object[] array) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
    }

    static Object[] grow(Object[] array, int size) {
        Object[] result = new Object[size];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    static int idealObjectArraySize(int need) {
        return idealByteArraySize(need * 4) / 4;
    }

    static int idealByteArraySize(int need) {
        for (int i = 4; i < 32; i++)
            if (need <= (1 << i) - 12)
                return (1 << i) - 12;
        return need;
    }
}
