package dark.client.renders;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import dark.common.DarkBotMain;

/** Basic Advanced Obj modeler render for quick test runs and scaling
 *
 * @author DarkGuardsman */
public class RenderCore extends TileEntitySpecialRenderer
{

    private IModelCustom modelTurret;
    private float scale;
    private String texture;

    public RenderCore(String modelName, String textureName, float scaleDown)
    {
        modelTurret = AdvancedModelLoader.loadModel("/assets/dark/models/" + modelName + ".obj");
        this.scale = scaleDown;
        this.texture = textureName;
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double xx, double yy, double zz, float f)
    {
        render(entity, xx, yy, zz, scale);
    }

    private void render(TileEntity laser, double xx, double yy, double zz, float s)
    {

        GL11.glPushMatrix();
        GL11.glTranslated(xx + 1.5, yy + 2, zz);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glScalef(s, s, s);
        GL11.glRotatef(0, 0F, 1F, 0F);

        FMLClientHandler.instance().getClient().renderEngine.func_110577_a(new ResourceLocation(DarkBotMain.DOMAIN, "textures/uv/" + texture + ".png"));
        modelTurret.renderAll();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();

    }
}
