package controller;

public class UserPokemon {
    int stt, score;
    String name;

    public UserPokemon() {
    }

    public UserPokemon(int stt, String name, int score) {
        this.stt = stt;
        this.score = score;
        this.name = name;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserPokemon{" +
                "stt=" + stt +
                ", score=" + score +
                ", name='" + name + '\'' +
                '}';
    }
}
