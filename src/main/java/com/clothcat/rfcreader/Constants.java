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
package com.clothcat.rfcreader;

/**
 *
 * @author ssta
 */
public class Constants {

    /**
     * Base user directory for rfcreader to store its files
     */
    public static final String RFC_USERDIR = System.getProperty("user.home") + "/.rfcReader/";
    /**
     * Location for configuration files and storage of XML index file
     */
    public static final String RFC_CONFIG = RFC_USERDIR + "config/";
    /**
     * Location for storage of downloaded RFCs
     */
    public static final String RFC_CACHE = RFC_USERDIR + "cache/";
    /**
     * Location of the RFC index XML file. TODO -- make this a configuration
     * item so it can be changed by the user
     */
    public static final String RFC_INDEX_URL = "ftp://ftp.ietf.org/ietf-online-proceedings/RFCs_with_extra_files/rfc-index.xml";
    /**
     * RFC Index XSML file local filename
     */
    public static final String RFC_INDEX_LOCAL_NAME = RFC_CONFIG + "rfc-index.xml";

    /**
     * The ftp directory where the RFCs are kept. TODO -- make this a
     * configuration item so it can be changed by the user
     */
    public static final String RFC_BASE_LOCATION = "ftp://ftp.rfc-editor.org/in-notes/";
}
