package org.firepick.firesight.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class TemporaryDirectory {
	final Path path;

	public TemporaryDirectory() {
		this("");
	}

	public TemporaryDirectory(String prefix) {
		try {
			path = Files.createTempDirectory(prefix);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Path getPath() {
		return path;
	}

	public TemporaryDirectory markDeleteOnExit() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				delete();
			}
		});

		return this;
	}

	public void delete() {
		if (!Files.exists(path)) {
			return;
		}

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult postVisitDirectory(final Path dir, final IOException e) throws IOException {
					Files.deleteIfExists(dir);
					return super.postVisitDirectory(dir, e);
				}

				@Override
				public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
					Files.deleteIfExists(file);
					return super.visitFile(file, attrs);
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
