package io.github.lilcatlily.bot.utils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

public class Utility
{
    /**
     * The Unix directory separator character.
     */
    public static final char   UNIX_SEPARATOR          = '/';
    /**
     * The Windows directory separator character.
     */
    public static final char   WINDOWS_SEPARATOR       = '\\';
    /**
     * Regex string for file extension separator.
     */
    public static final String EXTENSION_REGEX         = String.valueOf(WINDOWS_SEPARATOR) + String.valueOf(Utility.EXTENSION_SEPARATOR);
    /**
     * The Unix line separator string.
     */
    public static final String UNIX_LINE_SEPARATOR     = "\n";
    /**
     * The Windows line separator string.
     */
    public static final String WINDOWS_LINE_SEPARATOR  = "\r\n";
    /**
     * The system-dependent line separator string as returned by {@link System#lineSeparator()}.
     */
    public static final String LINE_SEPARATOR          = System.lineSeparator();
    /**
     * The extension separator character.
     */
    public static final char   EXTENSION_SEPARATOR     = '.';
    /**
     * The extension separator String.
     */
    public static final String EXTENSION_SEPARATOR_STR = Character.toString(EXTENSION_SEPARATOR);
    /**
     * The system separator character.
     */
    public static final char   SYSTEM_NAME_SEPARATOR   = File.separatorChar;

    public class FileX
    {
        private static final String EMPTY_STRING = "";
        private static final int    NOT_FOUND    = -1;

        public static String getFilenameWithoutExtension(final File file)
        {
            return file.getName().split("\\.")[0];
        }

        public static String getFilenameWithoutExtension(final String fileName)
        {
            return fileName.split("\\.")[0];
        }

        public static String getExtension(final File file)
        {
            if (file == null || !file.exists())
            {
                return null;
            }
            return getExtension(file.getName());
        }

        public static String getExtension(final String fileName) throws IllegalArgumentException
        {
            if (fileName == null)
            {
                return null;
            }
            final int index = indexOfExtension(fileName);
            if (index == NOT_FOUND)
            {
                return EMPTY_STRING;
            }
            return fileName.substring(index + 1);
        }

        public static int indexOfExtension(final String fileName) throws IllegalArgumentException
        {
            if (fileName == null)
            {
                return NOT_FOUND;
            }
            if (fileName.split(Utility.EXTENSION_REGEX).length > 2)
            {
                int extensionPos = fileName.indexOf(Utility.EXTENSION_SEPARATOR);
                final int lastSeparator = indexOfLastSeparator(fileName);
                return lastSeparator > extensionPos ? NOT_FOUND : extensionPos;
            }
            int extensionPos = fileName.lastIndexOf(Utility.EXTENSION_SEPARATOR);
            final int lastSeparator = indexOfLastSeparator(fileName);
            return lastSeparator > extensionPos ? NOT_FOUND : extensionPos;
        }

        /**
         * Returns the index of the last directory separator character.
         * The position of the last forward or backslash is returned.
         * The output will be the same irrespective of the machine that the code is running on.
         *
         * @param  fileName
         *                      the file name to find the last path separator in, null returns -1
         * 
         * @return          the index of the last separator character, or -1 if there
         *                  is no such character
         */
        public static int indexOfLastSeparator(final String fileName)
        {
            if (fileName == null)
            {
                return NOT_FOUND;
            }
            final int lastUnixPos = fileName.lastIndexOf(Utility.UNIX_SEPARATOR, 1);
            final int lastWindowsPos = fileName.lastIndexOf(Utility.WINDOWS_SEPARATOR, 1);
            return Math.max(lastUnixPos, lastWindowsPos);
        }

        /**
         * Checks whether the extension of the file name is one of those specified.
         * <p>
         * This method obtains the extension as the textual part of the file name
         * after the last dot. There must be no directory separator after the dot.
         * The extension check is case-sensitive on all platforms.
         *
         * @param  fileName
         *                                      the file name, null returns false
         * @param  extensions
         *                                      the extensions to check for, null checks for no extension
         * 
         * @return                          true if the file name is one of the extensions
         * 
         * @throws IllegalArgumentException
         *                                      if the file name contains the null character ({@code U+0000})
         */
        public static boolean isExtension(final String fileName, final Collection<String> extensions)
        {
            if (fileName == null)
            {
                return false;
            }
            requireNonNullChars(fileName);
            if (extensions == null || extensions.isEmpty())
            {
                return indexOfExtension(fileName) == NOT_FOUND;
            }
            return extensions.contains(getExtension(fileName));
        }

