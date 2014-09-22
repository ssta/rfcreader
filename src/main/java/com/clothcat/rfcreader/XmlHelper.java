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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
     * Filters the list of RfcEntry by searching the title (case-inseitively)
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

    /**
     * @return the instance
     */
    public static XmlHelper getInstance() {
        if (instance == null) {
            instance = new XmlHelper();
        }
        return instance;
    }

}
