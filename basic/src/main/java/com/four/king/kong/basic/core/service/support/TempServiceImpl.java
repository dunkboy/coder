package com.four.king.kong.basic.core.service.support;


import com.four.king.kong.basic.core.service.ITempService;
import com.four.king.kong.basic.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ScheduledFuture;

/**
 * <p>Description: 临时方件处理业务</p>
 * <p>Copyright: Copyright(c)2016.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2016-07-13 14:52:22.</p>
 *
 * @author xxx
 * @version 1.0
 **/
public class TempServiceImpl implements InitializingBean, DisposableBean, ITempService
{

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(TempServiceImpl.class);
    /**
     * 常量 SUFFIX
     */
    private static final String SUFFIX = ".expires";
    /**
     * 常量 DELAY
     */
    private static final long DELAY = 1000 * 60;

    /**
     * scheduler
     */
    private TaskScheduler scheduler;
    /**
     * root
     */
    private File root;
    /**
     * future
     */
    private ScheduledFuture<?> future;

    /**
     * <p>Description: afterPropertiesSet</p>
     * <p>Copyright:Copyright(c)2016</p>
     * <p>Company: xxx</p>
     * <p>CreateTime:2016年7月12日 下午2:42:36</p>
     *
     * @author xxx
     * @version 1.0
     */
    @Override
    public void afterPropertiesSet()
    {

        Assert.notNull(scheduler, "scheduler must not be null");
        Assert.notNull(root, "temp root must not be null");

        Runnable runnable = new Runnable()
        {

            @Override
            public void run()
            {
                final long now = System.currentTimeMillis();
                File[] temps = root.listFiles(new FileFilter()
                {
                    @Override
                    public boolean accept(File pathname)
                    {
                        if (StringUtils.endsWith(pathname.getName(), SUFFIX))
                        {
                            String name = StringUtils.substringBeforeLast(pathname.getName(), SUFFIX);
                            if (StringUtils.isEmpty(name))
                            {
                                if (logger.isWarnEnabled())
                                {
                                    logger.warn("expires " + pathname);
                                }
                                return true;
                            }
                            return Long.parseLong(name) < now;
                        }
                        return false;
                    }
                });
                if (temps != null && temps.length > 0)
                {
                    for (File temp : temps)
                    {
                        if (temp.delete())
                        {
                            logger.info("delete the temp file successfully!");
                        }
                        else
                        {
                            logger.error("delete the temp file failed!");
                        }
                    }
                }
            }
        };
        future = scheduler.scheduleWithFixedDelay(runnable, DELAY);
    }

    /**
     * <p>Description: 创建.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/13.</p>
     *
     * @param delay delay
     * @return File
     * @author xxx
     * @version 1.0
     */
    @Override
    public File create(long delay)
    {
        long now = System.currentTimeMillis() + delay;
        File temp = new File(root, (now++) + SUFFIX);
        while (temp.exists())
        {
            temp = new File(root, (now++) + SUFFIX);
        }
        return temp;
    }

    /**
     * <p>Description: 创建.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/13.</p>
     *
     * @param delay   delay
     * @param content content
     * @return File
     * @author xxx
     * @version 1.0
     */
    @Override
    public File create(long delay, byte[] content)
    {
        File temp = create(delay);
        try
        {
            FileUtils.writeByteArrayToFile(temp, content);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
        return temp;
    }

    /**
     * <p>Description: 创建.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/13.</p>
     *
     * @param delay delay
     * @param is    is
     * @return File
     * @author xxx
     * @version 1.0
     */
    @Override
    public File create(long delay, InputStream is)
    {
        File temp = create(delay);
        OutputStream os = null;
        try
        {
            IOUtils.copy(is, os = new FileOutputStream(temp));
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
        finally
        {
            IOUtils.closeQuietly(os);
        }
        return temp;
    }

    /**
     * <p>Description: 获取.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/13.</p>
     *
     * @param name name
     * @return File
     * @author xxx
     * @version 1.0
     */
    @Override
    public File get(String name)
    {
        return new File(root, name);
    }

    /**
     * <p>Description: destroy.</p>
     * <p>Copyright:Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime:2016年7月12日 下午2:42:36.</p>
     *
     * @author xxx
     * @version 1.0
     */
    @Override
    public void destroy()
    {
        if (future != null)
        {
            future.cancel(false);
        }
    }

    /**
     * <p>Description: 获取.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/13.</p>
     *
     * @param root root
     * @author xxx
     * @version 1.0
     */
    public void setRoot(String root)
    {
        if (StringUtils.isEmpty(root))
        {
            this.root = FileUtils.getTempDirectory();
        }
        else
        {
            this.root = new File(root);
        }
    }

    @Override
    public File getRoot()
    {
        return root;
    }

    public void setScheduler(TaskScheduler scheduler)
    {
        this.scheduler = scheduler;
    }
}
