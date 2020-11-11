package com.fabbe50.corgis;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Config {
    private ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    private ForgeConfigSpec spec;

    public ForgeConfigSpec getSpec() {
        return spec;
    }

    private Corgis corgis;
    private NormalCorgi normalCorgi;
    private Fabbe50Corgi fabbe50Corgi;
    private BodyguardCorgi bodyguardCorgi;
    private BusinessCorgi businessCorgi;
    private MelonCorgi melonCorgi;
    private PirateCorgi pirateCorgi;
    private SpyCorgi spyCorgi;
    private SunglassesCorgi sunglassesCorgi;
    private AntiCorgi antiCorgi;
    private LoveCorgi loveCorgi;
    private RadioactiveCorgi radioactiveCorgi;
    private HeroCorgi heroCorgi;
    private NerdCorgi nerdCorgi;

    public Config() {
        corgis = new Corgis();
        normalCorgi = new NormalCorgi();
        fabbe50Corgi = new Fabbe50Corgi();
        bodyguardCorgi = new BodyguardCorgi();
        businessCorgi = new BusinessCorgi();
        melonCorgi = new MelonCorgi();
        pirateCorgi = new PirateCorgi();
        spyCorgi = new SpyCorgi();
        sunglassesCorgi = new SunglassesCorgi();
        antiCorgi = new AntiCorgi();
        loveCorgi = new LoveCorgi();
        radioactiveCorgi = new RadioactiveCorgi();
        heroCorgi = new HeroCorgi();
        nerdCorgi = new NerdCorgi();

        spec = builder.build();
    }

    public Corgis getCorgis() {
        return corgis;
    }
    public NormalCorgi getNormalCorgi() {
        return normalCorgi;
    }
    public Fabbe50Corgi getFabbe50Corgi() {
        return fabbe50Corgi;
    }
    public BodyguardCorgi getBodyguardCorgi() {
        return bodyguardCorgi;
    }
    public BusinessCorgi getBusinessCorgi() {
        return businessCorgi;
    }
    public MelonCorgi getMelonCorgi() {
        return melonCorgi;
    }
    public PirateCorgi getPirateCorgi() {
        return pirateCorgi;
    }
    public SpyCorgi getSpyCorgi() {
        return spyCorgi;
    }
    public SunglassesCorgi getSunglassesCorgi() {
        return sunglassesCorgi;
    }
    public AntiCorgi getAntiCorgi() {
        return antiCorgi;
    }
    public LoveCorgi getLoveCorgi() {
        return loveCorgi;
    }
    public RadioactiveCorgi getRadioactiveCorgi() {
        return radioactiveCorgi;
    }
    public HeroCorgi getHeroCorgi() {
        return heroCorgi;
    }
    public NerdCorgi getNerdCorgi() {
        return nerdCorgi;
    }

    public class Corgis {
        public final ForgeConfigSpec.IntValue min;
        public final ForgeConfigSpec.IntValue max;
        public final ForgeConfigSpec.IntValue weight;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> biomeCategories;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> biomes;

        public Corgis() {
            builder.push("spawn chance");
            builder.comment("Configure spawn weight and group size.");
            min = builder.defineInRange("min", 3, 0, 64);
            max = builder.defineInRange("max", 8, 0, 64);
            weight = builder.defineInRange("weight", 6, 0, 100);
            builder.pop();
            builder.push("spawn biomes");
            biomeCategories = builder.defineList("biome categories", Collections.singletonList(Biome.Category.PLAINS.getName()), o -> Lists.newArrayList(Biome.Category.values()).contains(Biome.Category.byName(String.valueOf(o).toLowerCase(Locale.ROOT))));
            biomes = builder.defineList("biomes (resourcelocation)", Collections.emptyList(), o -> ForgeRegistries.BIOMES.containsKey(new ResourceLocation(String.valueOf(o))));
            builder.pop();
        }
    }

    public class NormalCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public NormalCorgi() {
            builder.push("normalCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class Fabbe50Corgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public Fabbe50Corgi() {
            builder.push("fabbe50Corgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class BodyguardCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public BodyguardCorgi() {
            builder.push("bodyguardCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class BusinessCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public BusinessCorgi() {
            builder.push("businessCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class MelonCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;
        private final ForgeConfigSpec.BooleanValue shouldBreakMelons;

        public MelonCorgi() {
            builder.push("melonCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);
            shouldBreakMelons = builder.comment("Should the Melon Corgi break melons?").define("Break", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }

        public boolean getShouldBreakMelons() {
            return shouldBreakMelons.get();
        }
    }

    public class PirateCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public PirateCorgi() {
            builder.push("pirateCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class SpyCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public SpyCorgi() {
            builder.push("spyCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class SunglassesCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public SunglassesCorgi() {
            builder.push("sunglassesCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class AntiCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public AntiCorgi() {
            builder.push("antiCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class LoveCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;
        private final ForgeConfigSpec.IntValue effectRange;
        private final ForgeConfigSpec.BooleanValue particles;

        public LoveCorgi() {
            builder.push("loveCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);
            effectRange = builder.comment("How long should the range be? (radius)").defineInRange("Range", 5, 0, 30);
            particles = builder.comment("Is the particle effect active?").define("Particles", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }

        public int getEffectRange() {
            return effectRange.get();
        }

        public boolean getParticleEffect() {
            return particles.get();
        }
    }

    public class RadioactiveCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;
        private final ForgeConfigSpec.IntValue effectRange;
        private final ForgeConfigSpec.BooleanValue particles;

        public RadioactiveCorgi() {
            builder.push("radioactiveCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);
            effectRange = builder.comment("How long should the range be? (radius)").defineInRange("Range", 5, 0, 30);
            particles = builder.comment("Is the particle effect active?").define("Particles", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }

        public int getEffectRange() {
            return effectRange.get();
        }

        public boolean getParticleEffect() {
            return particles.get();
        }
    }

    public class HeroCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public HeroCorgi() {
            builder.push("heroCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }

    public class NerdCorgi {
        private final ForgeConfigSpec.BooleanValue traitActive;

        public NerdCorgi() {
            builder.push("nerdCorgi");

            traitActive = builder.comment("Is the trait active?").define("Active", true);

            builder.pop();
        }

        public boolean getTraitActive() {
            return traitActive.get();
        }
    }
}
