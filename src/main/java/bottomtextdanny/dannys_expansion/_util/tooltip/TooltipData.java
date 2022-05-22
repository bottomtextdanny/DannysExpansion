package bottomtextdanny.dannys_expansion._util.tooltip;

import bottomtextdanny.dannys_expansion._util.DescriptionUtil;
import net.minecraft.network.chat.*;

import java.util.function.Function;
import java.util.function.Supplier;

public class TooltipData<T> {
    public static final TooltipData<Supplier<String>> STATISTICS =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.statistics"));
    public static final TooltipData<Supplier<String>> DELAY =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.shoot_delay"));
    public static final TooltipData<Supplier<String>> DAMAGE =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.damage"));
    public static final TooltipData<Supplier<String>> RATE_OF_FIRE =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.rate_of_fire"));
    public static final TooltipData<Supplier<String>> ADDITIONAL_BULLET_SPEED =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.additional_bullet_speed"));
    public static final TooltipData<Supplier<String>> BULLET_SPEED_MULTIPLIER =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.bullet_speed_multiplier"));
    public static final TooltipData<Supplier<String>> RECOIL =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.recoil"));
    public static final TooltipData<Supplier<String>> MAX_RECOIL =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.max_recoil"));
    public static final TooltipData<Supplier<String>> SCOPING_ZOOM =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.scoping_zoom"));
    public static final TooltipData<Supplier<String>> SCOPING_SENSIBILITY_MULTIPLIER =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.scoping_sens_multiplier"));
    public static final TooltipData<Supplier<String>> SCOPING_MOVEMENT =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.scoping_movement"));
    public static final TooltipData<Supplier<String>> PELLET_COUNT =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.pellet_count"));
    public static final TooltipData<Supplier<String>> PELLET_DISPERSION =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.pellet_dispersion"));
    public static final TooltipData<Supplier<String>> ARROW_DAMAGE_FACTOR =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.arrow_damage_factor"));
    public static final TooltipData<Supplier<String>> ARROW_SPEED_FACTOR =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.arrow_speed_factor"));
    public static final TooltipData<Supplier<String>> NOCK_TIME =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.nock_time"));
    public static final TooltipData<Supplier<String>> NOCK_ZOOM =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.nock_zoom"));
    public static final TooltipData<Supplier<String>> NOCK_MOVEMENT_SPEED =
            new TooltipData<>(translatableConsumer("description.dannys_expansion.nock_movement_speed"));

    private Function<T, MutableComponent> messageGetter;

    protected TooltipData(Function<T, MutableComponent> message) {
        messageGetter = message;
    }

    public MutableComponent message(T input) {
        return messageGetter.apply(input);
    }

    public TooltipData<T> style(Style style) {
        return new TooltipData<>(u -> this.messageGetter.apply(u).withStyle(style));
    }

    public static Function<Supplier<Boolean>, MutableComponent> translatableIf(String key) {
        TranslatableComponent text = new TranslatableComponent(key);
        return (value) -> {
            if (value.get()) return new TextComponent(text.getString().replace(DescriptionUtil.INPUT_KEY, String.valueOf(value.get())));
            return null;
        };
    }

    public static Function<Supplier<String>, MutableComponent> translatableConsumer(String key) {
        TranslatableComponent text = new TranslatableComponent(key);
        return (value) -> {
            return new TextComponent(text.getString().replace(DescriptionUtil.INPUT_KEY, String.valueOf(value.get())));
        };
    }
}