        /**
         * Checks whether the extension of the file name is that specified.
         * <p>
         * This method obtains the extension as the textual part of the file name
         * after the last dot. There must be no directory separator after the dot.
         * The extension check is case-sensitive on all platforms.
         *
         * @param  fileName
         *                                      the file name, null returns false
         * @param  extension
         *                                      the extension to check for, null or empty checks for no extension
         * 
         * @return                          true if the file name has the specified extension
         * 
         * @throws IllegalArgumentException
         *                                      if the file name contains the null character ({@code U+0000})
         */
        public static boolean isExtension(final File file, final String extension)
        {
            if (file == null)
            {
                return false;
            }
            var fileName = file.getName();
            requireNonNullChars(fileName);
            if (isEmpty(extension))
            {
                return indexOfExtension(fileName) == NOT_FOUND;
            }
            return getExtension(fileName).equals(extension);
        }

        /**
         * Checks whether the extension of the file name is that specified.
         * <p>
         * This method obtains the extension as the textual part of the file name
         * after the last dot. There must be no directory separator after the dot.
         * The extension check is case-sensitive on all platforms.
         *
         * @param  fileName
         *                                      the file name, null returns false
         * @param  extension
         *                                      the extension to check for, null or empty checks for no extension
         * 
         * @return                          true if the file name has the specified extension
         * 
         * @throws IllegalArgumentException
         *                                      if the file name contains the null character ({@code U+0000})
         */
        public static boolean isExtension(final String fileName, final String extension)
        {
            if (fileName == null)
            {
                return false;
            }
            requireNonNullChars(fileName);
            if (isEmpty(extension))
            {
                return indexOfExtension(fileName) == NOT_FOUND;
            }
            return getExtension(fileName).equals(extension);
        }

        /**
         * Checks whether the extension of the file name is one of those specified.
         * This method obtains the extension as the textual part of the file name
         * after the last dot. There must be no directory separator after the dot.
         * The extension check is case-sensitive on all platforms.
         *
         * @param  fileName
         *                                      the file name, null returns false
         * @param  extensions
         *                                      the extensions to check for, null checks for no extension
         * 
         * @return                          true if the file name is one of the extensions
         * 
         * @throws IllegalArgumentException
         *                                      if the file name contains the null character ({@code U+0000})
         */
        public static boolean isExtension(final String fileName, final String... extensions)
        {
            if (fileName == null)
            {
                return false;
            }
            requireNonNullChars(fileName);
            if (extensions == null || extensions.length == 0)
            {
                return indexOfExtension(fileName) == NOT_FOUND;
            }
            final String fileExt = getExtension(fileName);
            return Stream.of(extensions).anyMatch(fileExt::equals);
        }

        private static String requireNonNullChars(final String path)
        {
            if (path.indexOf(0) >= 0)
            {
                throw new IllegalArgumentException("Null character present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it");
            }
            return path;
        }

        private static boolean isEmpty(final String string)
        {
            return string == null || string.isEmpty();
        }

        /**
         * Flips the Windows name separator to Linux and vice-versa.
         *
         * @param  ch
         *                The Windows or Linux name separator.
         * 
         * @return    The Windows or Linux name separator.
         */
        public static char invertSeparator(final char ch)
        {
            if (ch == Utility.UNIX_SEPARATOR)
            {
                return Utility.WINDOWS_SEPARATOR;
            }
            if (ch == Utility.WINDOWS_SEPARATOR)
            {
                return Utility.UNIX_SEPARATOR;
            }
            throw new IllegalArgumentException(String.valueOf(ch));
        }
    }

    public final class NIO
    {
        /**
         * Returns a list of elements within the specified directory.
         *
         * @param  directory
         *                         a {@link Path} to a directory.
         * 
         * @return             a mutable {@link List} of {@link Path}s to elements within the specified directory.
         * 
         * @throws IOException
         *                         if an I/O error occurs when opening the directory.
         */
        public static List<Path> list(final String directory, Predicate<File> predicate) throws IOException
        {
            return list(Paths.get(directory)).stream().map(Path::toFile).filter(predicate).map(File::toPath).collect(Collectors.toList());
        }

        /**
         * Returns a list of elements within the specified directory.
         *
         * @param  directory
         *                         a {@link Path} to a directory.
         * 
         * @return             a mutable {@link List} of {@link Path}s to elements within the specified directory.
         * 
         * @throws IOException
         *                         if an I/O error occurs when opening the directory.
         */
        public static List<Path> list(final String directory)
        {
            try
            {
                return list(Paths.get(directory));
            } catch (IOException e)
            {
                e.printStackTrace();
                return List.of();
            }
        }

