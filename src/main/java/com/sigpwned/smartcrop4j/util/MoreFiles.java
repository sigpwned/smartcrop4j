/*-
 * =================================LICENSE_START==================================
 * smartcrop4j
 * ====================================SECTION=====================================
 * Copyright (C) 2024 Andy Boothe
 * ====================================SECTION=====================================
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ==================================LICENSE_END===================================
 */
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
