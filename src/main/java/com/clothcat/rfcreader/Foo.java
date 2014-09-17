/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
