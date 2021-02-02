package net.miniscape.data;

public interface Datasource {
    void openConnection();

    void close();
}