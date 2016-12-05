package com.algonquincollege.doir0008.dungeonaccountant;

import java.util.ArrayList;

/**
 * Model a game character
 *
 * @author Ryan Doiron (doir0008@algonquinlive.com)
 */

public class GameCharacter {

    protected String charClass;
    protected int skill1Value;
    protected int skill2Value;
    protected int skill3Value;
    protected String skill1;
    protected String skill2;
    protected String skill3;

    protected ArrayList<String> validClasses = new ArrayList<String>();
    protected ArrayList<String> validSkills = new ArrayList<String>();

    protected void setCharClass() {
        validClasses.add("Warrior");
        validClasses.add("Rogue");
        validClasses.add("Mage");
        validClasses.add("Druid");
    }

    protected void setSkills() {
        validSkills.add("Martial Prowess");
        validSkills.add("Superior Gear");
        validSkills.add("Appraise");
        validSkills.add("Scouting");
        validSkills.add("Guild Contacts");
        validSkills.add("Smooth Talk");
        validSkills.add("Superior Casting");
        validSkills.add("Chronoscrying");
        validSkills.add("Alchemy");
        validSkills.add("Forage");
        validSkills.add("Combat Healing");
        validSkills.add("Temple Contacts");
    }

    public GameCharacter() {
        setCharClass();
        setSkills();
        charClass = "Warrior";
        skill1 = "";
        skill2 = "";
        skill3 = "";
        skill1Value = 1;
        skill2Value = 1;
        skill3Value = 1;
    }

    public GameCharacter(
            String charClass,
            int skill1Value,
            int skill2Value,
            int skill3Value,
            String skill1,
            String skill2,
            String skill3 ) {
        setCharClass();
        setSkills();
        this.charClass = charClass;
        this.skill1Value = skill1Value;
        this.skill2Value = skill2Value;
        this.skill3Value = skill3Value;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
    }

    // getters
    public String getCharClass() { return charClass; }
    public int getSkill1Value() { return skill1Value; }
    public int getSkill2Value() { return skill2Value; }
    public int getSkill3Value() { return skill3Value; }
    public String getSkill1Name() { return skill1; }
    public String getSkill2Name() { return skill2; }
    public String getSkill3Name() { return skill3; }

    //setters

//  Replaced with updated setter below
//    public void setCharClass(String charClass) {
//        this.charClass = charClass;
//    }

    public boolean setCharClass(String charClass) {
        for(String s : validClasses)
        {
            if(charClass.equalsIgnoreCase(s))
            {
                this.charClass = s;
                return true;
            }
        }
        return false;
    }

    public void setSkill1Value(int skill1Value) {
        this.skill1Value = skill1Value;
    }

    public void setSkill2Value(int skill2Value) {
        this.skill2Value = skill2Value;
    }

    public void setSkill3Value(int skill3Value) {
        this.skill3Value = skill3Value;
    }

    public boolean setSkill1(String skill1) {
        for(String s : validSkills) {
            if(skill1.equalsIgnoreCase(s)) {
                this.skill1 = s;
            }
        }
        return false;
    }

    public boolean setSkill2(String skill2) {
        for(String s : validSkills) {
            if(skill2.equalsIgnoreCase(s)) {
                this.skill2 = s;
            }
        }
        return false;
    }

    public boolean setSkill3(String skill3) {
        for(String s : validSkills) {
            if(skill3.equalsIgnoreCase(s)) {
                this.skill3 = s;
            }
        }
        return false;
    }

}
