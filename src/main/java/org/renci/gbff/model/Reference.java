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

    public Reference(String index, String authors, String title, String journal, String pubmed, String remark) {
        super();
        this.index = index;
        this.authors = authors;
        this.title = title;
        this.journal = journal;
        this.pubmed = pubmed;
        this.remark = remark;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authors == null) ? 0 : authors.hashCode());
        result = prime * result + ((index == null) ? 0 : index.hashCode());
        result = prime * result + ((journal == null) ? 0 : journal.hashCode());
        result = prime * result + ((pubmed == null) ? 0 : pubmed.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reference other = (Reference) obj;
        if (authors == null) {
            if (other.authors != null)
                return false;
        } else if (!authors.equals(other.authors))
            return false;
        if (index == null) {
            if (other.index != null)
                return false;
        } else if (!index.equals(other.index))
            return false;
        if (journal == null) {
            if (other.journal != null)
                return false;
        } else if (!journal.equals(other.journal))
            return false;
        if (pubmed == null) {
            if (other.pubmed != null)
                return false;
        } else if (!pubmed.equals(other.pubmed))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

}
