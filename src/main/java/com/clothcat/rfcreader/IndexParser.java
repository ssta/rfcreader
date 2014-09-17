package com.clothcat.rfcreader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses and persists the index file
 *
 * @author ssta
 */
public class IndexParser {

    boolean in_preamble = true;

    public void parse() throws Exception {
        // TODO -- don't throw anything -- catch and handle gracefully
        File f = new File("rfc-index.txt");
        Scanner sc = new Scanner(f);
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
            parseChunk(chunk);
        }
    }

    private void parseChunk(String chunk) throws ParseException {
        Integer rfcnum;
        String title = null;
        String authors = null;
        String date = null;
        String status = null;
        String format = null;
        String obsoletes = null;
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
                authors = chunk.substring(0, pos2).trim();
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
                    obsoletes = chunk.substring(1, pos4);
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

            System.out.println("" + rfcnum + "::" + title + "::" + authors
                    + "::" + date + "::" + format + "::" + obsoletedBy + "::"
                    + obsoletes + "::" + updatedBy + "::" + updates + "\n" + chunk + "\n");
        }
    }

    public static void main(String[] args) throws Exception {
        IndexParser ip = new IndexParser();
        ip.parse();
    }
}
