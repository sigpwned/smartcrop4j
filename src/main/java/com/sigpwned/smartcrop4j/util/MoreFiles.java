package com.sigpwned.smartcrop4j.util;

import java.io.File;
import java.util.Optional;

public final class MoreFiles {

  private MoreFiles() {
  }

  /**
   * Returns the file extension of the given file, or an empty Optional if the file does not have an
   * extension. For example, "foo.txt" would return "txt".
   *
   * @param file the file
   * @return the file extension
   * @see #getFileExtension(String)
   */
  public static Optional<String> getFileExtension(File file) {
    return getFileExtension(file.getName());
  }

  /**
   * Returns the basename of the given file. For example, "foo.txt" would return "foo".
   *
   * @param file the file
   * @return the basename
   * @see #getFileBasename(String)
   */
  public static String getFileBasename(File file) {
    return getFileBasename(file.getName());
  }

  /**
   * Returns the file extension of the given filename, or an empty Optional if the filename does not
   * have an extension. For example, "foo.txt" would return "txt".
   *
   * @param filename the filename
   * @return the file extension
   */
  public static Optional<String> getFileExtension(String filename) {
    return Optional.ofNullable(filename).filter(s -> s.contains("."))
        .map(s -> s.substring(s.lastIndexOf(".") + 1, s.length()));
  }

  /**
   * Returns the basename of the given file. For example, "foo.txt" would return "foo".
   *
   * @param filename the filename
   * @return the basename
   */
  public static String getFileBasename(String filename) {
    int index = filename.lastIndexOf(".");
    return index == -1 ? filename : filename.substring(0, index);
  }
}
