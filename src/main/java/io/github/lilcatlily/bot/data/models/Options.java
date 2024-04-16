package io.github.lilcatlily.bot.data.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE)
public class Options
{
    private long positionChangeCooldown;
    private String idRequestChannelId;
    private String clubCommandChannelId;
    private String allCommandChannelId;
}
