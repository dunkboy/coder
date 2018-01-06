package com.four.king.kong.basic.web.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.four.king.kong.basic.core.service.ITempService;
import com.four.king.kong.basic.utils.IStringReplaceProcess;
import com.four.king.kong.basic.utils.JsonUtils;
import com.four.king.kong.basic.utils.PropsUtils;
import com.four.king.kong.basic.utils.StringUtils;
import com.four.king.kong.basic.utils.WebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p>Description: 控制器基类 .</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/17.</p>
 *
 * @author xxx
 * @version 1.0
 */
public class BasicController
{

    protected static final String AUTH_CODE = "authCode";
    /**
     * APPLICATION_JSON_UTF8_VALUE
     */
    protected static final String APPLICATION_JSON_UTF8_VALUE = APPLICATION_JSON_VALUE + ";charset=UTF-8";

    /**
     * logger
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * mediaRoot
     */
    private static final String mediaRoot = PropsUtils.get("coder.portal.media.root");

    /**
     * tempService
     */
    @Autowired
    @Qualifier(ITempService.SERVICE_ID)
    private ITempService tempService;

    /**
     * <p>Description: 上传文件.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/17.</p>
     *
     * @param multipartFile 文件
     * @param response      HttpServletResponse
     * @throws IOException ioe
     * @author xxx
     * @version 1.0
     */
    @RequestMapping(method = POST, value = "media", produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public void uploadMedia(@RequestParam(value = "multipartFile") MultipartFile multipartFile,
                            HttpServletResponse response) throws IOException
    {

        File parent = getTempDirectory();
       
        File temp = createTempFile(parent);
        // 修改日志级别debug为info
        if (logger.isInfoEnabled())
        {
            logger.info("upload media#" + multipartFile.getOriginalFilename() + " content-type#"
                    + multipartFile.getContentType() + " temp#" + temp);
        }
        InputStream is = null;
        try
        {
            /*
             * xxx 于 2016-10-22 11:37:49 修改。
             * 修改原因：commons-io:2.5 降级到 commons-io:2.4的修改
             */
            FileUtils.copyInputStreamToFile(is = multipartFile.getInputStream(), temp);
            // FileUtils.copyToFile(is = multipartFile.getInputStream(), temp);
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }

        response.setContentType(APPLICATION_JSON_UTF8_VALUE);

        OutputStream os = response.getOutputStream();
        JsonGenerator jg = JsonUtils.Jackson.createGenerator(os);
        jg.writeStartObject();
        jg.writeStringField("contentType", multipartFile.getContentType());
        jg.writeNumberField("contentLength", multipartFile.getSize());
        jg.writeStringField("name", multipartFile.getName());
        jg.writeStringField("filename", multipartFile.getOriginalFilename());
        boolean isPass = validMedia(multipartFile.getContentType(), temp);
        if (isPass)
        {
            jg.writeStringField("temp", temp.getName());
            jg.writeBooleanField("status", true);
            jg.writeStringField("message", "上传成功!");
        }
        else
        {
            jg.writeBooleanField("status", false);
            jg.writeStringField("message", "上传失败!");
        }

        jg.writeEndObject();
        jg.flush();

    }

    /**
     * <p>Description: 验证文件类型.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/17.</p>
     *
     * @param contentType 类型
     * @param temp        文件
     * @return boolean
     * @author xxx
     * @version 1.0
     */
    protected boolean validMedia(String contentType, File temp)
    {
        if (StringUtils.startsWith(contentType, "image/"))
        {
            if (validImage(temp))
            {
                return false;
            }
        }
        else if (StringUtils.startsWith(contentType, "application/zip")
                || StringUtils.startsWith(contentType, "application/octet-stream"))
        {
            return validZipOrStream(temp);
        }
        else if (StringUtils.startsWith(contentType, "application/vnd"))
        {
            return validVnd(temp);
        }
        return true;
    }

    /**
     * <p>Description: validZipOrStream.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/23.</p>
     *
     * @param temp temp
     * @return boolean
     * @author xxx
     * @version 1.0
     */
    private boolean validZipOrStream(File temp)
    {
        InputStream is = null;
        try
        {
            is = new FileInputStream(temp);
            int b1 = is.read() & 0xff;
            int b2 = is.read() & 0xff;
            is.close();
            return (b1 == 0x50 && b2 == 0x4b) || (b1 == 0xd0 && b2 == 0xcf);// xls文件
        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(null, e);
            }
            return false;
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * <p>Description: validVnd.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/23.</p>
     *
     * @param temp temp
     * @return boolean
     * @author xxx
     * @version 1.0
     */
    private boolean validVnd(File temp)
    {
        InputStream is = null;
        try
        {
            is = new FileInputStream(temp);
            int a[] = new int[7];
            for (int i = 0; i < 7; i++)
            {
                int b = is.read() & 0xff;
                a[i] = b;
            }
            
            return (a[0] == 0x50 && a[1] == 0x4b && (a[6] == 0x06 || a[6] == 0x08 )) || (a[0] == 0xd0 && a[1] == 0xcf && a[6] == 0x1a);// xls文件

        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(null, e);
            }
            return false;
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * <p>Description: validImage.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/23.</p>
     *
     * @param temp 文件
     * @return boolean
     * @author xxx
     * @version 1.0
     */
    private boolean validImage(File temp)
    {
        // 先验证文件头字节
        InputStream is = null;
        try
        {
            is = new FileInputStream(temp);
            int b1 = is.read() & 0xff;
            int b2 = is.read() & 0xff;
            // 判断是否是图片文件头字节
            if (b1 == 0x89 && b2 == 0x50 || b1 == 0xff && b2 == 0xd8)
            {
                // 获取图片的高和宽，有高和宽的才是图片
                Image img = ImageIO.read(temp);
                if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0)
                {
                    return true;
                }
            }
            else
            {
                return true;
            }
        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(null, e);
            }
            return true;
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
        return false;
    }

    /**
     * <p>Description: 访问静态资源文件.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/17.</p>
     *
     * @param id 文件id
     * @param os OutputStream
     * @throws IOException IOException
     * @author xxx
     * @version 1.0
     */
    @RequestMapping(method = GET, value = "media")
    public void getMedia(@RequestParam(value = "id") String id, OutputStream os) throws IOException
    {

        String path = mediaRoot() + "/" + mediaPath(id);

        if (id.endsWith(".properties") || id.endsWith(".xml"))
        {
            IOUtils.write("Will not be able to access the types of files!", os);
            return;
        }
        // 修改日志级别debug为info
        if (logger.isInfoEnabled())
        {
            logger.info("get media#" + id + " -> " + path);
        }

        InputStream is = null;
        try
        {
            File media = new File(path);
            if (media.exists())
            {
                IOUtils.copy(is = new FileInputStream(media), os);
            }
            else
            {
                logger.error(media + " is not exist!");
            }
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * <p>Description: 子类继承，根据 media 的 id 返回对应的路径.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/17.</p>
     *
     * @param id 资源路径
     * @return string
     * @author xxx
     * @version 1.0
     */
    protected String mediaPath(String id)
    {
        // throw new UnsupportedOperationException(getClass().getName() +
        // " must implements method: mediaPath(id)");
        return id;
    }

    /**
     * <p>Description: 子类继承，获取 media 的根目录.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/17.</p>
     *
     * @return string
     * @author xxx
     * @version 1.0
     */
    protected String mediaRoot()
    {
//        throw new UnsupportedOperationException(getClass().getName() + " must implements method: mediaRoot()");
        Map<String, String> args = new HashMap<String, String>();
        args.put("webAppRoot", WebUtils.getWebRoot());
        return replaceEvalPath(mediaRoot, args);
    }

    protected File getTempDirectory()
    {
        return tempService.getRoot();
    }

    /**
     * <p>Description: 创建临时文件.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/17.</p>
     *
     * @param parent file
     * @return file
     * @author xxx
     * @version 1.0
     */
    protected File createTempFile(File parent)
    {
        // 默认一天后删除临时创建的文件
        return tempService.create(1000 * 60 * 60 * 24);
    }

    /**
     * 获取临时文件
     *
     * @param temp fileName
     * @return file
     */
    protected File getTempFile(String temp)
    {
        return tempService.get(temp);
    }

    /**
     * <p>Description: 替换字符.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/17.</p>
     *
     * @param path 路径
     * @param args 替换值
     * @return string
     * @author xxx
     * @version 1.0
     */
    protected String replaceEvalPath(String path, final Map<String, String> args)
    {
        return StringUtils.replace(path, "{", "}", new IStringReplaceProcess()
        {
            @Override
            public String doReplace(String key, StringBuffer src, int prefixIndex, int suffixIndex)
            {
                String value = args.get(key);
                return StringUtils.isEmpty(value) ? "{" + key + "}" : value;
            }

        });
    }

}
