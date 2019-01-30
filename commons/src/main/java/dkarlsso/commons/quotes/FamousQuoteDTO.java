package dkarlsso.commons.quotes;

import java.io.Serializable;
import java.util.Objects;

public class FamousQuoteDTO implements Serializable {

    private static final long serialVersionUID = -4415920372743853048L;

    private String quote;

    private String author;

    private String category;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FamousQuoteDTO that = (FamousQuoteDTO) o;
        return Objects.equals(quote, that.quote) &&
                Objects.equals(author, that.author) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quote, author, category);
    }
}
