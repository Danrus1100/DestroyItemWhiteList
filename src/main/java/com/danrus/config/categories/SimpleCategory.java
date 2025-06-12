package com.danrus.config.categories;

import com.danrus.config.ModConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.SliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.text.Text;

public class SimpleCategory {
    public static ConfigCategory getCategory(){
        return ConfigCategory.createBuilder()
                .name(Text.translatable("diwl.category.general"))
                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("diwl.group.visibility"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("diwl.option.hide_whitelisted_slots"))
                                .binding(
                                        false,
                                        () -> ModConfig.get().hideWhiteListedSlots, // a getter to getCategory the current value from
                                        newVal -> ModConfig.get().hideWhiteListedSlots = newVal
                                )
                                .description(OptionDescription.of(
                                        Text.translatable("diwl.option.hide_whitelisted_slots.description")
                                ))
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("diwl.option.hide_whitelisted_items"))
                                .binding(
                                        false,
                                        () -> ModConfig.get().hideWhiteListedItems, // a getter to getCategory the current value from
                                        newVal -> ModConfig.get().hideWhiteListedItems = newVal
                                )
                                .description(OptionDescription.of(
                                        Text.translatable("diwl.option.hide_whitelisted_items.description")
                                ))
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("diwl.option.show_when_bind_pressed"))
                                .binding(
                                        false,
                                        () -> ModConfig.get().showWhenBindPressed, // a getter to getCategory the current value from
                                        newVal -> ModConfig.get().showWhenBindPressed = newVal
                                )
                                .description(OptionDescription.of(
                                        Text.translatable("diwl.option.show_when_bind_pressed.description")
                                ))
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("diwl.option.show_when_di_slot_hovered"))
                                .binding(
                                        false,
                                        () -> ModConfig.get().showWhenDIHovered, // a getter to getCategory the current value from
                                        newVal -> ModConfig.get().showWhenDIHovered = newVal
                                )
                                .description(OptionDescription.of(
                                        Text.translatable("diwl.option.show_when_di_slot_hovered.description")
                                ))
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("diwl.group.behavior"))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("diwl.option.clicks_for_force_delete"))
                                .binding(
                                        5,
                                        () -> ModConfig.get().clicksForForceDelete, // a getter to getCategory the current value from
                                        newVal -> ModConfig.get().clicksForForceDelete = newVal
                                )
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(1, 10)
                                        .step(1)
                                        .formatValue(val -> Text.translatable("diwl.option.clicks_for_force_delete."+val)))
                                .addListener(((option, event) -> {
                                    ModConfig.get().clicksForForceDelete = option.pendingValue();
                                    ModConfig.save();
                                }))
                                .description(OptionDescription.of(
                                        Text.translatable("diwl.option.clicks_for_force_delete.description")
                                ))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("diwl.option.force_delete_clicks_delay"))
                                .binding(
                                        5,
                                        () -> ModConfig.get().forceDeleteClickDelay, // a getter to getCategory the current value from
                                        newVal -> ModConfig.get().forceDeleteClickDelay = newVal
                                )
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(1, 20)
                                        .step(1)
                                        .formatValue(val -> Text.translatable("diwl.option.force_delete_clicks_delay."+val)))
                                .addListener(((option, event) -> {
                                    ModConfig.get().forceDeleteClickDelay = option.pendingValue();
                                    ModConfig.save();
                                }))
                                .description(OptionDescription.of(
                                        Text.translatable("diwl.option.force_delete_clicks_delay.description")
                                ))
                                .build())
                        .build())
                .build();
    }
}
