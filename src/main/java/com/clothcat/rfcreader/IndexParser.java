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

import com.clothcat.rfcreader.entities.AUTHORS;
import com.clothcat.rfcreader.entities.OBSOLETES;
import com.clothcat.rfcreader.entities.RFC_INDEX;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Parses and persists the index file
 *
 * @author ssta
 */
public class IndexParser {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PUnit");
    EntityManager em = emf.createEntityManager();
    boolean in_preamble = true;

    public void parse() throws Exception {
        // TODO -- don't throw anything -- catch and handle gracefully
        File f = new File("rfc-index.txt");
        Scanner sc = new Scanner(f);
        // whilst developing.testing only do the first few
        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (in_preamble) {
                if (line.trim().startsWith("0001")) {
                    in_preamble = false;
                } else {
                    continue;
                }
            }
            // not in preamble...
            String chunk = "";
            while (!line.trim().equals("") && sc.hasNextLine()) {
                chunk = chunk + " " + line;
                line = sc.nextLine().trim();
            }
            i++;
            if (i > 10000) {
                break;
            }
            parseChunk(chunk);
        }
    }

    /* Horrible and evil "by hand" parsing...works pretty well though :) */
    private void parseChunk(String chunk) {

        Integer rfcnum = null;
        String title = null;
        String authorstring = null;
        List<String> authors = new ArrayList<>();
        String date = null;
        String status = null;
        String format = null;
        String obsoletesstring;
        List<String> obsoletes = new ArrayList<>();
        String obsoletedBy = null;
        String updatedBy = null;
        String updates = null;
        String also = null;

        chunk = chunk.trim();
        // first 4 characters are the RFC number.
        rfcnum = Integer.parseInt(chunk.substring(0, 4));
        chunk = chunk.substring(4).trim();
        if (chunk.equalsIgnoreCase("NOT ISSUED.")) {
            // special case
            System.out.println("" + rfcnum + "::" + chunk);
        } else {
            // the title is all text up to the first full stop.
            int pos1 = chunk.indexOf(". ");
            title = chunk.substring(0, pos1 + 1);
            chunk = chunk.substring(pos1 + 1);
            Pattern datePattern = Pattern.compile("(January|February|March|April|May|June|July|August|September|October|November|December)\\s([0-9]{4}|[0-9] [0-9]{4})\\.");
            Matcher m = datePattern.matcher(chunk);
            if (m.find()) {
                int pos2 = m.start();
                authorstring = chunk.substring(0, pos2).trim();
                if (authorstring.contains(",")) {
                    String[] s = authorstring.split(",");
                    for (String a : s) {
                        authors.add(a);
                    }
                } else {
                    authors.add(authorstring);
                }
                int pos3 = m.end();
                date = chunk.substring(pos2, pos3).trim();
                chunk = chunk.substring(pos3).trim();
            }
            while (chunk.startsWith("(")) {
                if (chunk.substring(1).toUpperCase().startsWith("FORMAT")) {
                    int pos4 = chunk.indexOf(")");
                    format = chunk.substring(1, pos4);
                    chunk = chunk.substring(pos4 + 1).trim();
                } else if (chunk.substring(1).toUpperCase().startsWith("STATUS")) {
                    int pos4 = chunk.indexOf(")");
                    status = chunk.substring(1, pos4);
                    chunk = chunk.substring(pos4 + 1).trim();
                } else if (chunk.substring(1).toUpperCase().startsWith("OBSOLETED BY")) {
                    int pos4 = chunk.indexOf(")");
                    obsoletedBy = chunk.substring(1, pos4);
                    chunk = chunk.substring(pos4 + 1).trim();
                } else if (chunk.substring(1).toUpperCase().startsWith("OBSOLETES")) {
                    int pos4 = chunk.indexOf(")");
                    obsoletesstring = chunk.substring(1, pos4);
                    obsoletesstring = obsoletesstring.replace("Obsoletes", "").trim();
                    if (obsoletesstring.contains(",")) {
                        String[] s = obsoletesstring.split(",");
                        for (String o : s) {
                            obsoletes.add(o);
                        }
                    } else {
                        obsoletes.add(obsoletesstring);
                    }
                    chunk = chunk.substring(pos4 + 1).trim();
                } else if (chunk.substring(1).toUpperCase().startsWith("UPDATED BY")) {
                    int pos4 = chunk.indexOf(")");
                    updatedBy = chunk.substring(1, pos4);
                    chunk = chunk.substring(pos4 + 1).trim();
                } else if (chunk.substring(1).toUpperCase().startsWith("UPDATES")) {
                    int pos4 = chunk.indexOf(")");
                    updates = chunk.substring(1, pos4);
                    chunk = chunk.substring(pos4 + 1).trim();
                } else if (chunk.substring(1).toUpperCase().startsWith("ALSO")) {
                    int pos4 = chunk.indexOf(")");
                    also = chunk.substring(1, pos4);
                    chunk = chunk.substring(pos4 + 1).trim();
                }
            }
            em.getTransaction().begin();
            RFC_INDEX ri = new RFC_INDEX();
            ri.setId(rfcnum);
            for (String s : authors) {
                AUTHORS a = new AUTHORS();
                a.setRfcId(rfcnum);
                a.setAuthor(s.trim());
                em.persist(a);
            }
            ri.setIssueDate(date);
            ri.setStatus(status);
            ri.setTitle(title);
            ri.setAlso(also);
            ri.setObsoletedBy(obsoletedBy);
            for (String s : obsoletes) {
                if (s.contains("RFC")) {
                    // since not all Obsoletes are RFCs...
                    OBSOLETES o = new OBSOLETES();
                    o.setRfcId(rfcnum);
                    o.setObsoletesId(Long.parseLong(s.replace("RFC", "").trim()));
                    em.persist(o);
                }
            }
            ri.setUpdatedBy(updatedBy);
            ri.setUpdates(updates);
            em.persist(ri);
            em.getTransaction().commit();

            System.out.println("" + rfcnum + "::" + title + "::" + authorstring
                    + "::" + date + "::" + format + "::" + obsoletedBy + "::"
                    + obsoletes + "::" + updatedBy + "::" + updates + "\n" + chunk + "\n");
        }
    }
    /* temp main for testing */

    public static void main(String[] args) throws Exception {
        IndexParser ip = new IndexParser();
        ip.parse();
    }
}
