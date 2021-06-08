package bll;

public class Activity {
    private int id;
    private int idSeason;
    private String indentifier;

    public Activity(int id, int idSeason, String indentifier) {
        this.id = id;
        this.idSeason = idSeason;
        this.indentifier = indentifier;
    }

    @Override
    public String toString() {
        return indentifier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSeason() {
        return idSeason;
    }

    public void setIdSeason(int idSeason) {
        this.idSeason = idSeason;
    }

    public String getIndentifier() {
        return indentifier;
    }

    public void setIndentifier(String indentifier) {
        this.indentifier = indentifier;
    }
}
