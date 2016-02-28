package com.github.lunatrius.tracer.reference;

@SuppressWarnings("HardCodedStringLiteral")
public class Names {
    public static class Config {
        public static class Category {
            public static final String REGISTRY = "registry";
        }

        public static final String NAME = "name";
        public static final String NAME_DESC = ""; // intentionally left blank

        public static final String COLOR_RED = "colorRed";
        public static final String COLOR_RED_DESC = ""; // intentionally left blank
        public static final String COLOR_GREEN = "colorGreen";
        public static final String COLOR_GREEN_DESC = ""; // intentionally left blank
        public static final String COLOR_BLUE = "colorBlue";
        public static final String COLOR_BLUE_DESC = ""; // intentionally left blank
        public static final String COLOR_ALPHA = "colorAlpha";
        public static final String COLOR_ALPHA_DESC = ""; // intentionally left blank

        public static final String TTL = "ttl";
        public static final String TTL_DESC = ""; // intentionally left blank
        public static final String THICKNESS = "thickness";
        public static final String THICKNESS_DESC = ""; // intentionally left blank
        public static final String OFFSET_Y = "offsetY";
        public static final String OFFSET_Y_DESC = ""; // intentionally left blank

        public static final String LANG_PREFIX = Reference.MODID_LOWER + ".config";
    }

    public static final class Command {
        public static final class Message {
            public static final String USAGE = "tracer.command.usage";
            public static final String REGISTER_USAGE = "tracer.command.register.usage";
            public static final String REGISTER = "tracer.command.register";
            public static final String UNREGISTER_USAGE = "tracer.command.unregister.usage";
            public static final String UNREGISTER = "tracer.command.unregister";
            public static final String CLEAR = "tracer.command.clear";
        }

        public static final String NAME = "tracer";
        public static final String REGISTER = "register";
        public static final String UNREGISTER = "unregister";
        public static final String CLEAR = "clear";
    }
}
