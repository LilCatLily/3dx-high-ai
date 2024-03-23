package io.github.lilcatlily.bot.roles;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum SportsRole implements Role
{
    Cheerleaders,
    Football,
    Basketball,
    Baseball,
    Soccer,
    Swim,
    Track_Field("Track_&_Field"),
    Volleyball;

    String name = super.toString();
    
    SportsRole(String name)
    {
        this.name = name;
    }
    
    public String asTeam()
    {
        return getText() + " Team";
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
