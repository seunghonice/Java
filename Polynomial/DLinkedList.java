/*
 * (C) 2016 CSE2010 HW #2
 * 
 * Modify and/or methods (fields) if necessary ...
 */

import java.util.*;

class DNode<T> {
    private T info;
    private DNode<T> next;
    private DNode<T> prev;

    DNode(T info, DNode<T> prev, DNode<T> next) {
        this.info = info;
        this.prev = prev;
        this.next = next;
    }

    T getInfo() {
        return info;
    }

    void setInfo(T info) {
        this.info = info;
    }

    DNode<T> getNext() {
        return next;
    }

    void setNext(DNode<T> n) {
        next = n;
    }

    DNode<T> getPrev() {
        return prev;
    }

    void setPrev(DNode<T> p) {
        prev = p;
    }
}

public class DLinkedList<T> {
    private DNode<T> header;
    private DNode<T> trailer;
    private int size;

    public DLinkedList() {
        header = new DNode<T>(null, null, null);
        trailer = new DNode<T>(null, header, null);
        header.setNext(trailer);
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    // Add new node n at the front
    public void addFirst(DNode<T> n) {
        addAfter(header, n);
    }

    // Add new node n at the rear
    public void addLast(DNode<T> n) {
        addBefore(trailer, n);
    }

    // Remove a node at the front
    public void removeFirst() {
        remove(header.getNext());
    }

    // Remove a node at the front
    public void removeLast() {
        remove(trailer.getPrev());
    }

    // Add new node n before node p
    public void addBefore(DNode<T> p, DNode<T> n) {
       
		/*
         * Your code goes here
         */
        n.setPrev(p.getPrev());
        p.getPrev().setNext(n);
        n.setNext(p);
        p.setPrev(n);
        size++;
    }

    // Add new node n after node p
    public void addAfter(DNode<T> p, DNode<T> n) {
       
		/*
         * Your code goes here
         */
        n.setNext(p.getNext());
        p.getNext().setPrev(n);
        n.setPrev(p);
        p.setNext(n);
        size++;
    }

    // Remove a node p
    public void remove(DNode<T> p) {
        p.getPrev().setNext(p.getNext());
        p.getNext().setPrev(p.getPrev());
        p.setPrev(null);
        p.setNext(null);
        size--;
    }

    public DNode<T> getFirstNode() {
        if (size == 0) 
            return null;
        else
            return header.getNext();
    }

    public DNode<T> getNextNode(DNode<T> cur) {
        /*
         * Your code goes here
         */
        return cur.getNext();
    }

    // Add methods if necessary
    public DNode<T> getTrailer() {
    	return trailer;
    }
    public DNode<T> getHeader() {
    	return header;
    }
}

