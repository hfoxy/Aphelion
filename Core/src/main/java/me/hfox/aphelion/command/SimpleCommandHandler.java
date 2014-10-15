package me.hfox.aphelion.command;

public abstract class SimpleCommandHandler<U> implements CommandHandler<U> {

    protected String[] aliases;
    protected String description;
    protected String usage;

    public SimpleCommandHandler(String label, String[] aliases, String description, String usage) {
        this.aliases = newArray(label, aliases);
        this.description = description;
        this.usage = usage;
    }

    public SimpleCommandHandler(String[] aliases, String description, String usage) {
        this.aliases = aliases;
        this.description = description;
        this.usage = usage;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    private String[] newArray(String first, String... rest) {
        String[] all = new String[rest.length + 1];
        all[0] = first;
        System.arraycopy(rest, 0, all, 1, rest.length);

        return all;
    }

}
