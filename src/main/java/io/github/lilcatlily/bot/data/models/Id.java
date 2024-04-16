package io.github.lilcatlily.bot.data.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE)
public class Id
{
    private int count;
}
