package pl.trollcraft.creative.chat.config;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class DisplayCondition {

    private enum Condition {

        IS("is", (a, b) -> { return a.equals(b); }),
        IS_NOT("is-not", (a, b) -> !a.equals(b));

        private String operator;
        private ConditionCheck conditionCheck;

        Condition (String operator, ConditionCheck conditionCheck) {
            this.operator = operator;
            this.conditionCheck = conditionCheck;
        }

        public static Condition resolve(String operator) {
            for (Condition c : values())
                if (c.operator.equalsIgnoreCase(operator))
                    return c;
            return null;
        }

    }

    private String var;
    private Condition condition;
    private String val;

    public DisplayCondition (String expression) {
        String data[] = expression.split(" ");
        this.var = data[0];
        this.condition = Condition.resolve(data[1]);
        this.val = data[2];
    }

    public boolean verify(Player player) {

        String val = this.val.replaceAll("empty", "");

        return condition.conditionCheck
                .check(PlaceholderAPI.setBracketPlaceholders(player, var), PlaceholderAPI.setBracketPlaceholders(player, val));
    }

}
