package it.jpokebattle.model.player;

import it.jpokebattle.model.team.Team;

public abstract class Player {

    private String name;
    private Team team;


    public Player(String name) {
        this.name = name;
        this.team = new Team();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
