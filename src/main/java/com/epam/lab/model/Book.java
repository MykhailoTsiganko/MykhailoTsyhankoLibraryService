package com.epam.lab.model;


import java.io.Serializable;


public class Book implements Serializable{

    private String name;


    private String authorName;


    private String genre;

    public Book(String name, String authorName, String genre) {
        this.name = name;
        this.authorName = authorName;
        this.genre = genre;
    }

    public Book(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getName() != null ? !getName().equals(book.getName()) : book.getName() != null) return false;
        if (getAuthorName() != null ? !getAuthorName().equals(book.getAuthorName()) : book.getAuthorName() != null)
            return false;
        return getGenre() != null ? getGenre().equals(book.getGenre()) : book.getGenre() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAuthorName() != null ? getAuthorName().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
