package io.github.lilcatlily.bot.data.models;

import lombok.*;
import net.dv8tion.jda.api.entities.User;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE)
public class Generated
{
    @Builder.Default
    private Map<String, UserData> data = new HashMap<String, Generated.UserData>();

    public void addUserData(String id, UserData userData)
    {
        data.put(id, userData);
    }

    public boolean isRegistered(User user)
    {
        return data.containsKey(user.getId());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(access = AccessLevel.PACKAGE)
    public static class UserData
    {
        private long    receivedIdEpoch;
        private String  username;
        private boolean faculty;
    }
}
