package davincij.betamod.events.init;

import davincij.betamod.betamoditems;
// + imports for Namespace, TemplateItem, Item, ItemRegistryEvent, EventListener, Entrypoint
//   (let your IDE add these)

public class ItemListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        BetaModItems.greatwoodStick  = simpleItem("greatwood_stick");
        BetaModItems.silverwoodStick = simpleItem("silverwood_stick");
        BetaModItems.ironCap         = simpleItem("iron_cap");
        BetaModItems.silverCap       = simpleItem("silver_cap");
        BetaModItems.thaumiumCap     = simpleItem("thaumium_cap");
    }

    private static Item simpleItem(String name) {
        return new TemplateItem(NAMESPACE.id(name)).setTranslationKey(NAMESPACE, name);
    }
}
