package edu.sabanciuniv.cs310.pokemonworld;

/**
 * Created by Mert on 26.04.2016.
 */
public class Pokemon {

    private String name;
    private Integer level;
    private Integer exp;
    private String exist;

    public Pokemon(String name, Integer exp, String exist){
        this.name = name;
        this.exp = exp;
        this.level = exp / 100;
        this.exist = exist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String isOwn() {
        return exist;
    }

    public void setOwn(String exist) {
        this.exist = exist;
    }
}
