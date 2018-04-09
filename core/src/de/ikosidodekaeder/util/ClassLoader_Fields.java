package de.ikosidodekaeder.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Badstraße
 Turmstraße
 Südbahnhof
 Chausseestraße
 Elisenstraße
 Poststraße
 Seestraße
 Elektrizitätswerk
 Hafenstraße
 Neue Straße
 Westbahnhof
 Münchner Straße
 Wiener Straße
 Berliner Straße
 Theaterstraße
 Museumsstraße
 Opernplatz
 Nordbahnhof
 Lessingstraße
 Schillerstraße
 Wasserwerk
 Goethestraße
 Rathausplatz
 Hauptstraße
 Bahnhofstraße
 Hauptbahnhof
 Parkstraße
 Schloßallee
 * Created by Johannes on 08.04.2018.
 */

public class ClassLoader_Fields extends ClassLoader {

    static final String Package = "com.ikosidodekaeder.logic.FieldTypes";

    public static Object InstanceFromMeta(String clazz, Class<?>[] ctorArgTypes, Object... args) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> FieldInstance = Class.forName(Package + "." + clazz);
        Constructor<?> ctor = FieldInstance.getConstructor(
                ctorArgTypes);

        return ctor.newInstance(
                args
        );
    }

    public ClassLoader_Fields(ClassLoader parent) {
        super(parent);
    }

    /**
     * Loads the class from the file system. The class file should be located in
     * the file system. The name should be relative to get the file location
     *
     * @param name
     *            Fully Classified name of class, for example com.ikosidodekaeder.logic.FieldTypes.Foo
     */
    private Class getClass(String name) throws ClassNotFoundException {
        String file = name.replace('.', File.separatorChar) + ".class";
        byte[] b = null;
        try {
            // This loads the byte code data from the file
            b = loadClassFileData(file);
            // defineClass is inherited from the ClassLoader class
            // that converts byte array into a Class. defineClass is Final
            // so we cannot override it
            Class c = defineClass(name, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Every request for a class passes through this method. If the class is in
     * com.ikosidodekaeder.logic.FieldTypes package, we will use this classloader or else delegate the
     * request to parent classloader.
     *
     *
     * @param name
     *            Full class name
     */
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        System.out.println("Loading Class '" + name + "'");
        if (name.startsWith(Package)) {
            System.out.println("Loading Class using CCLoader");
            return getClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * Reads the file (.class) into a byte array. The file should be
     * accessible as a resource and make sure that its not in Classpath to avoid
     * any confusion.
     *
     * @param name
     *            File name
     * @return Byte array read from the file
     * @throws IOException
     *             if any exception comes in reading the file
     */
    private byte[] loadClassFileData(String name) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(
                name);
        int size = stream.available();
        byte buff[] = new byte[size];
        DataInputStream in = new DataInputStream(stream);
        in.readFully(buff);
        in.close();
        return buff;
    }


}
