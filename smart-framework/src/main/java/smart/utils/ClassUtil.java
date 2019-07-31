package smart.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
@Slf4j
public final class ClassUtil {

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean init) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个包名下都所有class
     *
     * @param packageName
     * @param <?>
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> set = new HashSet<>(128);
        String path = packageName.replace(".", "/");
        try {
            Enumeration<URL> urls = getClassLoader().getResources(path);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocal = url.getProtocol();
                    if (protocal.equals("file")) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(set, packagePath, packageName);
                    } else if (protocal.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (null != jarFile) {
                                Enumeration<JarEntry> enumeration = jarFile.entries();
                                while (enumeration.hasMoreElements()) {
                                    JarEntry jarEntry = enumeration.nextElement();
                                    String jarName = jarEntry.getName();
                                    if (jarName.endsWith(".class")) {
                                        String className = jarName.substring(0, jarName.lastIndexOf(".")).replaceAll("/", ".");
                                        addClass(set, className);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    private static void addClass(Set<Class<?>> set, String className) {
        set.add(loadClass(className, true));
    }

    private static void addClass(Set<Class<?>> set, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(file -> file.isFile() && file.getName().endsWith(".class") || file.isDirectory());

        Arrays.asList(files).forEach(file -> {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNoneEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                addClass(set, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(set, subPackagePath, subPackageName);
            }
        });

    }

    public static void main(String[] args) {
        ClassUtil.getClassSet("smart.config");
    }
}
