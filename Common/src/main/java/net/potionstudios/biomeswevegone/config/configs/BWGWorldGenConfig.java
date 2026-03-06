package net.potionstudios.biomeswevegone.config.configs;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.serialization.codec.CommentedCodec;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.config.ConfigUtils;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

public record BWGWorldGenConfig(Map<ResourceKey<Biome>, Boolean> enabledBiomes, int regionWeight) {

    private static final Path PATH = PlatformHandler.PLATFORM_HANDLER.configPath().resolve("world_generation.json5");

    @NotNull
    public static Supplier<BWGWorldGenConfig> INSTANCE = Suppliers.memoize(BWGWorldGenConfig::getOrCreateConfigFromDisk);

    private static final Codec<BWGWorldGenConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    CommentedCodec.of(Codec.unboundedMap(ResourceKey.codec(Registries.BIOME), Codec.BOOL), "enabled_biomes", "Which biomes are enabled, if disabled the biome will default to its vanilla counterpart for the given region").orElse(getDefaultBiomes()).forGetter(BWGWorldGenConfig::enabledBiomes),
                    CommentedCodec.of(Codec.INT, "region_weight", "How much each BWG region weighs. This weight applies to all 3 BWG Regions").orElse(8).forGetter(BWGWorldGenConfig::regionWeight)
            ).apply(instance, BWGWorldGenConfig::new)
    );

    private static BWGWorldGenConfig createDefault() {
        return new BWGWorldGenConfig(getDefaultBiomes(), 8);
    }

    private static @NotNull Object2BooleanMap<ResourceKey<Biome>> getDefaultBiomes() {
        Object2BooleanMap<ResourceKey<Biome>> enabledBiomes = new Object2BooleanOpenHashMap<>();
        for (ResourceKey<Biome> biomeResourceKey : BWGBiomes.BIOME_FACTORIES.keySet()) {
            enabledBiomes.put(biomeResourceKey, true);
        }

        enabledBiomes.replace(BWGBiomes.ERODED_BOREALIS, false);
        return enabledBiomes;
    }

    private static BWGWorldGenConfig getOrCreateConfigFromDisk() {
        return ConfigUtils.loadConfig(PATH, CODEC, createDefault());
    }
}
