package io.github.rubiksimplosion.minecrafttas.command;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.rubiksimplosion.minecrafttas.script.ScriptManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.command.ServerCommandSource;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class FileCompletion {
    public static SuggestionProvider<ServerCommandSource> fileList() {
        return (ctx, builder) -> getSuggestionsBuilder(builder, Arrays.stream(ScriptManager.scriptDirectory.listFiles())
                .map(File::getName)
                .filter(e -> e.endsWith(".script"))
                .map(e -> e.replace(".script", ""))
                .collect(Collectors.toList()));
    }

    private static CompletableFuture<Suggestions> getSuggestionsBuilder(SuggestionsBuilder builder, List<String> list) {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);

        if(list.isEmpty()) { // If the list is empty then return no suggestions
            return Suggestions.empty(); // No suggestions
        }

        for (String str : list) { // Iterate through the supplied list
            if (str.toLowerCase(Locale.ROOT).startsWith(remaining)) {
                builder.suggest(str); // Add every single entry to suggestions list.
            }
        }
        return builder.buildFuture(); // Create the CompletableFuture containing all the suggestions
    }
}
