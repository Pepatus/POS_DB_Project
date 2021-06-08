package bll;

public class Season {

    public Season(int id, String indentifier) {
        this.id = id;
        this.indentifier = indentifier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndentifier() {
        return indentifier;
    }

    public void setIndentifier(String indentifier) {
        this.indentifier = indentifier;
    }

    @Override
    public String toString() {
        return this.indentifier;
    }

    private int id;
    private String indentifier;
}
