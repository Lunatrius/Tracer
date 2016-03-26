package com.github.lunatrius.tracer.handler;

import com.github.lunatrius.tracer.Tracer;
import com.github.lunatrius.tracer.reference.Names;
import com.github.lunatrius.tracer.reference.Reference;
import com.github.lunatrius.tracer.trace.registry.TraceRegistry;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigurationHandler {
    public static final ConfigurationHandler INSTANCE = new ConfigurationHandler();

    public static final String VERSION = "1";

    public static Configuration configuration;

    public static final int DEFAULT_COLOR_RED = 255;
    public static final int DEFAULT_COLOR_GREEN = 0;
    public static final int DEFAULT_COLOR_BLUE = 0;
    public static final int DEFAULT_COLOR_ALPHA = 255;
    public static final int DEFAULT_TTL = 2;
    public static final double DEFAULT_THICKNESS = 1.5;
    public static final double DEFAULT_OFFSET_Y = 0.005;

    public static void init(final File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile, VERSION, true);
            loadConfiguration();
        }
    }

    public static void loadConfiguration() {
        loadConfigurationRegistry();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    private static void loadConfigurationRegistry() {
        TraceRegistry.INSTANCE.clear();

        final ConfigCategory category = configuration.getCategory(Names.Config.Category.REGISTRY);
        for (final ConfigCategory categoryRenderInfo : category.getChildren()) {
            registerTraceRenderInformation(categoryRenderInfo.getName());
        }
    }

    public static void registerTraceRenderInformation(final String categoryName) {
        final String categoryQualifiedName = Names.Config.Category.REGISTRY + "." + categoryName.replace(".", "_");
        registerTraceRenderInformation(categoryName, categoryQualifiedName);
    }

    private static void registerTraceRenderInformation(final String categoryName, final String categoryQualifiedName) {
        final Property propName = configuration.get(categoryQualifiedName, Names.Config.NAME, categoryName, Names.Config.NAME_DESC);
        propName.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.NAME);
        final String name = propName.getString();

        final Property propColorRed = configuration.get(categoryQualifiedName, Names.Config.COLOR_RED, DEFAULT_COLOR_RED, Names.Config.COLOR_RED_DESC, 0, 255);
        propColorRed.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.COLOR_RED);
        final int red = propColorRed.getInt(DEFAULT_COLOR_RED);

        final Property propColorGreen = configuration.get(categoryQualifiedName, Names.Config.COLOR_GREEN, DEFAULT_COLOR_GREEN, Names.Config.COLOR_GREEN_DESC, 0, 255);
        propColorGreen.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.COLOR_GREEN);
        final int green = propColorGreen.getInt(DEFAULT_COLOR_GREEN);

        final Property propColorBlue = configuration.get(categoryQualifiedName, Names.Config.COLOR_BLUE, DEFAULT_COLOR_BLUE, Names.Config.COLOR_BLUE_DESC, 0, 255);
        propColorBlue.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.COLOR_BLUE);
        final int blue = propColorBlue.getInt(DEFAULT_COLOR_BLUE);

        final Property propColorAlpha = configuration.get(categoryQualifiedName, Names.Config.COLOR_ALPHA, DEFAULT_COLOR_ALPHA, Names.Config.COLOR_ALPHA_DESC, 0, 255);
        propColorAlpha.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.COLOR_ALPHA);
        final int alpha = propColorAlpha.getInt(DEFAULT_COLOR_ALPHA);

        final Property propTTL = configuration.get(categoryQualifiedName, Names.Config.TTL, DEFAULT_TTL, Names.Config.TTL_DESC, 1, 120);
        propTTL.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.TTL);
        final int ttl = propTTL.getInt(DEFAULT_TTL) * 20;

        final Property propThickness = configuration.get(categoryQualifiedName, Names.Config.THICKNESS, DEFAULT_THICKNESS, Names.Config.THICKNESS_DESC, 1.0, 10.0);
        propThickness.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.THICKNESS);
        final double thickness = propThickness.getDouble(DEFAULT_THICKNESS);

        final Property propOffsetY = configuration.get(categoryQualifiedName, Names.Config.OFFSET_Y, DEFAULT_OFFSET_Y, Names.Config.OFFSET_Y_DESC, -1.0, +1.0);
        propOffsetY.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.OFFSET_Y);
        final double offsetY = propOffsetY.getDouble(DEFAULT_OFFSET_Y);

        setCategoryPropertyOrder(categoryQualifiedName, Names.Config.NAME, Names.Config.COLOR_RED, Names.Config.COLOR_GREEN, Names.Config.COLOR_BLUE, Names.Config.COLOR_ALPHA, Names.Config.TTL, Names.Config.THICKNESS, Names.Config.OFFSET_Y);

        Tracer.proxy.setConfigEntryClassSlider(propColorRed, propColorGreen, propColorBlue, propColorAlpha);
        Tracer.proxy.setConfigEntryClassSlider(propTTL, propThickness, propOffsetY);

        TraceRegistry.INSTANCE.register(name, red, green, blue, alpha, thickness, ttl, offsetY);
    }

    private static void setCategoryPropertyOrder(final String category, final String... names) {
        final ArrayList<String> order = new ArrayList<String>();
        Collections.addAll(order, names);
        configuration.setCategoryPropertyOrder(category, order);
    }

    public static void unregisterTraceRenderInformation(final String name) {
        final ConfigCategory category = configuration.getCategory(Names.Config.Category.REGISTRY);
        for (final ConfigCategory categoryRenderInfo : category.getChildren()) {
            if (name.equalsIgnoreCase(categoryRenderInfo.getName())) {
                configuration.removeCategory(categoryRenderInfo);
            }
        }
    }

    public static List<String> getRegisteredEntityNames() {
        final ArrayList<String> names = new ArrayList<String>();

        final ConfigCategory category = configuration.getCategory(Names.Config.Category.REGISTRY);
        for (final ConfigCategory categoryRenderInfo : category.getChildren()) {
            names.add(categoryRenderInfo.getName());
        }

        return names;
    }

    private ConfigurationHandler() {}

    @SubscribeEvent
    public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.MODID)) {
            loadConfiguration();
        }
    }
}