        /**
         * Returns a list of elements within the specified directory.
         *
         * @param  directory
         *                         a {@link Path} to a directory.
         * 
         * @return             a mutable {@link List} of {@link Path}s to elements within the specified directory.
         * 
         * @throws IOException
         *                         if an I/O error occurs when opening the directory.
         */
        public static List<Path> list(final Path directory) throws IOException
        {
            Checks.checkNotNull(directory, "directory should not be null");
            Checks.checkArgument(Files.isDirectory(directory), "directory should be a directory");
            try (Stream<Path> list = Files.list(directory))
            {
                return list.collect(Collectors.toList());
            }
        }

        /**
         * Returns whether the tree of the specified directory is empty, i.e. does not contain
         * any files and only contains directories.
         *
         * @param  directory
         *                         a {@link Path} to a directory.
         * 
         * @return             {@code true} if the tree of the specified directory is empty,
         *                     or otherwise {@code false}.
         * 
         * @throws IOException
         *                         if an I/O error occurs when opening a directory.
         */
        public static boolean isTreeEmpty(final Path directory) throws IOException
        {
            Checks.checkNotNull(directory, "directory should not be null");
            Checks.checkArgument(Files.isDirectory(directory), "directory should be a directory");
            List<Path> children = list(directory);
            while (!children.isEmpty())
            {
                final List<Path> nextChildren = new ArrayList<>();
                for (Path child : children)
                {
                    if (!Files.isDirectory(child))
                    {
                        return false;
                    }
                    nextChildren.addAll(list(child));
                }
                children = nextChildren;
            }
            return true;
        }

        /**
         * Creates any nonexistent parent directories of the specified {@link Path} if necessary.
         *
         * @param  path
         *                         a {@link Path}.
         * 
         * @throws IOException
         *                         if an I/O error occurs.
         */
        public static void ensureParentExists(final Path path) throws IOException
        {
            Checks.checkNotNull(path, "path should not be null");
            final Path parent = path.getParent();
            if (parent != null)
            {
                Files.createDirectories(parent);
            }
        }

        /**
         * Copies the specified files to the specified target directory while preserving directory
         * structure.
         * This is done by finding the common ancestor of the {@link Path}s using
         * {@link Path#getCommonAncestor(Collection)}.
         *
         * @param  files
         *                             a collection of {@link Path}s.
         * @param  targetDirectory
         *                             a {@link Path} to a target directory.
         * @param  options
         *                             {@link CopyOption}s that specify how the files should be copied.
         * 
         * @throws IOException
         *                             if an I/O error occurs.
         */
        public static void copyPreservingDirectoryStructure(final Collection<Path> files, final Path targetDirectory, CopyOption... options) throws IOException
        {
            Checks.checkNotNull(files, "files should not be null");
            Checks.checkNotNull(targetDirectory, "targetDirectory should not be null");
            Checks.checkArgument(Files.isDirectory(targetDirectory), "targetDirectory should be a directory");
            Checks.checkNotNull(options, "options should not be null");
            if (files.isEmpty())
            {
                return;
            }
            if (files.size() == 1)
            {
                final Path file = files.iterator().next();
                Files.copy(file, targetDirectory.resolve(file.getFileName()), options);
                return;
            }
            final List<Path> normalized = files.stream().map(file -> file.toAbsolutePath().normalize()).collect(Collectors.toList());
            final Path commonAncestor = PathX.getCommonAncestor(normalized);
            for (Path file : normalized)
            {
                final Path target = targetDirectory.resolve(commonAncestor.relativize(file));
                ensureParentExists(target);
                Files.copy(file, target, options);
            }
        }

        /**
         * Returns a list of {@link Path}s that match the specified glob relative to the
         * specified directory.
         *
         * @param  directory
         *                         a {@link Path} to a directory.
         * @param  glob
         *                         a glob. The Unix path separator ({@code /}) should be used instead of the
         *                         Windows path separator ({@code \}), as the backslash is used as an escape character.
         * 
         * @return             a list of {@link Path}s that match the specified glob relative to the
         *                     specified directory.
         * 
         * @throws IOException
         *                         if an I/O error occurs.
         * 
         * @see                FileSystem#getPathMatcher(String)
         */
        public static List<Path> matchGlob(final Path directory, final String glob) throws IOException
        {
            Checks.checkNotNull(directory, "directory should not be null");
            Checks.checkArgument(Files.isDirectory(directory), "directory should be a directory");
            Checks.checkNotNull(glob, "glob should not be null");
            var normalizrdDirectory = directory.toAbsolutePath().normalize();
            final FileSystem fileSystem = normalizrdDirectory.getFileSystem();
            final PathMatcher matcher = fileSystem.getPathMatcher("glob:" + PathX.withUnixDirectorySeparators(normalizrdDirectory) + "/" + glob);
            try (Stream<Path> stream = Files.walk(normalizrdDirectory))
            {
                return stream.filter(matcher::matches).collect(Collectors.toList());
            }
        }
    }

