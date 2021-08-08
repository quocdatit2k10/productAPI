package au.xero.product.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Read file message property
 */
public class PropertiesUtil {

    private static Properties props;
    private static final String START_TOKEN = "{";
    private static final String END_TOKEN = "}";

    static
    {
        props = new Properties();
        try
        {
            PropertiesUtil util = new PropertiesUtil();
            props = util.getPropertiesFromClasspath("message.properties");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // private constructor
    private PropertiesUtil()
    {
    }

    public static String getProperty(String key)
    {
        return props.getProperty(key);
    }

    public static String getProperty(String key, Object[] args)
    {
        String value = props.getProperty(key);
        if(value != null)
        {
            int index = 0;
            for(Object arg : args)
            {
                String token = START_TOKEN + String.valueOf(index) + END_TOKEN;
                value = value.replace(token, String.valueOf(arg));
                index ++;
            }
        }
        return value;
    }

    public static Set<Object> getkeys()
    {
        return props.keySet();
    }

    /**
     * loads properties file from classpath
     *
     * @param propFileName
     * @return
     * @throws IOException
     */
    private Properties getPropertiesFromClasspath(String propFileName)
            throws IOException
    {
        Properties props = new Properties();
        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream == null)
        {
            throw new FileNotFoundException("property file '" + propFileName
                    + "' not found in the classpath");
        }

        props.load(inputStream);
        return props;
    }
}
