package com.four.king.kong.basic.core.service.support;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.context.support.ServletContextResource;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>Description: spring托管properties属性文件类.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{

    /**
     * <p>Description: 设置文件路径.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/10.</p>
     *
     * @param locations 路径
     * @author xxx
     * @version 1.0
     */
    @Override
    public void setLocations(Resource... locations)
    {
        Set<Resource> urls = new LinkedHashSet<Resource>();
        Set<Resource> fsrls = new LinkedHashSet<Resource>();
        Set<Resource> scrls = new LinkedHashSet<Resource>();
        for (Resource location : locations)
        {
            if (location instanceof ServletContextResource)
            {
                scrls.add(location);
            }
            else if (location instanceof FileSystemResource)
            {
                fsrls.add(location);
            }
            else if (location instanceof UrlResource)
            {
                urls.add(location);
            }
            else
            {
                throw new IllegalArgumentException("not support location " + location.getClass().getName() + " -> "
                        + location);
            }
        }
        Set<Resource> locationsToUse = new LinkedHashSet<Resource>();
        // jar 包的最前面
        locationsToUse.addAll(urls);
        // 文件的覆盖 jar 包的
        locationsToUse.addAll(fsrls);
        // servlet 的覆盖文件的
        locationsToUse.addAll(scrls);
        // 设置新的顺序 locations
        super.setLocations(locationsToUse.toArray(new Resource[locationsToUse.size()]));
    }

}
