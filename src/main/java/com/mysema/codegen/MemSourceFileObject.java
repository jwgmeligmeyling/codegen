/*
 * Copyright (c) 2010 Mysema Ltd.
 * 
 * base on code from https://hickory.dev.java.net/
 * 
 */
package com.mysema.codegen;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/**
 * @author tiwe
 *
 */
public class MemSourceFileObject extends SimpleJavaFileObject {
    
    private static URI toUri(String fqname) {
        return URI.create(fqname.replace(".","/") + ".java");
    }
    
    private final StringBuilder contents;
    
    public MemSourceFileObject(String fullName) {
        super(toUri(fullName),JavaFileObject.Kind.SOURCE);
        contents = new StringBuilder(1000);
    }
    
    public MemSourceFileObject(String fullName, String content) {
        this(fullName);
        contents.append(content);        
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return contents;
    }
    
    public Writer openWriter() {
        return new Writer() {
            @Override
            public Writer append(CharSequence csq) throws IOException {
                contents.append(csq);
                return this;
            }

            @Override
            public void close(){}

            @Override
            public void flush() {}

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                contents.append(cbuf,off,len);
            }
        };
    }
}