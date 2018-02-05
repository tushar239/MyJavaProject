package algorithms.crackingcodinginterviewbook.objectorienteddesign;

import java.util.ArrayList;
import java.util.List;

// p.g. 338 of Cracking Coding Interview book
/*
    File System can be implemented in object oriented way.

    File system is nothing but a set of directories and files.
    Each directory can have children has other directories of files.

 */
public class FileSystem {

    static class Entry {
        protected MyDirectory parentDirectory; // every file/directory has parent directory
        protected String name;  // every file/directory has these properties
        protected long createdTime;
        protected long modifiedTime;
        protected long lastAccessedTime;

        public Entry(String name, MyDirectory parent) {
            this.name = name;
            this.parentDirectory = parent;
        }


        public boolean delete() {
            if (parentDirectory == null) return false;
            return parentDirectory.delete(this);
        }

    }

    static class MyDirectory extends Entry {
        private List<Entry> entries = new ArrayList<>(); // each directory can have child directories or files

        public MyDirectory(String name, MyDirectory parentDirectory) {
            super(name, parentDirectory);
        }

        public boolean delete(Entry entry) {
            return entries.remove(entry);
        }
        public boolean add(String name, EntryTypeEnum entryType) {
            if(entryType == EntryTypeEnum.DIRECTORY) {
                return entries.add(new MyDirectory(name, this));
            } else if(entryType == EntryTypeEnum.FILE) {
                return entries.add(new MyFile(name, this));
            }
            return false;
        }
    }

    static class MyFile extends Entry {
        private String content; // each file can have content

        public MyFile(String name, MyDirectory parentDirectory) {
            super(name, parentDirectory);
        }
    }

    enum EntryTypeEnum {
        DIRECTORY,FILE;
    }
}
