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

import com.clothcat.rfcreader.entities.RFC_INDEX;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ssta
 */
public class Foo {

    public static void main(String[] args) throws Exception {
        IndexParser ip = new IndexParser();
        ip.parse();
        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("PUnit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        RFC_INDEX ri = new RFC_INDEX();
        ri.setId(1);
        ri.setAuthors("S. Crocker");
        ri.setIssueDate(new Date(1969, 3, 1));
        ri.setStatus("UNKNOWN");
        ri.setTitle("Host Software");
        em.persist(ri);
        em.getTransaction().commit();*/
    }
}
