package io.github.lilcatlily.bot.roles;

public enum ClubRoles implements Role
{
    Band,
    Anime,
    Naturist,
    Gaming,
    Gym,
    Dance,
    Drama,
    Debate,
    Auto_Racing,
    Café_Worker;
    
    public String asClub()
    {
        if(this != Café_Worker)
            return getText() + " Club";
        else
            return getText();
    }
}
