package files;

import net.minecraft.client.texture.ReloadableTexture;
import net.minecraft.client.texture.TextureContents;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public class CustomLogoTexture extends ReloadableTexture {

    public CustomLogoTexture(Identifier id) {
        super(id);
    }

    @Override
    public TextureContents loadContents(ResourceManager resourceManager) throws IOException {
        try (InputStream input = resourceManager.getResource(this.getId()).get().getInputStream()) {
            return new TextureContents(NativeImage.read(input), new TextureResourceMetadata(true, true));
        }
    }
}
