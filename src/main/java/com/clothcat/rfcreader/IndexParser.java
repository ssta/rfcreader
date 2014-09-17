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
        String title;
        String authors = null;
        String date = null;
        String status;

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
            Pattern datePattern = Pattern.compile("(January|February|March|April|May|June|July|August|September|October|November|December)\\s([0-9]{4}|[0-9] [0-9]{4})");
            Matcher m = datePattern.matcher(chunk);
            if (m.find()) {
                int pos2 = m.start();
                authors = chunk.substring(0, pos2);
                date = chunk.substring(pos2, m.end());
            }
            System.out.println("" + rfcnum + "::" + title + "::" + authors + "::" + date + "\n" + chunk + "\n");
        }
    }

    public static void main(String[] args) throws Exception {
        IndexParser ip = new IndexParser();
        ip.parse();
    }
}
