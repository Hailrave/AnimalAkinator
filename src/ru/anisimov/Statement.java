package ru.anisimov;

import java.io.Serializable;
import java.util.Objects;

public class Statement implements Serializable {
    public String value;
    public Statement yesAnswer;
    public Statement noAnswer;

    public Statement(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, yesAnswer, noAnswer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement = (Statement) o;
        return Objects.equals(value, statement.value) &&
                Objects.equals(yesAnswer, statement.yesAnswer) &&
                Objects.equals(noAnswer, statement.noAnswer);
    }
}
