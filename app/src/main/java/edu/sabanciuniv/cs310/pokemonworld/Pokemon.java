package edu.sabanciuniv.cs310.pokemonworld;

/**
 * Created by Mert on 26.04.2016.
 */
public class Pokemon {

    private Integer pid;
    private String name;
    private Integer level;
    private Integer exp;
    private String exist;
    private String move;
    private String moveType;

    public Pokemon(Integer pid, String name, Integer exp, String exist, String move, String moveType){
        this.pid = pid;
        this.name = name;
        this.exp = exp;
        this.move = move;
        this.moveType = moveType;
        this.level = exp / 100;
        this.exist = exist;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
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

}
