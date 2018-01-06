package com.four.king.kong.basic.utils.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * <p>Description: ServiceUtils.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/5.</p>
 *
 * @author xxx
 * @version 1.0
 */
public abstract class ServiceUtils
{

    /**
     * AC
     */
    private static ApplicationContext ac;

    /**
     * <p>Description: get .</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/5.</p>
     *
     * @param id id
     * @return Object
     * @author xxx
     * @version 1.0
     */
    public static Object get(String id)
    {
        return ac.getBean(id);
    }

    /**
     * <p>Description: get.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/5.</p>
     *
     * @param id    id
     * @param clazz clazz
     * @param <T>   t
     * @return T
     * @author xxx
     * @version 1.0
     */
    public static <T> T get(String id, Class<T> clazz)
    {
        return ac.getBean(id, clazz);
    }

    /**
     * <p>Description: list.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/5.</p>
     *
     * @param clazz clazz
     * @param <T>   t
     * @return Map
     * @author xxx
     * @version 1.0
     */
    public static <T> Map<String, T> list(Class<T> clazz)
    {
        return ac.getBeansOfType(clazz);
    }

    /**
     * <p>Description: getApplicationContext .</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/5.</p>
     *
     * @return ApplicationContext ApplicationContext
     * @author xxx
     * @version 1.0
     */
    public static ApplicationContext getApplicationContext()
    {
        return ac;
    }

    /**
     * <p>Description: Resolver.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/5.</p>
     *
     * @author xxx
     * @version 1.0
     */
    public static final class Resolver implements ApplicationContextAware
    {

        /**
         * <p>Description: setApplicationContext.</p>
         * <p>Copyright: Copyright(c) 2017.</p>
         * <p>Company: xxx.</p>
         * <p>CreateTime: 2017/1/5.</p>
         *
         * @param applicationContext applicationContext
         * @throws BeansException BeansException
         * @author xxx
         * @version 1.0
         */
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
        {
            ac = applicationContext;
        }

    }

    /**
     * <p>Description: Listener.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/5.</p>
     *
     * @author xxx
     * @version 1.0
     */
    public static final class Listener implements ApplicationListener<ContextRefreshedEvent>
    {

        /**
         * queues
         */
        private static List<Runnable> queues = new ArrayList<Runnable>();
        /**
         * executor
         */
        private Executor executor;

        /**
         * <p>Description: onApplicationEvent.</p>
         * <p>Copyright: Copyright(c) 2017.</p>
         * <p>Company: xxx.</p>
         * <p>CreateTime: 2017/1/5.</p>
         *
         * @param event event
         * @author xxx
         * @version 1.0
         */
        @Override
        public void onApplicationEvent(ContextRefreshedEvent event)
        {
            if (event.getApplicationContext().getParent() == null)
            {
                synchronized (Listener.class)
                {
                    while (queues != null && !queues.isEmpty())
                    {
                        executor.execute(queues.remove(0));
                    }
                    queues = null;
                }
            }
        }

        public void setExecutor(Executor executor)
        {
            this.executor = executor;
        }

        /**
         * <p>Description: prepared .</p>
         * <p>Copyright: Copyright(c) 2017.</p>
         * <p>Company: xxx.</p>
         * <p>CreateTime: 2017/1/5.</p>
         *
         * @param runnable runnable
         * @author xxx
         * @version 1.0
         */
        public static void prepared(Runnable runnable)
        {
            if (!queuing(runnable))
            {
                runnable.run();
            }
        }

        /**
         * <p>Description: queuing .</p>
         * <p>Copyright: Copyright(c) 2017.</p>
         * <p>Company: xxx.</p>
         * <p>CreateTime: 2017/1/5.</p>
         *
         * @param runnable runnable
         * @author xxx
         * @version 1.0
         */
        private static boolean queuing(Runnable runnable)
        {
            synchronized (Listener.class)
            {
                if (queues != null)
                {
                    queues.add(runnable);
                    return true;
                }
            }
            return false;
        }
    }
}
