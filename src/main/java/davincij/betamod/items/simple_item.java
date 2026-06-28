package davincij.betamod.items;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class simple_item extends TemplateItem {
    public simple_item(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier.namespace, identifier.path);
    }

    // Machine interaction here when you add it
}
