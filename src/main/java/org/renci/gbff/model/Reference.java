package org.renci.gbff.model;

import java.io.Serializable;

public class Reference implements Serializable {

    private static final long serialVersionUID = -4539091424412617923L;

    private String index;

    private String authors;

    private String title;

    private String journal;

    private String pubmed;

    private String remark;

    public Reference() {
        super();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getPubmed() {
        return pubmed;
    }

    public void setPubmed(String pubmed) {
        this.pubmed = pubmed;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return String.format("Reference [index=%s, authors=%s, title=%s, journal=%s, pubmed=%s, remark=%s]", index,
                authors, title, journal, pubmed, remark);
    }

}
