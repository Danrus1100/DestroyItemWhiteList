package com.danrus.config.categories;

import com.danrus.config.ModConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.text.Text;

public class SimpleCategory {
    public static ConfigCategory get(){
        return ConfigCategory.createBuilder()
                .name(Text.translatable("diwl.category.general"))
                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("diwl.group.visibility"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("diwl.option.hide_while_bind_up"))
                                .binding(
                                        true,
                                        () -> ModConfig.get().hideWhileBindUp, // a getter to get the current value from
                                        newVal -> ModConfig.get().hideWhileBindUp = newVal
                                )
                                .description(OptionDescription.of(
                                        Text.translatable("diwl.option.hide_while_bind_up.description")
                                ))
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .build();
    }
}
