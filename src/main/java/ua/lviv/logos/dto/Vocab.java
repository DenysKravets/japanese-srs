package ua.lviv.logos.dto;

import java.util.Objects;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vocab {

    @Id
    private String id;
    @ManyToOne
    private User user;
    private Integer number;
    private String word;
    private Level level;
    private Boolean learned;
    private Timestamp nextDate;
    private Integer streak;



    public Vocab() {
    }

    public Vocab(User user, Integer number, String word, Level level, Boolean learned, Timestamp nextDate, Integer streak) {
        this.user = user;
        this.number = number;
        this.word = word;
        this.level = level;
        this.learned = learned;
        this.nextDate = nextDate;
        this.streak = streak;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Boolean isLearned() {
        return this.learned;
    }

    public Boolean getLearned() {
        return this.learned;
    }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }

    public Timestamp getNextDate() {
        return this.nextDate;
    }

    public void setNextDate(Timestamp nextDate) {
        this.nextDate = nextDate;
    }

    public Integer getStreak() {
        return this.streak;
    }

    public void setStreak(Integer streak) {
        this.streak = streak;
    }

    public Vocab id(String id) {
        setId(id);
        return this;
    }

    public Vocab user(User user) {
        setUser(user);
        return this;
    }

    public Vocab number(Integer number) {
        setNumber(number);
        return this;
    }

    public Vocab word(String word) {
        setWord(word);
        return this;
    }

    public Vocab level(Level level) {
        setLevel(level);
        return this;
    }

    public Vocab learned(Boolean learned) {
        setLearned(learned);
        return this;
    }

    public Vocab nextDate(Timestamp nextDate) {
        setNextDate(nextDate);
        return this;
    }

    public Vocab streak(Integer streak) {
        setStreak(streak);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Vocab)) {
            return false;
        }
        Vocab vocab = (Vocab) o;
        return Objects.equals(id, vocab.id) && Objects.equals(user, vocab.user) && Objects.equals(number, vocab.number) && Objects.equals(word, vocab.word) && Objects.equals(level, vocab.level) && Objects.equals(learned, vocab.learned) && Objects.equals(nextDate, vocab.nextDate) && Objects.equals(streak, vocab.streak);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, number, word, level, learned, nextDate, streak);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user='" + getUser() + "'" +
            ", number='" + getNumber() + "'" +
            ", word='" + getWord() + "'" +
            ", level='" + getLevel() + "'" +
            ", learned='" + isLearned() + "'" +
            ", nextDate='" + getNextDate() + "'" +
            ", streak='" + getStreak() + "'" +
            "}";
    }
   


    
}
