package io.github.lilcatlily.bot.data.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE)
public class CloudinaryData
{
    private String name;
    private String key;
    private String secret;
}