    public static class PathX
    {
        /**
         * Returns whether the specified string is a valid path.
         *
         * @param  path
         *                  a string.
         * 
         * @return      {@code true} if the specified string is a valid path, or otherwise {@code false}.
         */
        public static boolean isValid(final String path)
        {
            if (path == null)
            {
                return false;
            }
            try
            {
                Paths.get(path);
                return true;
            } catch (InvalidPathException ignored)
            {
            }
            return false;
        }

        /**
         * Returns the file name of the specified {@link PathX} as a string.
         * If the {@link PathX} has zero elements, an empty string is returned.
         *
         * @param  path
         *                  a path.
         * 
         * @return      the file name of the specified {@link PathX} as a string.
         */
        public static String getFileName(final Path path)
        {
            Checks.checkNotNull(path, "path should not be null");
            final Path name = path.getFileName();
            return name == null ? "" : name.toString();
        }

        /**
         * Returns whether a {@link PathX} is the ancestor of another {@link PathX}.
         *
         * @param  ancestor
         *                      a potential ancestor {@link PathX}.
         * @param  child
         *                      a potential child {@link PathX}.
         * 
         * @return          {@code true} if the specified potential ancestor {@link PathX} is an ancestor of the
         *                  specified potential child {@link PathX}, or otherwise {@code false}.
         */
        public static boolean isAncestor(final Path ancestor, final Path child)
        {
            Checks.checkNotNull(ancestor, "ancestor should not be null");
            Checks.checkNotNull(child, "child should not be null");
            var c = child;
            while ((c = child.getParent()) != null)
            {
                if (c.equals(ancestor))
                {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns the closest common ancestor {@link PathX} of two {@link PathX}s.
         *
         * @param  path1
         *                   a {@link PathX}.
         * @param  path2
         *                   another {@link PathX}.
         * 
         * @return       the closest common ancestor {@link PathX} of two {@link PathX}s.
         */
        public static Path getCommonAncestor(final Path path1, final Path path2)
        {
            Checks.checkNotNull(path1, "path1 should not be null");
            Checks.checkNotNull(path2, "path2 should not be null");
            // Taken from https://stackoverflow.com/a/54596369
            if (path1.equals(path2))
            {
                return path1;
            }
            var nPath1 = path1.normalize();
            var nPath2 = path2.normalize();
            final int smallestNameCount = Math.min(nPath1.getNameCount(), nPath2.getNameCount());
            for (int i = smallestNameCount; i > 0; i--)
            {
                final Path subpath1 = nPath1.subpath(0, i);
                if (subpath1.equals(nPath2.subpath(0, i)))
                {
                    // Since Path#subpath strips away the initial "/", we prepend Path#getRoot.
                    return Paths.get(Objects.toString(nPath1.getRoot(), "") + subpath1.toString());
                }
            }
            return nPath1.getRoot();
        }

        /**
         * Returns the closest common ancestor {@link PathX} of a collection of {@link PathX}s.
         *
         * @param  paths
         *                   a collection of {@link PathX}s.
         * 
         * @return       the closest common ancestor {@link PathX} of the specified {@link PathX}s.
         */
        public static Path getCommonAncestor(final Collection<Path> paths)
        {
            Checks.checkNotNull(paths, "paths should not be null");
            Checks.checkArgument(paths.size() > 1, "paths should contain at least two elements");
            Path ancestor = null;
            // We use ImmutableSet#copyOf to remove duplicates.
            for (Path path : Set.copyOf(paths))
            {
                ancestor = ancestor == null ? path : getCommonAncestor(ancestor, path);
            }
            return ancestor;
        }

        /**
         * Returns the string representation of the specified {@link PathX} with Unix directory
         * separators.
         *
         * @param  path
         *                  a {@link PathX}.
         * 
         * @return      the string representation of the specified {@link PathX} with Unix directory
         *              separators.
         * 
         * @see         Utility#UNIX_SEPARATOR
         */
        public static String withUnixDirectorySeparators(final Path path)
        {
            Checks.checkNotNull(path, "path should not be null");
            return withUnixDirectorySeparators(path.toString());
        }

        /**
         * Returns the specified path with Unix directory separators.
         *
         * @param  path
         *                  a path.
         * 
         * @return      the specified path with Unix directory separators.
         * 
         * @see         Utility#UNIX_SEPARATOR
         */
        public static String withUnixDirectorySeparators(final String path)
        {
            Checks.checkNotNull(path, "path should not be null");
            return path.replace(Utility.WINDOWS_SEPARATOR, Utility.UNIX_SEPARATOR);
        }
    }
}
