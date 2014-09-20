/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clothcat.rfcreader.network;

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
        // TODO handle exceptions better
        String url = "ftp://ftp.ietf.org/ietf-online-proceedings/RFCs_with_extra_files/rfc-index.xml";
        if (safe) {
            fetchIndexSafely(url);
        } else {
            fetchIndexUnsafely(url);
        }
    }

    private static void fetchIndexSafely(String url) throws FileAlreadyExistsException {
        // TODO handle exceptions better
        File f = new File("rfc-index.xml");
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

    /**
     * Fetch the RFC referred to by number.
     *
     * @param num The RFC to fetch
     * @throws java.nio.file.FileAlreadyExistsException
     */
    public static void fetchRfc(int num) throws FileAlreadyExistsException {
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
        String base_location = "ftp://ftp.rfc-editor.org/in-notes/";
        String fname = "rfc" + num + ".txt";
        File f = new File(fname);
        if (f.exists()) {
            if (!overwrite) {
                throw new FileAlreadyExistsException(f.getAbsolutePath());
            } else {
                f.delete();
            }
        }

        try {
            URL u = new URL(base_location + fname);
            FileUtils.copyURLToFile(u, f);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
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

    // fake main for ease of testing stubs
    public static void main(String[] args) {
        try {
            fetchRfc(7330);
        } catch (FileAlreadyExistsException ex) {
            Logger.getLogger(RfcFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
