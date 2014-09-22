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

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.rfc_editor.rfc_index.Author;
import org.rfc_editor.rfc_index.RfcEntry;
import org.rfc_editor.rfc_index.RfcIndex;

/**
 * Helper class to parse the XML index file and to pull data from it
 *
 * @author ssta
 */
public class XmlHelper {

    private static XmlHelper instance;
    private RfcIndex index;
    private List<RfcEntry> rfcList;
    private List<String> allAuthorNames;

    /**
     * Singleton
     */
    private XmlHelper() {
        try {
            File f = new File(Constants.RFC_INDEX_LOCAL_NAME);
            JAXBContext ctx = JAXBContext.newInstance(RfcIndex.class);
            Unmarshaller unm = ctx.createUnmarshaller();
            index = (RfcIndex) unm.unmarshal(f);
        } catch (JAXBException ex) {
            Logger.getLogger(XmlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<RfcEntry> getRfcList() {
        if (rfcList == null) {
            // current number of RFCs is 7200 or so...I think 
            // 9000 is big enough to not need the list to resize
            // impacting performance whilst not being TOO wasteful 
            // of space
            rfcList = new ArrayList<>(9000);
            for (Object o : index.getStdEntryAndBcpEntryAndFyiEntry()) {
                if (o instanceof RfcEntry) {
                    RfcEntry re = (RfcEntry) o;
                    rfcList.add(re);
                }
            }
        }
        return rfcList;
    }

    /**
     * Filters the list of RfcEntry by searching the title (case-insensitively)
     * for the search terms given.
     *
     * @param searchTerms An array of terms to be searched for. All terms must
     * match for entry to be included.
     */
    public List<RfcEntry> filteredListByTitle(String[] searchTerms) {
        List<RfcEntry> filteredList = new ArrayList<>();
        for (RfcEntry re : getRfcList()) {
            boolean matches = true;
            for (String s : searchTerms) {
                if (!re.getTitle().toLowerCase().contains(s.toLowerCase())) {
                    matches = false;
                }
            }

            if (matches) {
                filteredList.add(re);
            }
        }
        return filteredList;
    }

    public List<RfcEntry> filteredListByAuthor(String author) {
        List<RfcEntry> filteredList = new ArrayList<>();
        for (RfcEntry re : getRfcList()) {
            boolean matches = false;
            for (Author a : re.getAuthor()) {
                if (a.getName().toLowerCase().contains(author.toLowerCase())) {
                    matches = true;
                }
            }

            if (matches) {
                filteredList.add(re);
            }
        }
        return filteredList;
    }

    /**
     * @return the instance
     */
    public static XmlHelper getInstance() {
        if (instance == null) {
            instance = new XmlHelper();
        }
        return instance;
    }

    /**
     * @return the allAuthorNames
     */
    public List<String> getAllAuthorNames() {
        if (allAuthorNames == null) {
            // we want to sort by surname, then by initial so we need
            // a custom comparator
            Comparator<String> comparator = new Comparator<String>() {

                @Override
                public int compare(String s1, String s2) {
                    // guard against strings with trailing .
                    if (s1.endsWith(".")) {
                        s1 = s1.substring(0, s1.length() - 1);
                    }
                    if (s2.endsWith(".")) {
                        s2 = s2.substring(0, s2.length() - 1);
                    }
                    // find the last . which (ought to) delimit the initials 
                    // from the surname
                    int i1 = s1.lastIndexOf(".");
                    int i2 = s2.lastIndexOf(".");
                    String sn1, sn2;

                    if (i1 < 0) {
                        sn1 = s1.trim();
                    } else {
                        sn1 = s1.substring(i1 + 1).trim();
                    }
                    if (i2 < 0) {
                        sn2 = s2.trim();
                    } else {
                        sn2 = s2.substring(i2 + 1).trim();
                    }

                    // compare surnames
                    int res = sn1.compareToIgnoreCase(sn2);
                    // if surnames are the same compare initials
                    if (res == 0) {
                        return s1.trim().compareToIgnoreCase(s2.trim());
                    } else {
                        return res;
                    }
                }
            };
            SortedSet<String> set = new TreeSet<>(comparator);
            for (RfcEntry re : getRfcList()) {
                for (Author a : re.getAuthor()) {
                    set.add(a.getName().trim());
                }
            }

            allAuthorNames = new ArrayList<>(set);
        }

        return allAuthorNames;
    }

    public static void main(String[] args) {
        String s1 = "Audio-Video Transport Working Group";
        String s2 = "D. Aimmerman";
        Comparator<String> comparator = new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {
                // guard against strings with trailing .
                if (s1.endsWith(".")) {
                    s1 = s1.substring(0, s1.length() - 1);
                }
                if (s2.endsWith(".")) {
                    s2 = s2.substring(0, s2.length() - 1);
                }
                // find the last . which (ought to) delimit the initials 
                // from the surname
                int i1 = s1.lastIndexOf(".");
                int i2 = s2.lastIndexOf(".");
                String sn1, sn2;

                if (i1 < 0) {
                    sn1 = s1.trim();
                } else {
                    sn1 = s1.substring(i1 + 1).trim();
                }
                if (i2 < 0) {
                    sn2 = s2.trim();
                } else {
                    sn2 = s2.substring(i2 + 1).trim();
                }

                // compare surnames
                int res = sn1.compareToIgnoreCase(sn2);
                // if surnames are the same compare initials
                if (res == 0) {
                    return s1.trim().compareToIgnoreCase(s2.trim());
                } else {
                    return res;
                }
            }
        };
        int i = comparator.compare(s1, s2);
        System.out.println(i);
    }
}
