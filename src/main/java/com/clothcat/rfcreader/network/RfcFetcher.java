/*
 * The MIT License
 *
 * Copyright 2014 Stephen Stafford.
 *
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
 */
package com.clothcat.rfcreader.network;

import com.clothcat.rfcreader.Constants;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 * Static methods to manage file downloads.
 *
 * @author ssta
 */
public class RfcFetcher {

    /**
     * Fetches the rfc index XML file from
     * ftp://ftp.ietf.org/ietf-online-proceedings/RFCs_with_extra_files/rfc-index.xml
     *
     * @param safe if true will not overwrite a pre-existing index.
     * @throws java.nio.file.FileAlreadyExistsException
     *
     */
    public static void fetchIndex(boolean safe) throws FileAlreadyExistsException {
        makeDirs();
        // TODO handle exceptions better
        String url = Constants.RFC_INDEX_URL;
        if (safe) {
            fetchIndexSafely(url);
        } else {
            fetchIndexUnsafely(url);
        }
    }

    private static void fetchIndexSafely(String url) throws FileAlreadyExistsException {
        // TODO handle exceptions better
        File f = new File(Constants.RFC_INDEX_LOCAL_NAME);
        if (f.exists()) {
            throw new FileAlreadyExistsException(f.getAbsolutePath());
        } else {
            try {
                URL u = new URL(url);
                FileUtils.copyURLToFile(u, f);
            } catch (MalformedURLException ex) {
                Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void fetchIndexUnsafely(String url) {
        // TODO handle exceptions better
        try {
            File f = new File("rfc-index.xml");
            if (f.exists()) {
                f.delete();
            }
            fetchIndexSafely(url);
        } catch (FileAlreadyExistsException ex) {
            Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fetch the RFC referred to by number.
     *
     * @param num The RFC to fetch
     * @throws java.nio.file.FileAlreadyExistsException
     */
    public static void fetchRfc(int num) throws FileAlreadyExistsException {
        makeDirs();
        fetchRfc(num, false);
    }

    /**
     * Fetch the RFC referred to by number.
     *
     * @param num The RFC to fetch
     * @param overwrite Whether to overwrite any existing file.
     * @throws java.nio.file.FileAlreadyExistsException
     */
    public static void fetchRfc(int num, boolean overwrite) throws FileAlreadyExistsException {
        makeDirs();
        String base_location = Constants.RFC_BASE_LOCATION;
        String s = "rfc" + num + ".txt";
        File filename = new File(Constants.RFC_CACHE + s);
        if (filename.exists()) {
            if (!overwrite) {
                throw new FileAlreadyExistsException(filename.getAbsolutePath());
            } else {
                filename.delete();
            }
        }

        try {
            URL u = new URL(base_location + s);
            FileUtils.copyURLToFile(u, filename);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the local directories required if they don't already exist. A bit
     * wasteful to keep trying to create them all the time perhaps, but better
     * than then not being there when needed...
     */
    private static void makeDirs() {
        File f;
        f = new File(Constants.RFC_CACHE);
        f.mkdirs();
        f = new File(Constants.RFC_CONFIG);
        f.mkdirs();
    }
}
