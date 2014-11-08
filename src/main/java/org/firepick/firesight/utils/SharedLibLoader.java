package org.firepick.firesight.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class SharedLibLoader {

	private static final SharedLibLoader SHARED_LIB_LOADER = new SharedLibLoader();

	private static final Logger log = Logger.getLogger(SharedLibLoader.class);

	private String prefix;
	private String postfix;
	private String path;

	public static void loadLibraries(String... libraries) {
		SHARED_LIB_LOADER.loadLibs(libraries);
	}

	public static String getOSName() {
		return System.getProperty("os.name");
	}

	public static String getOSArch() {
		return System.getProperty("os.arch");
	}

	public static boolean isOSWindows() {
		return getOSName().toLowerCase().startsWith("windows");
	}

	public static boolean isOSMacOSX() {
		return getOSName().equals("Mac OS X");
	}

	private SharedLibLoader() {
	}

	private String getFullLibraryPath(String baseName) {

		return File.separatorChar + path + File.separatorChar + getFullLibraryName(baseName);
	}

	private String getFullLibraryName(String baseName) {
		return prefix + baseName + postfix;
	}

	private void loadLibs(String[] libraries) {
		configureOS();
		Path tempDir = null;
		for (String lib : libraries) {
			if (!loadFromSystem(lib)) {
				log.debug("Loading supplied library for " + lib);
				if (tempDir == null) {
					tempDir = new TemporaryDirectory().markDeleteOnExit().getPath().normalize();
					addLibraryPath(tempDir.toString());
				}
				extractShared(lib, tempDir);
				System.loadLibrary(lib);

			}
		}
	}

	private boolean loadFromSystem(String libraryName) {
		try {
			System.loadLibrary(libraryName);
			log.trace("System library loaded for " + libraryName);
			return true;
		} catch (final UnsatisfiedLinkError e) {
			log.trace("library path: " + System.getProperty("java.library.path"));
			/* Only update the library path and load if the original error indicates it's missing from the library path. */
			if (!String.format("no %s in java.library.path", libraryName).equals(e.getMessage())) {
				throw e;
			}
		}
		return false;
	}

	private void configureOS() {
		prefix = "lib";
		if (isOSWindows()) {
			path = "windows";
			postfix = ".dll";
		} else {
			if (isOSMacOSX()) {
				path = "macosx";
				postfix = ".dylib";
			} else {
				path = getOSName().toLowerCase();
				postfix = ".so";
			}
		}
		path += File.separatorChar + getOSArch();

	}

	private Path extractShared(String lib, Path targetDir) {
		log.trace("Extracting " + getFullLibraryPath(lib) + " to " + targetDir);
		final InputStream binary = SharedLibLoader.class.getResourceAsStream(getFullLibraryPath(lib));
		if (binary == null) {
			throw new IllegalStateException("Library " + lib + " not available for " + getOSName() + "-" + getOSArch());
		}
		final Path destination = targetDir.resolve("./" + getFullLibraryName(lib)).normalize();

		try {
			Files.createDirectories(targetDir);
			Files.copy(binary, destination);
			return destination;
		} catch (final IOException ioe) {
			throw new IllegalStateException(String.format("Error writing native library to \"%s\".", destination), ioe);
		}

	}

	/**
	 * Adds the specified path to the java library path
	 * 
	 * taken from http://stackoverflow.com/questions/15409223/adding-new-paths-for-native-libraries-at-runtime-in-java
	 * 
	 * @param pathToAdd
	 *            the path to add
	 */
	private static void addLibraryPath(String pathToAdd) {
		try {
			final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
			usrPathsField.setAccessible(true);

			// get array of paths
			final String[] paths = (String[]) usrPathsField.get(null);

			// check if the path to add is already present
			for (String path : paths) {
				if (path.equals(pathToAdd)) {
					return;
				}
			}

			// add the new path
			final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
			newPaths[newPaths.length - 1] = pathToAdd;
			usrPathsField.set(null, newPaths);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
