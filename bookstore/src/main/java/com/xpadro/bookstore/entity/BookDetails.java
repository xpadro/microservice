package com.xpadro.bookstore.entity;

public class BookDetails {
    private String title;
    private int year;
    private String author;
    private String isbn;

    public BookDetails() {
    }

    public BookDetails(String title, int year, String author, String isbn) {
        this.title = title;
        this.year = year;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
