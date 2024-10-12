package brstudio.godzin.snea.mixins;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;
import java.util.List;

public class MixinsLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Lists.newArrayList(
                "mixins.snea.json"
        );
    }

    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return Loader.isModLoaded(mixinConfig.substring(mixinConfig.indexOf('_') + 1, mixinConfig.lastIndexOf('.')));
    }
}
